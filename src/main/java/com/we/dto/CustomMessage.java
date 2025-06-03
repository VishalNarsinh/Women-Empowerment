package com.we.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomMessage {
    private String message;
    private String status;
}
