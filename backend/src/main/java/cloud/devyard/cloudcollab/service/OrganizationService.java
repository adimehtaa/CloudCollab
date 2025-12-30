package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.model.Organization;

public interface OrganizationService {
    public Organization  createOrganization(String name);
    public Organization getOrganizationById(Long id);
}
