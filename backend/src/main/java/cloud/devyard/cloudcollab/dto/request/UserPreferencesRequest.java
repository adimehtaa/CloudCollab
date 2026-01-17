package cloud.devyard.cloudcollab.dto.request;

import lombok.Data;

@Data
public class UserPreferencesRequest {
    private Boolean emailNotifications;
    private Boolean pushNotifications;
    private Boolean smsNotifications;
    private String theme;
    private String language;
    private String timezone;
    private Boolean profilePublic;
    private Boolean showEmail;
    private Boolean showOnlineStatus;
}
