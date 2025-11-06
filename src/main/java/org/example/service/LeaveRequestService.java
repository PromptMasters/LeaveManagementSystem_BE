package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.LeaveRequest;
import org.example.model.User;
import org.example.model.LeaveBalance;
import org.example.repository.LeaveRequestRepository;
import org.example.repository.LeaveBalanceRepository;
import org.example.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.Enum.Status;
import org.example.Enum.LeaveType;
import org.example.dto.response.LeaveRequestResponse;
import org.example.dto.request.LeaveRequestRequest;
import org.springframework.data.domain.*;
import java.util.List;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaveRequestService {

        private final SimpMessagingTemplate messagingTemplate;
        private final LeaveRequestRepository leaveRequestRepository;
        private final LeaveBalanceRepository leaveBalanceRepository;
        private final UserRepository userRepository;

        public LeaveRequest createLeaveRequest(Long requestorId, LeaveRequestRequest requestDto) {

                User requestor = userRepository.findById(requestorId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                LeaveBalance leaveBalance = leaveBalanceRepository.findByUser_Id(requestorId)
                                .orElseThrow(() -> new IllegalArgumentException("Leave balance not found for user"));

                int totalDays = (int) (requestDto.getEndDate().toEpochDay() - requestDto.getStartDate().toEpochDay())
                                + 1;

                if (leaveBalance.getRemainingDays() < totalDays) {
                        throw new IllegalArgumentException("Insufficient leave balance");
                }

                // Check overlap
                boolean hasOverlap = leaveRequestRepository.existsByRequestor_IdAndStatusInAndDateOverlap(
                                requestorId,
                                List.of(Status.PENDING, Status.APPROVED),
                                requestDto.getStartDate(),
                                requestDto.getEndDate());

                if (hasOverlap) {
                        throw new IllegalArgumentException(
                                        "You already have a leave request overlapping this date range!");
                }

                leaveBalance.setRemainingDays(leaveBalance.getRemainingDays() - totalDays);
                leaveBalanceRepository.save(leaveBalance);

                LeaveRequest leaveRequest = LeaveRequest.builder()
                                .requestor(requestor)
                                .title(requestDto.getTitle())
                                .reason(requestDto.getReason())
                                .startDate(requestDto.getStartDate())
                                .endDate(requestDto.getEndDate())
                                .leaveType(requestDto.getLeaveType())
                                .status(Status.PENDING)
                                .totalDays(totalDays)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
                Long managerId = requestor.getManager().getId();

                // Gửi riêng tới topic của manager đó
                messagingTemplate.convertAndSend("/topic/manager/" + managerId,
                                LeaveRequestResponse.mapToResponse(saved));

                return saved;
        }

        public Page<LeaveRequestResponse> getAllRequests(
                        String keyword, Status status, LeaveType leaveType, int page, int limit, String sortBy,
                        String sortType) {

                Sort sort = sortType.equalsIgnoreCase("desc")
                                ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();

                Pageable pageable = PageRequest.of(page - 1, limit, sort);
                Page<LeaveRequest> leavePage = leaveRequestRepository.searchRequests(keyword, status, leaveType,
                                pageable);

                return leavePage.map(LeaveRequestResponse::mapToResponse);
        }

        public LeaveRequest updateStatus(Long id, Status status) {
                LeaveRequest req = leaveRequestRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
                req.setStatus(status);
                req.setUpdatedAt(LocalDateTime.now());
                return leaveRequestRepository.save(req);
        }

        public List<LeaveRequestResponse> getRequestsByUser(Long userId) {
                List<LeaveRequest> list = leaveRequestRepository.findByRequestor_Id(userId);
                return list.stream().map(LeaveRequestResponse::mapToResponse).toList();
        }
}
