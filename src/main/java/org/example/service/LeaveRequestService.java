package org.example.service;

import org.example.model.LeaveRequest;
import org.example.model.User;
import org.example.repository.LeaveRequestRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
    }

    public LeaveRequest createLeaveRequest(Long requestorId, LeaveRequest request) {
        Optional<User> userOpt = userRepository.findById(requestorId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User requestor = userOpt.get();

        request.setRequestor(requestor);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        return leaveRequestRepository.save(request);
    }

    public List<LeaveRequest> getAllRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest updateStatus(Long id, String status) {
        LeaveRequest req = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        req.setStatus(status.toUpperCase());
        req.setUpdatedAt(LocalDateTime.now());
        return leaveRequestRepository.save(req);
    }
}
