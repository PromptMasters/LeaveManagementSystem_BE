package org.example.service;

import org.example.model.LeaveRequest;
import org.example.model.LeaveRequestResponse;
import org.example.model.User;
import org.example.repository.LeaveRequestRepository;
import org.example.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.Enum.Status;
import org.example.Enum.LeaveType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveRequestService {
    private final SimpMessagingTemplate messagingTemplate;
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public LeaveRequest createLeaveRequest(Long requestorId, LeaveRequest request) {
        Optional<User> userOpt = userRepository.findById(requestorId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User requestor = userOpt.get();

        request.setRequestor(requestor);
        request.setStatus(Status.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        LeaveRequest leaveRequest = leaveRequestRepository.save(request);
        messagingTemplate.convertAndSend("/topic/leave-requests", leaveRequest);
        return leaveRequest;
    }

    public List<LeaveRequestResponse> getAllRequests() {
        List<LeaveRequest> list = leaveRequestRepository.findAll();
        return list.stream()
                .map(LeaveRequestResponse::new)
                .toList();
    }


    public LeaveRequest updateStatus(Long id, Status status) {
        LeaveRequest req = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        req.setStatus(status);
        req.setUpdatedAt(LocalDateTime.now());
        return leaveRequestRepository.save(req);
    }
}
