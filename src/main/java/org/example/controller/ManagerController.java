package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/leave-request/{id}")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests(@PathVariable UUID id) {
        List<LeaveRequest> allLeaveRequests = managerService.getAllLeaveRequests();
        return ResponseEntity.ok(allLeaveRequests);
    }

    @PostMapping("/leave-request/{id}")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@PathVariable UUID userId) {
        LeaveRequestResponse leaveRequest = employeeService.createLeaveRequest(userId);
        return ResponseEntity.ok(leaveRequest);
    }
}
