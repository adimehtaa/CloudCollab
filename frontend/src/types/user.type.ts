import type { ApiResponse } from "./ApiResponse";

// Base User Profile Interface (matching your API response)
export interface UserProfile {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  avatarUrl: string | null;
  bio: string | null;
  phoneNumber: string | null;
  jobTitle: string | null;
  department: string | null;
  location: string | null;
  dateOfBirth: string | null;
  emailVerified: boolean;
  active: boolean;
  organizationId: number | null;
  organizationName: string | null;
  roles: string[];
  createdAt: string;
  lastLoginAt: string | null;
  passwordChange: string | null;
}

// User Profile Update Request (partial update)
export interface UserProfileUpdateRequest {
  firstName?: string;
  lastName?: string;
  bio?: string;
  phoneNumber?: string;
  jobTitle?: string;
  department?: string;
  location?: string;
  dateOfBirth?: string;
}

// Password Change Request
export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}

// User Preferences
export interface UserPreferences {
  theme?: 'light' | 'dark' | 'system';
  language?: string;
  timezone?: string;
  notifications?: {
    email?: boolean;
    push?: boolean;
    sms?: boolean;
  };
  privacy?: {
    profileVisibility?: 'public' | 'private' | 'organization';
    showEmail?: boolean;
    showPhone?: boolean;
  };
  [key: string]: any; // Allow for additional custom preferences
}

// User Invitation
export interface UserInvitation {
  id: number;
  email: string;
  role: string;
  status: 'pending' | 'accepted' | 'rejected' | 'expired';
  invitedBy: string;
  invitedAt: string;
  expiresAt: string;
}

// Invite User Request
export interface InviteUserRequest {
  email: string;
  role: string;
  message?: string;
}

// API Response Types
export type UserProfileResponse = ApiResponse<UserProfile>;
export type UserPreferencesResponse = ApiResponse<UserPreferences>;
export type UserInvitationsResponse = ApiResponse<UserInvitation[]>;
export type InviteUserResponse = ApiResponse<UserInvitation>;

// Avatar Upload Response
export interface AvatarUploadData {
  avatarUrl: string;
}
export type AvatarUploadResponse = ApiResponse<AvatarUploadData>;

// Password Change Response
export type ChangePasswordResponse = ApiResponse<{
  message: string;
}>;