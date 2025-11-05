package org.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.LeaveRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LeaveRequestResponse {
    private Long leaveRequestId;
    private String requestorName;
    private String departmentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String title;
    private Integer totalDays;
    private String status;
    private String leaveType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LeaveRequestResponse mapToResponse(LeaveRequest lr) {
        return LeaveRequestResponse.builder()
                .leaveRequestId(lr.getId())
                .requestorName(lr.getRequestor() != null ? lr.getRequestor().getUsername() : null)
                .departmentName(lr.getRequestor() != null ? lr.getRequestor().getDepartment() : null)
                .startDate(lr.getStartDate())
                .endDate(lr.getEndDate())
                .reason(lr.getReason())
                .title(lr.getTitle())
                .totalDays(lr.getTotalDays())
                .status(lr.getStatus() != null ? lr.getStatus().name() : null)
                .leaveType(lr.getLeaveType() != null ? lr.getLeaveType().name() : null)
                .createdAt(lr.getCreatedAt())
                .updatedAt(lr.getUpdatedAt())
                .build();
    }
}
