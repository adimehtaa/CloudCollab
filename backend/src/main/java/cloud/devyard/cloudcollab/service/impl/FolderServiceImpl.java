package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.dto.request.CreateFolderRequest;
import cloud.devyard.cloudcollab.dto.response.FolderResponse;
import cloud.devyard.cloudcollab.exception.ResourceNotFoundException;
import cloud.devyard.cloudcollab.model.Folder;
import cloud.devyard.cloudcollab.model.Organization;
import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.repository.FolderRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.FolderService;
import cloud.devyard.cloudcollab.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final OrganizationService organizationService;

    @Override
    @Transactional
    public FolderResponse createFolder(CreateFolderRequest folderRequest, Long userId, Long organizationId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Organization organization = organizationService.getOrganizationById(organizationId);

        Folder parentFolder = null;
        if(folderRequest.getParentFolderId() != null){
            parentFolder = folderRepository.findById(folderRequest.getParentFolderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Folder not Found."));
        }

        Folder folder = Folder.builder()
                .name(folderRequest.getName())
                .description(folderRequest.getDescription())
                .color(folderRequest.getColor())
                .parentFolder(parentFolder)
                .organization(organization)
                .createdBy(user)
                .isShared(false)
                .build();

        Folder saved = folderRepository.save(folder);
        return mapToResponse(saved);
    }

    public List<FolderResponse> getRootFolders(Long organizationId){
        return folderRepository.findByOrganizationIdAndParentFolderIsNullAndDeletedAtIsNull(organizationId)
                .stream().map(this::mapToResponse)
                .toList();
    }

    public List<FolderResponse> getSubFolders(Long parentFolderId) {
        return folderRepository.findByParentFolderIdAndDeletedAtIsNull(parentFolderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private FolderResponse mapToResponse(Folder saved) {

        return FolderResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .color(saved.getColor())
                .parentFolderId(saved.getParentFolder() != null ?
                        saved.getParentFolder().getId() : null )
                .createdById(saved.getCreatedBy().getId())
                .createdByName(saved.getCreatedBy().getFirstName() + " " +
                        saved.getCreatedBy().getLastName())
                .isShared(saved.getIsShared())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}
