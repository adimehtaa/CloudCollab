package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
}
