package br.dev.mhc.financialassistant.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "login_attempts")
public class LoginAttempt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "failed_attempts")
    private int failedAttempts;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public LoginAttempt(String username) {
        this.username = username;
        this.failedAttempts = 0;
    }

    public void addFailedAttempt() {
        failedAttempts++;
    }

    public void successfulLogin() {
        failedAttempts = 0;
    }

}
