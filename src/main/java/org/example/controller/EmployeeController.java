package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.LeaveRequest;
import org.example.service.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping("/leave-request")
    public ResponseEntity<LeaveRequest> createLeaveRequest(@PathVariable UUID userId) {
        LeaveRequest leaveRequest = leaveRequestService.createLeaveRequest(userId);
        return ResponseEntity.ok(leaveRequest);
    }
}
