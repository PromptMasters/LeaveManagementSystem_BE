package org.example.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.Enum.Status;

@Getter
@Setter
public class UpdateStatusRequest {
    private Status status;
}