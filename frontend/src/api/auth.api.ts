import apiClient from './axios';

export const authAPI = {
  login(credentials:unknown) {
    return apiClient.post('/v1/auth/login', credentials);
  },
  
  signup(userData:unknown) {
    return apiClient.post('/v1/auth/signup', userData);
  },
  
  refreshToken(refreshToken:string) {
    return apiClient.post('/v1/auth/refresh', { refreshToken });
  },
  
  logout() {
    return apiClient.post('/v1/auth/logout');
  },
};