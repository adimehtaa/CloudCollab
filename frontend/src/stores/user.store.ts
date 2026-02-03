import { defineStore } from "pinia";
import { ref } from "vue";
import { userAPI } from "../api/user.api";

export const useUserStore = defineStore('user', () => {

    const profile = ref(null);
    const preferences = ref(null);
    const loading = ref(false);
    const error = ref(null);

    async function fetchProfile() {
        try {
            loading.value = true;
            error.value = null;

            const response = await userAPI.getCurrentUser();
            profile.value = response.data.data;
            return await response.data;

        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch profile';
            throw err;
        } finally {
            loading.value = false
        }
    };

    async function updateProfile(data: any) {
        try {
            loading.value = true
            error.value = null

            const response = await userAPI.updateProfile(data)
            profile.value = response.data.data
            return response.data;

        } catch (err: any) {
            error.value =
                err.response?.data?.message || 'Failed to update profile'
            throw err
        } finally {
            loading.value = false
        }
    }

    async function uploadAvatar(file:any) {
        try {
            loading.value = true;
            error.value = null;
            const response = await userAPI.uploadAvatar(file);

            await fetchProfile();
            return response.data;
        } catch (err : any) {
            error.value = err.response?.data?.message || 'Failed to upload avatar';
            throw err;
        } finally {
            loading.value = false;
        }

    }

    async function changePassword(data :unknown) {
        try {
            loading.value = true;
            error.value = null;
            const response = await userAPI.changePassword(data);
            return response.data.data;
        } catch (err : any) {
            error.value = err.response?.data?.message || 'Failed to change password';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function fetchPreferences(){
        try {
            const response = await userAPI.getPreferences();
            preferences.value = response.data.data;
            return response.data;
        } catch (err :any) {
            error.value = err.response?.data?.message || 'Failed to fetch preferences';
            throw err;
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
        fetchPreferences
    }
})