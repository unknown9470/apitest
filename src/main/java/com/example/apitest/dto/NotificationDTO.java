package com.example.apitest.dto;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private String type;
    private String recipient;
    private String message;
    private String status; 

}
