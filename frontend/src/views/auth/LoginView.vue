<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-100 px-4">
        <div class="w-full max-w-md bg-white p-8 rounded-xl shadow-md space-y-6">
            <!-- Header -->
            <div>
                <h2 class="text-center text-3xl font-extrabold text-gray-900">
                    Sign in to CloudCollab
                </h2>
                <p class="mt-2 text-center text-sm text-gray-600">
                    Or
                    <span class="font-medium text-primary-600 hover:text-primary-500 cursor-pointer">
                        create a new account
                    </span>
                </p>
            </div>

            <!-- Error -->
            <div v-if="error" class="rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
                {{ error }}
            </div>

            <!-- Form -->
            <form class="space-y-6" @submit.prevent="handleLogin">
                <div class="space-y-4">
                    <!-- Username / Email -->
                    <div>
                        <label for="username" class="block text-sm font-medium text-gray-700 mb-1">
                            Email or Username
                        </label>
                        <input id="username" v-model="form.usernameOrEmail" type="text" required :disabled="loading"
                            class="input-field" placeholder="Enter your email or username" />
                    </div>

                    <!-- Password -->
                    <div>
                        <label for="password" class="block text-sm font-medium text-gray-700 mb-1">
                            Password
                        </label>
                        <input id="password" v-model="form.password" type="password" required :disabled="loading"
                            class="input-field" placeholder="Enter your password" />
                    </div>
                </div>

                <!-- Remember / Forgot -->
                <div class="flex items-center justify-between">
                    <label class="flex items-center space-x-2 text-sm text-gray-600">
                        <input type="checkbox"
                            class="h-4 w-4 rounded border-gray-300 text-primary-600 focus:ring-primary-500" />
                        <span>Remember me</span>
                    </label>

                    <a href="#" class="text-sm font-medium text-primary-600 hover:text-primary-500">
                        Forgot password?
                    </a>
                </div>

                <!-- Submit -->
                <button type="submit" :disabled="loading"
                    class="w-full flex justify-center rounded-lg bg-primary-600 px-4 py-2 text-sm font-semibold text-white hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed">
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

