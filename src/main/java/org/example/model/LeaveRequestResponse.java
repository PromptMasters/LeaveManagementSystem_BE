package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.Enum.LeaveType;
import org.example.Enum.Status;
import org.example.model.User;
import org.example.model.LeaveRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class LeaveRequestResponse {

    private Long LeaveRequestId;

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

    // ✅ thêm constructor tiện dụng
    public LeaveRequestResponse(LeaveRequest lr) {
        this.LeaveRequestId = lr.getId();
        this.requestorName = lr.getRequestor() != null ? lr.getRequestor().getUsername() : null;
        this.departmentName = lr.getRequestor() != null && lr.getRequestor().getDepartment() != null
                ? lr.getRequestor().getDepartment() : null;
        this.startDate = lr.getStartDate();
        this.endDate = lr.getEndDate();
        this.reason = lr.getReason();
        this.title = lr.getTitle();
        this.totalDays = lr.getTotalDays();
        this.status = lr.getStatus() != null ? lr.getStatus().name() : null;
        this.leaveType = lr.getLeaveType() != null ? lr.getLeaveType().name() : null;
        this.createdAt = lr.getCreatedAt();
        this.updatedAt = lr.getUpdatedAt();
    }
}
