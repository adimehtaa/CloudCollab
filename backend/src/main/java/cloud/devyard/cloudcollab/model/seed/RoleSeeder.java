package cloud.devyard.cloudcollab.model.seed;

import cloud.devyard.cloudcollab.model.Role;
import cloud.devyard.cloudcollab.model.enums.RoleType;
import cloud.devyard.cloudcollab.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        Arrays.stream(RoleType.values()).forEach(roleType ->
                roleRepository.findByName(roleType)
                        .orElseGet(() -> roleRepository.save(new Role(roleType)))
        );
    }
}

