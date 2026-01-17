package cloud.devyard.cloudcollab.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 500)
    private String bio;

    @Size(max = 50)
    private String phoneNumber;

    @Size(max = 100)
    private String jobTitle;

    @Size(max = 100)
    private String department;

    @Size(max = 100)
    private String location;

    private LocalDate dateOfBirth;
}
