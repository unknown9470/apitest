package com.example.apitest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.apitest.model.ScheduledNotification;
import com.example.apitest.model.User;
import com.example.apitest.repository.ScheduledNotificationRepository;
import com.example.apitest.repository.UserRepository;
import com.example.apitest.strategy.NotificationStrategy;

@Service
public class ScheduledNotificationService {

    private final ScheduledNotificationRepository scheduledNotificationRepository;
    private final UserRepository userRepository;
    private final Map<String, NotificationStrategy> strategies;

    public ScheduledNotificationService(
        ScheduledNotificationRepository scheduledNotificationRepository, 
        UserRepository userRepository, 
        Map<String, NotificationStrategy> strategies){
            this.scheduledNotificationRepository = scheduledNotificationRepository;
            this.userRepository = userRepository;
            this.strategies = strategies;
    }

    public ScheduledNotification schedule(String username, ScheduledNotification notif) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        notif.setUser(user);
        notif.setSent(false);
        return scheduledNotificationRepository.save(notif);
    }

    public List<ScheduledNotification> getAllForUser(String username) {
        return scheduledNotificationRepository.findAll().stream()
        .filter(n -> n.getUser().getUsername().equals(username)).toList();
    }

    @Scheduled(fixedRate = 60000)
    public void sendScheduledNotifications() {
        List<ScheduledNotification> toSend = scheduledNotificationRepository.findByScheduledAtBeforeAndSentFalse(LocalDateTime.now());

        for (ScheduledNotification notif : toSend) {
            NotificationStrategy strategy = strategies.get(notif.getType().toLowerCase());
            if (strategy != null){
                strategy.sendNotification(notif.getMessage(), notif.getRecipient());
                notif.setSent(true);
                scheduledNotificationRepository.save(notif);
            }
        }
    }
    
}
