package com.parkease.backend.entity;

import com.parkease.backend.enumtype.Role;
import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Common fields for ALL roles =====

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // ===== Approval & status =====

    /**
     * ADMIN  -> approved = true
     * PROVIDER -> approved = false (until admin approves)
     * DRIVER -> approved = true (for now)
     */
    @Column(nullable = false)
    private boolean approved = false;

    @Column(nullable = false)
    private boolean enabled = true;

    // ===== Audit =====

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
      @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<ParkingLot> parkingLots;

    // ===== Constructors =====

    public User() {
    }

    /**
     * Recommended constructor for registration
     */
    public User(String fullName, String email, String phoneNumber, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;

        // 🔑 Role-based defaults
        if (role == Role.PROVIDER) {
            this.approved = false;
        } else {
            this.approved = true; // ADMIN & DRIVER
        }
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
 
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
 
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
 
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isApproved() {
        return approved;
    }
 
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isEnabled() {
        return enabled;
    }
 
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }
}
