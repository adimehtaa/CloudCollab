package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.CreateFolderRequest;
import cloud.devyard.cloudcollab.dto.response.FolderResponse;

import java.util.List;

public interface FolderService {
    public FolderResponse createFolder(CreateFolderRequest folderRequest , Long userId, Long organizationId);
    public List<FolderResponse> getRootFolders(Long organizationId);
    public List<FolderResponse> getSubFolders(Long parentFolderId);

}
