package cloud.devyard.cloudcollab.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String bio;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    private String location;
    private LocalDate dateOfBirth;
    private Boolean emailVerified;
    private Boolean active;
    private Long organizationId;
    private String organizationName;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime passwordChange;
}
