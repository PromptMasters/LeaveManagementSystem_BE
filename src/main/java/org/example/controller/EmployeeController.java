package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/leave-request")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@PathVariable UUID userId) {
        LeaveRequestResponse leaveRequest = employeeService.createLeaveRequest(userId);
        return ResponseEntity.ok(leaveRequest);
    }
}
