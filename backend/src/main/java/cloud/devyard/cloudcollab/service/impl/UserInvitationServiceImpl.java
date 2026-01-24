package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.dto.request.InviteUserRequest;
import cloud.devyard.cloudcollab.dto.response.InvitationResponse;
import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.exception.ResourceNotFoundException;
import cloud.devyard.cloudcollab.model.Organization;
import cloud.devyard.cloudcollab.model.Role;
import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserInvitation;
import cloud.devyard.cloudcollab.model.enums.InvitationStatus;
import cloud.devyard.cloudcollab.repository.RoleRepository;
import cloud.devyard.cloudcollab.repository.UserInvitationRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.OrganizationService;
import cloud.devyard.cloudcollab.service.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final UserInvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationService organizationService;

    @Value("${invitation.expiry-hours:72}")
    private int invitationExpiryHours;


    @Override
    public InvitationResponse inviteUser(InviteUserRequest request, Long organizationId, Long invitedByUserId) {

        if (userRepository.existsByEmailAndOrganizationId(
                request.getEmail(), organizationId)) {
            throw new BadRequestException(
                    "User with this email already exists in the organization"
            );
        }

        if (invitationRepository.existsByEmailAndOrganizationIdAndInvitationStatus(
                request.getEmail(), organizationId, InvitationStatus.PENDING)) {
            throw new BadRequestException(
                    "Pending invitation already exists for this email"
            );
        }

        User invitedBy = userRepository.findById(invitedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Inviting user not found"));

        Organization organization = organizationService.getOrganizationById(organizationId);

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        UserInvitation invitation = UserInvitation.builder()
                .email(request.getEmail())
                .organization(organization)
                .role(role)
                .invitationStatus(InvitationStatus.PENDING)
                .token(UUID.randomUUID().toString())
                .inviteBy(invitedBy)
                .expiresAt(LocalDateTime.now().plusHours(invitationExpiryHours))
                .build();

        // TODO: Send invitation email

        return mapToInvitationResponse(invitationRepository.save(invitation));
    }

    @Override
    public List<InvitationResponse> getOrganizationInvitations(Long organizationId){
        return invitationRepository.findByOrganizationIdAndInvitationStatus(organizationId , InvitationStatus.PENDING)
                .stream().map(this::mapToInvitationResponse).toList();
    }

    @Override
    public void cancelInvitation(Long invitationId, Long userId) {
        UserInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(()->  new ResourceNotFoundException("User not found"));

        if (!invitation.getOrganization().getId().equals(user.getOrganization().getId())){
            throw new BadRequestException("You don't have permission to cancel this invitation");
        }

        invitation.setInvitationStatus(InvitationStatus.CANCELLED);
        invitationRepository.save(invitation);
    }

    @Override
    public void expireOldInvitations() {

        List<UserInvitation> expiredInvitations = invitationRepository
                .findByInvitationStatusAndExpiresAtBefore(InvitationStatus.PENDING, LocalDateTime.now());

        expiredInvitations.forEach(invitation -> {
            invitation.setInvitationStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitation);
        });

    }


    private InvitationResponse mapToInvitationResponse(UserInvitation invitation) {
        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getEmail())
                .roleName(invitation.getRole().getName().name())
                .invitedByName(invitation.getInviteBy().getFirstName() + " " + invitation.getInviteBy().getLastName())
                .status(invitation.getInvitationStatus().name())
                .createdAt(invitation.getCreatedAt())
                .expiresAt(invitation.getExpiresAt())
                .build();
    }
}
