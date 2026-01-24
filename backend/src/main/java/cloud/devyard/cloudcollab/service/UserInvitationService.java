package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.InviteUserRequest;
import cloud.devyard.cloudcollab.dto.response.InvitationResponse;

import java.util.List;

public interface UserInvitationService {

    public InvitationResponse inviteUser(InviteUserRequest request, Long organizationId, Long invitedByUserId);
    public List<InvitationResponse> getOrganizationInvitations(Long organizationId);
    public void cancelInvitation(Long invitationId, Long userId);
    public void expireOldInvitations();

}
