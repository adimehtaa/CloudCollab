package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.dto.request.LoginRequest;
import cloud.devyard.cloudcollab.dto.request.SignupRequest;
import cloud.devyard.cloudcollab.dto.response.JwtResponse;
import cloud.devyard.cloudcollab.dto.response.UserResponse;
import cloud.devyard.cloudcollab.exception.AlreadyExistsException;
import cloud.devyard.cloudcollab.exception.InvalidCredentialsException;
import cloud.devyard.cloudcollab.exception.ResourceNotFoundException;
import cloud.devyard.cloudcollab.model.Organization;
import cloud.devyard.cloudcollab.model.RefreshToken;
import cloud.devyard.cloudcollab.model.Role;
import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.enums.RoleType;
import cloud.devyard.cloudcollab.repository.RoleRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.security.JwtTokenProvider;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.AuthService;
import cloud.devyard.cloudcollab.service.OrganizationService;
import cloud.devyard.cloudcollab.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final OrganizationService organizationService;

    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarUrl(user.getAvatarUrl())
                .organizationId(user.getOrganization().getId())
                .organizationName(user.getOrganization().getName())
                .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                .build();
    }

    @Override
    @Transactional
    public UserResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername()))
        {
            throw new AlreadyExistsException("Username is already taken");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())){
            throw new AlreadyExistsException("Email is already registered");
        }

        Organization organization = null;
        if (signupRequest.getOrganizationName() != null && !signupRequest.getOrganizationName().trim().isEmpty()){
            organization = organizationService.createOrganization(signupRequest.getOrganizationName());
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .emailVerified(false)
                .active(true)
                .organization(organization)
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found"));
        roles.add(userRole);

        if (organization != null) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin Role not found"));
            roles.add(adminRole);
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .avatarUrl(savedUser.getAvatarUrl())
                .emailVerified(savedUser.getEmailVerified())
                .active(savedUser.getActive())
                .organizationId(savedUser.getOrganization() != null ? savedUser.getOrganization().getId() : null)
                .organizationName(savedUser.getOrganization() != null ? savedUser.getOrganization().getName() : null)
                .roles(savedUser.getRoles().stream().map(role -> role.getName().name()).toList())
                .createdAt(savedUser.getCreatedAt())
                .lastLoginAt(savedUser.getLastLogin())
                .build();
    }

    @Override
    public JwtResponse refreshToken(String requestRefreshToken) {
        // TODO: implement refreshToken
        return null;
    }

    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }
}
