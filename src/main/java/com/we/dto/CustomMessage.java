package com.we.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomMessage {
    private String message;
    private String status;
}
