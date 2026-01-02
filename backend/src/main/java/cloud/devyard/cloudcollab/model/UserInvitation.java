package cloud.devyard.cloudcollab.model;

import cloud.devyard.cloudcollab.model.enums.InvitationStatus;
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
@Table(name = "user_invitations")
public class UserInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false , name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by_user_id")
    private User inviteBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "role_id")
    private Role role;

    @Column(nullable = false , unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus invitationStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime acceptedAt;
}
