package com.example.apitest.strategy;

import org.springframework.stereotype.Component;

@Component("sms")
public class SmsNotification implements NotificationStrategy {

    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("📱 SMS envoyé à " + recipient + " : " + message);
    }
    
}
