package com.parkease.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.parkease.backend.entity.Notification;
import com.parkease.backend.repository.NotificationRepository;

@RestController
@RequestMapping("/api/admin/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /* ================= GET ADMIN NOTIFICATIONS ================= */
    @GetMapping
    public List<Notification> getAdminNotifications() {
        return notificationRepository
                .findByTargetRoleOrderByCreatedAtDesc("ADMIN");
    }

    /* ================= MARK AS READ ================= */
    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        n.setRead(true);
        notificationRepository.save(n);
    }
}
