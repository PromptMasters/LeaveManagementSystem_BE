package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.Enum.LeaveType;
import org.example.Enum.Status;
import org.example.dto.request.LeaveRequestRequest;
import org.example.dto.request.UpdateStatusRequest;
import org.example.dto.response.ApiResponses;
import org.example.dto.response.LeaveRequestResponse;
import org.example.dto.response.PagingResponse;
import org.example.service.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/leave-request")
@RequiredArgsConstructor
@Tag(name = "Leave Request", description = "Endpoints for managing employee leave requests")
public class LeaveRequestController {

        private final LeaveRequestService leaveRequestService;

        @Operation(summary = "Create a new leave request", description = "Create leave request for a specific employee (by requestorId)")
        @ApiResponse(responseCode = "200", description = "Leave request created successfully")
        @PostMapping
        public ResponseEntity<String> createLeaveRequest(
                        @Parameter(description = "ID of the user creating the leave request") @RequestParam Long requestorId,
                        @RequestBody LeaveRequestRequest requestDto) {

                leaveRequestService.createLeaveRequest(requestorId, requestDto);
                return ResponseEntity.ok("Leave request created successfully!");
        }

        // GET: ALL (search + filter + paging)
        @Operation(summary = "Search & filter leave requests", description = "Search by keyword, status, or leave type with pagination")
        @ApiResponse(responseCode = "200", description = "Leave requests fetched successfully")
        @GetMapping
        public ResponseEntity<ApiResponses<?>> getAllLeaveRequests(
                        @Parameter(description = "Search keyword (username or department)") @RequestParam(required = false) String keyword,
                        @Parameter(description = "Filter by status") @RequestParam(required = false) Status status,
                        @Parameter(description = "Filter by leave type") @RequestParam(required = false, name = "leave_type") LeaveType leaveType,
                        @Parameter(description = "Page number (default = 1)") @RequestParam(defaultValue = "1") int page,
                        @Parameter(description = "Page limit (default = 15)") @RequestParam(defaultValue = "15") int limit,
                        @Parameter(description = "Sort by field (default = createdAt)") @RequestParam(defaultValue = "createdAt") String sortBy,
                        @Parameter(description = "Sort order asc/desc (default = desc)") @RequestParam(defaultValue = "desc") String sortType) {

                Page<LeaveRequestResponse> result = leaveRequestService.getAllRequests(
                                keyword, status, leaveType, page, limit, sortBy, sortType);

                PagingResponse paging = PagingResponse.builder()
                                .page(page)
                                .limit(limit)
                                .totalPages(result.getTotalPages())
                                .build();

                return ResponseEntity.ok(ApiResponses.<Object>builder()
                                .statusCode(200)
                                .message("Get leave requests successfully")
                                .data(result.getContent())
                                .paging(paging)
                                .build());
        }

        // UPDATE STATUS
        @Operation(summary = "Update leave request status", description = "Update status of a leave request (e.g., APPROVED / REJECTED)")
        @ApiResponse(responseCode = "200", description = "Status updated successfully")
        @PatchMapping("/{id}/status")
        public ResponseEntity<String> updateStatus(
                        @Parameter(description = "Leave request ID") @PathVariable Long id,
                        @RequestBody UpdateStatusRequest request) {

                leaveRequestService.updateStatus(id, request.getStatus());
                return ResponseEntity.ok("Leave request update status successfully!");
        }

        @GetMapping("/user/{userId}")
        public List<LeaveRequestResponse> getRequestsByUser(
                        @Parameter(description = "ID of the user (employee)") @PathVariable Long userId) {
                return leaveRequestService.getRequestsByUser(userId);
        }
}
