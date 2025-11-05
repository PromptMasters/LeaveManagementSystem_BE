package org.example.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.model.LeaveRequest;
import org.example.service.service.LeaveRequestService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private LeaveRequest createLeaveRequest(LeaveRequest leaveRequest){
        return leaveRequest;
    };
}
