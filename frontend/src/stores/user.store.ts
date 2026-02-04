import { defineStore } from "pinia";
import { ref } from "vue";
import { userAPI } from "../api/user.api";
import type {
    UserProfile,
    UserProfileUpdateRequest,
    UserPreferences,
    ChangePasswordRequest
} from "../types/user.type";

export const useUserStore = defineStore('user', () => {

    const profile = ref<UserProfile | null>(null);
    const preferences = ref<UserPreferences | null>(null);
    const loading = ref<boolean>(false);
    const error = ref<string | null>(null);

    async function fetchProfile() {
        try {
            loading.value = true;
            error.value = null;

            const response = await userAPI.getCurrentUser();
            profile.value = response.data.data;
            return response.data;

        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch profile';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function updateProfile(data: UserProfileUpdateRequest) {
        try {
            loading.value = true;
            error.value = null;

            const response = await userAPI.updateProfile(data);
            profile.value = response.data.data;
            return response.data;

        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to update profile';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function uploadAvatar(file: File) {
        try {
            loading.value = true;
            error.value = null;
            const response = await userAPI.uploadAvatar(file);

            await fetchProfile();
            return response.data;
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to upload avatar';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function changePassword(data: ChangePasswordRequest) {
        try {
            loading.value = true;
            error.value = null;
            const response = await userAPI.changePassword(data);
            return response.data.data;
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to change password';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function fetchPreferences() {
        try {
            const response = await userAPI.getPreferences();
            preferences.value = response.data.data;
            return response.data;
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch preferences';
            throw err;
        }
    }

    async function updatePreferences(data: UserPreferences) {
        try {
            loading.value = true;
            error.value = null;
            const response = await userAPI.updatePreferences(data);
            preferences.value = response.data.data;
            return response.data;
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to update preferences';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    return {
        profile,
        preferences,
        loading,
        error,
        fetchProfile,
        updateProfile,
        uploadAvatar,
        changePassword,
        fetchPreferences,
        updatePreferences
    };
});