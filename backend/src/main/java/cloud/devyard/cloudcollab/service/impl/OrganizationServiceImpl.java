package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.model.Organization;
import cloud.devyard.cloudcollab.repository.OrganizationRepository;
import cloud.devyard.cloudcollab.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization  createOrganization(String name){
        String slug = generateSlug(name);

        if (organizationRepository.existsBySlug(slug))
        {
            throw new BadRequestException("Organization with this name already exists");
        }

        Organization organization = Organization.builder()
                .name(name)
                .slug(slug)
                .active(true)
                .subscriptionPlan("FREE")
                .build();

        return organizationRepository.save(organization);
    }

    private String generateSlug(String name){
        String slug = name.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");

        String finalSlug = slug;
        int counter = 1;
        while (organizationRepository.existsBySlug(finalSlug)) {
            finalSlug = slug + "-" + counter;
            counter++;
        }

        return finalSlug;
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Organization not found"));
    }
}
