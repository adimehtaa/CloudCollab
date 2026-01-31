package cloud.devyard.cloudcollab.dto.response;

import cloud.devyard.cloudcollab.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesResponse {

    private Long id;
    private  Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private Boolean emailVerified;
    private Boolean active;
    private Long organizationId;
    private String organizationName;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private String bio;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    private String location;
    private LocalDate dateOfBirth;
    private Boolean emailNotifications;
    private Boolean pushNotifications;
    private  Boolean smsNotifications;
    private String theme;
    private String language;
    private String timezone;
    private Boolean profilePublic;
    private Boolean showEmail;
    private Boolean showOnlineStatus;
}
