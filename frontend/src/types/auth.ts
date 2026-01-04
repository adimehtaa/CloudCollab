import type { ApiResponse } from "./ApiResponse";

export interface LoginRequest{
    usernameOrEmail:string
    password: string
}

export interface JwtResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: 'Bearer';
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  avatarUrl: string | null;
  organizationId: string;
  organizationName: string;
  roles: string[];
}

export interface User {
  id: string
  username: string
  email: string
  firstName: string
  lastName: string
  avatarUrl?: string | null
  organizationId?: string | null
  organizationName?: string | null
  roles: string[]
}


export type LoginResponse = ApiResponse<JwtResponse>;
