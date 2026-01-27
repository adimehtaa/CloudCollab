import type { ApiResponse } from "../types/ApiResponse";
import apiClient from "./axios"

export const userAPI = {

    // Profile management
    getCurrentUser(){
        return apiClient.get("/v1/users/me")
    },

    getUserById(userId : number) {
        return apiClient.get<ApiResponse<any>>(`/v1/users/${userId}`);
    },

    updateProfile(data : unknown){
        return apiClient.put<ApiResponse<any>>('/v1/users/me', data)
    },

    uploadAvatar(file : any){
        const formData = new FormData();
        formData.append('file', file);
        return apiClient.post<ApiResponse<any>>('/v1/users/me/avatar' , formData, {
            headers:{
                'Content-Type': 'multipart/form-data',
            },
        });
    },

    changePassword(data :any) {
        return apiClient.put<ApiResponse<any>>('/v1/users/me/password', data);
    },

    // Preferences
    getPreferences() {
        return apiClient.get<ApiResponse<any>>('/v1/users/me/preferences');
    },
  
    updatePreferences(data :any) {
        return apiClient.put<ApiResponse<any>>('/v1/users/me/preferences', data);
    },

    // Invitations
    inviteUser(data : any) {
        return apiClient.post<ApiResponse<any>>('/v1/users/invite', data);
    },
    
    getInvitations() {
        return apiClient.get<ApiResponse<any>>('/v1/users/invitations');
    },

    cancelInvitation(invitationId : number) {
        return apiClient.delete<ApiResponse<any>>(`/v1/users/invitations/${invitationId}`);
    },
}