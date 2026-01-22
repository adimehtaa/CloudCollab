package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.repository.UserInvitationRepository;
import cloud.devyard.cloudcollab.service.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final UserInvitationRepository invitationRepository;


    @Value("${invitation.expiry-hours:72}")
    private int invitationExpiryHours;
}
