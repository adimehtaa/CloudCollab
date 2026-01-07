import axios, { type AxiosInstance } from 'axios'
import { useAuthStore } from '../stores/auth.store';
import { router } from '../router';

const apiClient: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
})

// Request interceptor - Add token to requests
apiClient.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
    const token = authStore.accessToken;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor - Handle token refresh
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // If error is 401 and we haven't retried yet
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      const authStore = useAuthStore();

      try {
        // Try to refresh the token
        // await authStore.refreshToken();

        // Retry the original request with new token
        originalRequest.headers.Authorization = `Bearer ${authStore.accessToken}`;
        return apiClient(originalRequest);
      } catch (refreshError) {
        // Refresh failed, logout user
        authStore.logout();
        router.push('/login');
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export default apiClient
