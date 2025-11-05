package org.example.controller;

import org.example.Enum.Status;
import org.example.model.LeaveRequest;
import org.example.model.LeaveRequestResponse;
import org.example.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/leave-request")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // POST /leave-request
    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestParam Long requestorId, @RequestBody LeaveRequest request) {
        LeaveRequest saved = leaveRequestService.createLeaveRequest(requestorId, request);
        return ResponseEntity.ok(saved);
    }

    // GET /leave-request
    @GetMapping
    public ResponseEntity<List<LeaveRequestResponse>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllRequests());
    }

    // POST /leave-request/{id}/status
    @PostMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status) {
        LeaveRequest updated = leaveRequestService.updateStatus(id,status);
        return ResponseEntity.ok(updated);
    }
}
