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
public interface LeaveRequestService {

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository);

    public LeaveRequest createLeaveRequest(Long requestorId, LeaveRequest request);

    public List<LeaveRequest> getAllRequests();

    public LeaveRequest updateStatus(Long id, String status);
}
