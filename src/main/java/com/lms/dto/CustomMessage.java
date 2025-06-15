package com.lms.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {
    private String message;
    private String status;
}
