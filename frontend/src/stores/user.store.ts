import { defineStore } from "pinia";
import { ref } from "vue";
import { userAPI } from "../api/user.api";
import type { ApiResponse } from "../types/ApiResponse";

export const useUserStore = defineStore('user' , () => {

    const profile = ref(null);
    const preferences = ref(null);
    const loading = ref(false);
    const error = ref(null);

    async function fetchProfile() {
        try {
            loading.value = true;
            error.value = null;
            const response= userAPI.getCurrentUser();
            // profile.value = response;
            // return response.data;
            
        } catch (err : any) {
            error.value = err.response?.data?.message || 'Failed to fetch profile';
            throw err;
        }finally{
            loading.value = false
        }
    }
})