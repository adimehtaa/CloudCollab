import type { User } from "../types/auth";

const TOKEN_KEY = 'accessToken';
const REFRESH_TOKEN_KEY = 'refreshToken';
const USER_KEY = 'user';

export const storage = {
  // Token management
  getToken() {
    return localStorage.getItem(TOKEN_KEY);
  },
  
  setToken(token :string) {
    localStorage.setItem(TOKEN_KEY, token);
  },
  
  removeToken() {
    localStorage.removeItem(TOKEN_KEY);
  },
  
  // Refresh token management
  getRefreshToken() {
    return localStorage.getItem(REFRESH_TOKEN_KEY);
  },
  
  setRefreshToken(token: string) {
    localStorage.setItem(REFRESH_TOKEN_KEY, token);
  },
  
  removeRefreshToken() {
    localStorage.removeItem(REFRESH_TOKEN_KEY);
  },
  
  // User data management
  getUser() {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  },
  
  setUser(user: User) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  },
  
  removeUser() {
    localStorage.removeItem(USER_KEY);
  },
  
  // Clear all
  clearAll() {
    this.removeToken();
    this.removeRefreshToken();
    this.removeUser();
  },
};