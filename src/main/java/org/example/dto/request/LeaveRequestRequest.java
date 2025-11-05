package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Enum.LeaveType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestRequest {

    private String title;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType leaveType;
}
