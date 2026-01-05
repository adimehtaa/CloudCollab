<template>
    <div class="min-h-screen flex items-center justify-center px-4" style="background-color: var(--bg-secondary);">
        <div class="w-full max-w-md p-8 space-y-6" style="background-color: var(--bg-elevated); 
                    border-radius: var(--radius-lg); 
                    box-shadow: var(--shadow-md);">
            <!-- Header -->
            <div>
                <h2 class="text-center text-3xl font-extrabold" style="color: var(--text-primary);">
                    Sign in to CloudCollab
                </h2>
                <p class="mt-2 text-center text-sm" style="color: var(--text-secondary);">
                    Or
                    <span class="font-medium cursor-pointer link-hover"
                        :style="{ color: isCreateHovered ? 'var(--accent-hover)' : 'var(--accent-primary)' }"
                        @mouseenter="isCreateHovered = true" @mouseleave="isCreateHovered = false">
                        create a new account
                    </span>
                </p>
            </div>

            <!-- Error -->
            <div v-if="error" class="px-4 py-3 text-sm" style="border-radius: var(--radius-md); 
                        border: 1px solid var(--status-error); 
                        background-color: rgba(239, 68, 68, 0.1); 
                        color: var(--status-error);">
                {{ error }}
            </div>

            <!-- Form -->
            <form class="space-y-6" @submit.prevent="handleLogin">
                <div class="space-y-4">
                    <!-- Username / Email -->
                    <div>
                        <label for="username" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Email or Username
                        </label>
                        <input id="username" v-model="form.usernameOrEmail" type="text" required :disabled="loading"
                            class="input-field" placeholder="Enter your email or username" />
                    </div>

                    <!-- Password -->
                    <div>
                        <label for="password" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Password
                        </label>
                        <input id="password" v-model="form.password" type="password" required :disabled="loading"
                            class="input-field" placeholder="Enter your password" />
                    </div>
                </div>

                <!-- Remember / Forgot -->
                <div class="flex items-center justify-between">
                    <label class="flex items-center space-x-2 text-sm" style="color: var(--text-secondary);">
                        <input type="checkbox" class="h-4 w-4" style="border-radius: var(--radius-sm); 
                                      border: 1px solid var(--border-medium); 
                                      accent-color: var(--accent-primary);" />
                        <span>Remember me</span>
                    </label>

                    <a href="#" class="text-sm font-medium link-hover"
                        :style="{ color: isForgotHovered ? 'var(--accent-hover)' : 'var(--accent-primary)' }"
                        @mouseenter="isForgotHovered = true" @mouseleave="isForgotHovered = false">
                        Forgot password?
                    </a>
                </div>

                <!-- Submit -->
                <button type="submit" :disabled="loading"
                    class="w-full flex justify-center px-4 py-2 text-sm font-semibold" :style="{
                        borderRadius: 'var(--radius-md)',
                        backgroundColor: loading ? 'var(--accent-primary)' : (isButtonHovered ? 'var(--accent-hover)' : 'var(--accent-primary)'),
                        color: 'var(--text-inverse)',
                        opacity: loading ? '0.6' : '1',
                        cursor: loading ? 'not-allowed' : 'pointer',
                        transition: 'all 0.2s ease'
                    }" @mouseenter="!loading && (isButtonHovered = true)" @mouseleave="isButtonHovered = false">
                    <span v-if="!loading">Sign in</span>
                    <span v-else>Signing in...</span>
                </button>
            </form>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth.store'
import type { LoginRequest } from '../../types/auth'

const router = useRouter()
const authStore = useAuthStore()

const form = ref<LoginRequest>({
    usernameOrEmail: '',
    password: ''
})

const loading = ref(false)
const error = ref('')

// Hover states
const isCreateHovered = ref(false)
const isForgotHovered = ref(false)
const isButtonHovered = ref(false)

async function handleLogin() {
    try {
        loading.value = true
        error.value = ''

        await authStore.login(form.value)
        router.push('/dashboard')
    } catch (err: any) {
        error.value =
            err.response?.data?.message ||
            'Login failed. Please try again.'
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.link-hover {
    transition: color 0.2s ease;
}

</style
