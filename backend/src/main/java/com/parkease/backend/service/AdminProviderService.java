package com.parkease.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkease.backend.dto.AdminProviderResponse;
import com.parkease.backend.entity.Notification;
import com.parkease.backend.entity.User;
import com.parkease.backend.enumtype.Role;
import com.parkease.backend.repository.NotificationRepository;
import com.parkease.backend.repository.UserRepository;

@Service
@Transactional
public class AdminProviderService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public AdminProviderService(
            UserRepository userRepository,
            NotificationRepository notificationRepository
    ) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    /* ================= GET ALL PROVIDERS ================= */
    public List<AdminProviderResponse> getAllProviders() {
        return userRepository.findByRole(Role.PROVIDER)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    /* ================= APPROVE PROVIDER ================= */
    public void approveProvider(Long id) {
        User provider = getProvider(id);

        provider.setApproved(true);
        provider.setEnabled(true);
        userRepository.save(provider);

        notificationRepository.save(
                new Notification(
                        "Your provider account has been approved by admin.",
                        "PROVIDER"
                )
        );
    }

    /* ================= SUSPEND PROVIDER ================= */
    public void suspendProvider(Long id) {
        User provider = getProvider(id);

        provider.setEnabled(false);
        userRepository.save(provider);

        notificationRepository.save(
                new Notification(
                        "Your provider account has been suspended by admin.",
                        "PROVIDER"
                )
        );
    }

    /* ================= REACTIVATE PROVIDER ================= */
    public void reactivateProvider(Long id) {
        User provider = getProvider(id);

        provider.setEnabled(true);
        userRepository.save(provider);

        notificationRepository.save(
                new Notification(
                        "Your provider account has been reactivated.",
                        "PROVIDER"
                )
        );
    }

    /* ================= REJECT PROVIDER ================= */
    public void rejectProvider(Long id) {
        User provider = getProvider(id);
        userRepository.delete(provider);

        notificationRepository.save(
                new Notification(
                        "A provider application was rejected and removed.",
                        "ADMIN"
                )
        );
    }

    /* ================= HELPERS ================= */
    private User getProvider(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (user.getRole() != Role.PROVIDER) {
            throw new RuntimeException("User is not a provider");
        }
        return user;
    }

    private AdminProviderResponse mapToDto(User u) {
        AdminProviderResponse dto = new AdminProviderResponse();
        dto.id = u.getId();
        dto.name = u.getFullName();
        dto.ownerName = u.getFullName();
        dto.email = u.getEmail();
        dto.phone = u.getPhoneNumber();
        dto.appliedDate = u.getCreatedAt();

        if (!u.isApproved()) {
            dto.status = "pending";
        } else if (!u.isEnabled()) {
            dto.status = "suspended";
        } else {
            dto.status = "approved";
        }
        return dto;
    }
}
