package com.example.apitest.controller;

import com.example.apitest.model.ScheduledNotification;
import com.example.apitest.service.ScheduledNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/schedule")
public class ScheduledNotificationController {

    private final ScheduledNotificationService scheduledService;

    public ScheduledNotificationController(ScheduledNotificationService scheduledService) {
        this.scheduledService = scheduledService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduledNotification>> getAll(Authentication authentication) {
        return ResponseEntity.ok(scheduledService.getAllForUser(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<ScheduledNotification> schedule(@RequestBody ScheduledNotification notif, Authentication authentication) {
        return ResponseEntity.ok(scheduledService.schedule(authentication.getName(), notif));
    }
    
}
