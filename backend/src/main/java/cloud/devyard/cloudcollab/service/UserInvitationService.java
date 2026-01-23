package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.InviteUserRequest;
import cloud.devyard.cloudcollab.dto.response.InvitationResponse;

public interface UserInvitationService {

    public InvitationResponse inviteUser(InviteUserRequest request, Long organizationId, Long invitedByUserId);

}
