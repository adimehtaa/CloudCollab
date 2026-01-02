package cloud.devyard.cloudcollab.model;

import cloud.devyard.cloudcollab.model.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_activities", indexes = {
        @Index(name = "idx_user_id" , columnList = "user_id"),
        @Index(name = "idx_created_at", columnList = "createdAt")
})
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String ipAddress;

    private String userAgent;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
