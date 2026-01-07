import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { authAPI } from "../api/auth.api";
import { storage } from "../utils/storage";
import type { JwtResponse, LoginRequest, SignupRequest, User, UserResponse } from "../types/auth";

export const useAuthStore = defineStore('auth', () => {
    const accessToken = ref<string | null>(storage.getToken() ?? null)
    const refreshToken = ref<string | null>(storage.getRefreshToken() ?? null)
    const user = ref<User | null>(storage.getUser() ?? null)
    const loading = ref(false)
    const error = ref<string | null>(null)

    const isAuthenticated = computed(() => !!accessToken.value && !!user.value)

    const currentUser = computed(() => user.value)

    const userRoles = computed<readonly string[]>(() => {
        return user.value?.roles ?? []
    })

    const isAdmin = computed(() => userRoles.value.includes('ROLE_ADMIN'))

    const isSuperAdmin = computed(() =>
        userRoles.value.includes('ROLE_SUPER_ADMIN')
    )

    async function login(credentials: LoginRequest): Promise<JwtResponse> {
        try {
            loading.value = true
            error.value = null

            const { data } = await authAPI.login(credentials)
            const payload = data.data

            accessToken.value = payload.accessToken
            refreshToken.value = payload.refreshToken

            user.value = {
                id: payload.id,
                username: payload.username,
                email: payload.email,
                firstName: payload.firstName,
                lastName: payload.lastName,
                avatarUrl: payload.avatarUrl ?? null,
                organizationId: payload.organizationId ?? null,
                organizationName: payload.organizationName ?? null,
                roles: payload.roles ?? []
            }

            storage.setToken(payload.accessToken)
            storage.setRefreshToken(payload.refreshToken)
            storage.setUser(user.value)

            return payload
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Login failed'
            throw err
        } finally {
            loading.value = false
        }
    }

    async function signup(request: SignupRequest): Promise<UserResponse> {
        try {
            loading.value = true;
            error.value = null;

            const { data } = await authAPI.signup(request);
            const payload: UserResponse = data.data;

            return payload;
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Signup failed';
            throw err;
        } finally {
            loading.value = false;
        }
    }

    async function refreshTokenAction() { }

    async function logout(): Promise<void> {
        try {
            if (accessToken.value) {
                await authAPI.logout();
            }
        } catch {
            // ignore backend errors (token may already be invalid)
        } finally {
            accessToken.value = null;
            refreshToken.value = null;
            user.value = null;
            storage.clearAll();
        }
    }

    return {
        accessToken,
        refreshToken,
        user,
        loading,
        error,
        isAuthenticated,
        currentUser,
        userRoles,
        isAdmin,
        isSuperAdmin,
        login,
        signup,
        refreshTokenAction,
        logout
    }
})
