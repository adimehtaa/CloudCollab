package cloud.devyard.cloudcollab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "user_preferences")
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Boolean emailNotifications = true;

    @Column(nullable = false)
    private Boolean pushNotifications = true;

    @Column(nullable = false)
    private  Boolean smsNotifications = false;

    private String theme = "light"; // light, dark, auto

    private String language = "en";

    private String timezone = "UTC";

    @Column(nullable = false)
    private Boolean profilePublic = false;

    @Column(nullable = false)
    private Boolean showEmail = false;

    @Column(nullable = false)
    private Boolean showOnlineStatus = true;
}
