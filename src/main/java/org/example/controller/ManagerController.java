package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.LeaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/leave-request")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequest(@PathVariable UUID id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/leave-request")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@PathVariable UUID userId) {
        LeaveRequestResponse leaveRequest = employeeService.createLeaveRequest(userId);
        return ResponseEntity.ok(leaveRequest);
    }
}
