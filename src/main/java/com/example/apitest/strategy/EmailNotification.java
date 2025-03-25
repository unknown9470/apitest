package com.example.apitest.strategy;

import org.springframework.stereotype.Component;

@Component("email")
public class EmailNotification implements NotificationStrategy{
    
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("📧 Email envoyé à " + recipient + " : " + message);
    }
}
