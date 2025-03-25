package com.example.apitest.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.apitest.dto.NotificationDTO;
import com.example.apitest.mapper.NotificationMapper;
import com.example.apitest.model.Notification;
import com.example.apitest.model.User;
import com.example.apitest.repository.NotificationRepository;
import com.example.apitest.repository.UserRepository;
import com.example.apitest.strategy.NotificationStrategy;

@Service
public class NotificationService {

    private final UserRepository userRepository;

    private final Map<String, NotificationStrategy> strategies;
    private final NotificationRepository notificationRepository;
    
    public NotificationService(Map<String, NotificationStrategy> strategies,
            NotificationRepository notificationRepository,
            UserRepository userRepository) {
        this.strategies = strategies;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public NotificationDTO send(String type, String recipient, String message, String username) {
        NotificationStrategy strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("❌ Type de notification inconnu: " + type);
        }

        strategy.sendNotification(message, recipient);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Notification notification = new Notification();
        notification.setType(type);
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setStatus("envoyé");
        notification.setUser(user);

        return NotificationMapper.toDTO(notificationRepository.save(notification));
    }

    public List<NotificationDTO> getAll(String username) {
        return notificationRepository.findByUserUsername(username).stream()
            .map(NotificationMapper::toDTO).toList();
    }

    public Optional<NotificationDTO> getById(Long id) {
        return notificationRepository.findById(id)
            .map(NotificationMapper::toDTO);
    }

    public NotificationDTO update(Long id, String message, String status) {
        Notification notif = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        notif.setMessage(message);
        notif.setStatus(status);
        return NotificationMapper.toDTO(notificationRepository.save(notif));
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public List<NotificationDTO> getUnread(String username) {
        return notificationRepository.findByUserUsernameAndStatus(username, "non lu").stream()
            .map(NotificationMapper::toDTO).toList();

    }

    public NotificationDTO markAsRead(Long id) {
        Notification notif = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification Introuvable"));
        notif.setStatus("lu");
        return NotificationMapper.toDTO(notificationRepository.save(notif));
    }


}
