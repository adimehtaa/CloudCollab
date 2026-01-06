import apiClient from './axios';
import type {LoginRequest, LoginResponse, JwtResponse, SignupRequest } from '../types/auth';
import type { ApiResponse } from '../types/ApiResponse';

export const authAPI = {
  login(credentials: LoginRequest) {
    return apiClient.post<LoginResponse>(
      '/v1/auth/login',
      credentials
    );
  },

  signup(userData: SignupRequest) {
    return apiClient.post<ApiResponse<null>>(
      '/v1/auth/signup',
      userData
    );
  },

  refreshToken(refreshToken: string) {
    return apiClient.post<ApiResponse<JwtResponse>>(
      '/v1/auth/refresh',
      { refreshToken }
    );
  },

  logout() {
    return apiClient.post<ApiResponse<null>>(
      '/v1/auth/logout'
    );
  },
};
