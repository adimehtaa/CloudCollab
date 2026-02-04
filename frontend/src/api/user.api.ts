import type { 
  UserProfileResponse,
  UserProfileUpdateRequest,
  UserPreferencesResponse,
  UserPreferences,
  ChangePasswordRequest,
  ChangePasswordResponse,
  AvatarUploadResponse,
  InviteUserRequest,
  InviteUserResponse,
  UserInvitationsResponse
} from "../types/user.type";
import apiClient from "./axios";

export const userAPI = {

  // Profile management
  getCurrentUser() {
    return apiClient.get<UserProfileResponse>("/v1/users/me");
  },

  getUserById(userId: number) {
    return apiClient.get<UserProfileResponse>(`/v1/users/${userId}`);
  },

  updateProfile(data: UserProfileUpdateRequest) {
    return apiClient.put<UserProfileResponse>('/v1/users/me', data);
  },

  uploadAvatar(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return apiClient.post<AvatarUploadResponse>('/v1/users/me/avatar', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  changePassword(data: ChangePasswordRequest) {
    return apiClient.put<ChangePasswordResponse>('/v1/users/me/password', data);
  },

  // Preferences
  getPreferences() {
    return apiClient.get<UserPreferencesResponse>('/v1/users/me/preferences');
  },

  updatePreferences(data: UserPreferences) {
    return apiClient.put<UserPreferencesResponse>('/v1/users/me/preferences', data);
  },

  // Invitations
  inviteUser(data: InviteUserRequest) {
    return apiClient.post<InviteUserResponse>('/v1/users/invite', data);
  },

  getInvitations() {
    return apiClient.get<UserInvitationsResponse>('/v1/users/invitations');
  },

  cancelInvitation(invitationId: number) {
    return apiClient.delete<ChangePasswordResponse>(`/v1/users/invitations/${invitationId}`);
  },
};