package cloud.devyard.cloudcollab.model.dto.request;

import cloud.devyard.cloudcollab.model.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Username is required", groups = ValidationGroups.Create.class)
    @Size(min = 3, max = 50, message = "Username must be between {min} and {max} characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username can only contain letters, numbers, underscores and hyphens")
    private String username;

    @NotBlank(message = "Email is required", groups = ValidationGroups.Create.class)
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be within {max} characters")
    private String email;

    @NotBlank(message = "Password is required", groups = ValidationGroups.Create.class)
    @Size(min = 6, max = 120, message = "Password must be between {min} and {max} characters")
    private String password;

    @Size(max = 100, message = "First name must be within {max} characters")
    private String firstName;

    @Size(max = 100, message = "Last name must be within {max} characters")
    private String lastName;

    @Pattern(regexp = "^https?://.+", message = "Must be a valid URL")
    private String avatarUrl;
}