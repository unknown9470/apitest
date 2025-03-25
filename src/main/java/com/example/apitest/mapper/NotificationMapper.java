package com.example.apitest.mapper;

import com.example.apitest.dto.NotificationDTO;
import com.example.apitest.model.Notification;

public class NotificationMapper {
    
    public static NotificationDTO toDTO(Notification notif) {

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notif.getId());
        dto.setType(notif.getType());
        dto.setMessage(notif.getMessage());
        dto.setRecipient(notif.getRecipient());
        dto.setStatus(notif.getStatus());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {

        Notification notif = new Notification();
        notif.setId(dto.getId());
        notif.setType(dto.getType());
        notif.setMessage(dto.getMessage());
        notif.setRecipient(dto.getRecipient());
        notif.setStatus(dto.getStatus());
        return notif;
    }
}
