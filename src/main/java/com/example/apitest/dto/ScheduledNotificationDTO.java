package com.example.apitest.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ScheduledNotificationDTO {

    private Long id;

    private String type;
    private String recipient;
    private String message;

    private LocalDateTime scheduledAt;
    private boolean sent = false;
    
}
