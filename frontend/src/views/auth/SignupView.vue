<template>
    <div class="min-h-screen flex items-center justify-center px-4" style="background-color: var(--bg-secondary);">
        <div class="w-full max-w-md p-8 space-y-6"
            style="background-color: var(--bg-elevated); border-radius: var(--radius-lg); box-shadow: var(--shadow-md);">
            <!-- Header -->
            <div>
                <h2 class="text-center text-3xl font-extrabold" style="color: var(--text-primary);">
                    Sign up to CloudCollab
                </h2>
                <p class="mt-2 text-center text-sm" style="color: var(--text-secondary);">
                    Already have an account?
                    <router-link to="/login" class="font-medium link-hover"
                        :style="{ color: isSignInHovered ? 'var(--accent-hover)' : 'var(--accent-primary)' }"
                        @mouseenter="isSignInHovered = true" @mouseleave="isSignInHovered = false">
                        Sign in
                    </router-link>
                </p>
            </div>

            <!-- Error -->
            <div v-if="error" class="px-4 py-3 text-sm" style="border-radius: var(--radius-md);
                border: 1px solid var(--status-error);
                background-color: rgba(239, 68, 68, 0.1);
                color: var(--status-error);">
                {{ error }}
            </div>

            <!-- Validation Errors -->
            <div v-if="validationErrors.length" class="px-4 py-3" style="border-radius: var(--radius-md);
                border: 1px solid var(--status-error);
                background-color: rgba(239, 68, 68, 0.1);">
                <ul class="list-disc list-inside text-sm space-y-1" style="color: var(--status-error);">
                    <li v-for="err in validationErrors" :key="err">{{ err }}</li>
                </ul>
            </div>

            <!-- Form -->
            <form class="space-y-6" @submit.prevent="handleSignup">
                <div class="space-y-4">
                    <!-- Username -->
                    <div>
                        <label for="username" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Username *
                        </label>
                        <input id="username" v-model="form.username" type="text" required :disabled="loading"
                            class="input-field" placeholder="Choose a username" />
                    </div>

                    <!-- Email -->
                    <div>
                        <label for="email" class="block text-sm font-medium mb-1" style="color: var(--text-secondary);">
                            Email *
                        </label>
                        <input id="email" v-model="form.email" type="email" required :disabled="loading"
                            class="input-field" placeholder="your@email.com" />
                    </div>

                    <!-- First Name -->
                    <div>
                        <label for="firstName" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            First Name *
                        </label>
                        <input id="firstName" v-model="form.firstName" type="text" required :disabled="loading"
                            class="input-field" placeholder="John" />
                    </div>

                    <!-- Last Name -->
                    <div>
                        <label for="lastName" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Last Name *
                        </label>
                        <input id="lastName" v-model="form.lastName" type="text" required :disabled="loading"
                            class="input-field" placeholder="Doe" />
                    </div>

                    <!-- Password -->
                    <div>
                        <label for="password" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Password *
                        </label>
                        <input id="password" v-model="form.password" type="password" required :disabled="loading"
                            class="input-field" placeholder="Min. 6 characters" />
                    </div>

                    <!-- Confirm Password -->
                    <div>
                        <label for="confirmPassword" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Confirm Password *
                        </label>
                        <input id="confirmPassword" v-model="form.confirmPassword" type="password" required
                            :disabled="loading" class="input-field" placeholder="Re-enter password" />
                    </div>

                    <!-- Organization Name (Optional) -->
                    <div>
                        <label for="organizationName" class="block text-sm font-medium mb-1"
                            style="color: var(--text-secondary);">
                            Organization Name (Optional)
                        </label>
                        <input id="organizationName" v-model="form.organizationName" type="text" :disabled="loading"
                            class="input-field" placeholder="Your company name" />
                        <p class="mt-1 text-xs" style="color: var(--text-tertiary);">
                            Create a new organization or leave blank to join later
                        </p>
                    </div>
                </div>

                <!-- Terms acceptance -->
                <div class="flex items-start">
                    <input id="terms" v-model="form.acceptTerms" type="checkbox" required :disabled="loading"
                        class="h-4 w-4 mt-1" style="border-radius: var(--radius-sm);
                                      border: 1px solid var(--border-medium);
                                      accent-color: var(--accent-primary);" />
                    <label for="terms" class="ml-2 block text-sm" style="color: var(--text-secondary);">
                        I agree to the
                        <a href="#" class="link-hover"
                            :style="{ color: isTermsHovered ? 'var(--accent-hover)' : 'var(--accent-primary)' }"
                            @mouseenter="isTermsHovered = true" @mouseleave="isTermsHovered = false">
                            Terms of Service
                        </a>
                        and
                        <a href="#" class="link-hover"
                            :style="{ color: isPrivacyHovered ? 'var(--accent-hover)' : 'var(--accent-primary)' }"
                            @mouseenter="isPrivacyHovered = true" @mouseleave="isPrivacyHovered = false">
                            Privacy Policy
                        </a>
                    </label>
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
                    <span v-if="!loading">Create account</span>
                    <span v-else>Creating account...</span>
                </button>
            </form>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../../stores/auth.store';
import type { SignupFormData } from '../../types/auth';

const router = useRouter();
const authStore = useAuthStore();

const loading = ref(false);
const error = ref('');
const validationErrors = ref<string[]>([]);

const form = ref<SignupFormData>({
    username: '',
    email: '',
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    organizationName: '',
    acceptTerms: false,
});

// Hover states
const isSignInHovered = ref(false);
const isTermsHovered = ref(false);
const isPrivacyHovered = ref(false);
const isButtonHovered = ref(false);

function validateForm(): boolean {
    validationErrors.value = [];

    if (form.value.username.length < 3) {
        validationErrors.value.push('Username must be at least 3 characters');
    }

    if (form.value.password.length < 6) {
        validationErrors.value.push('Password must be at least 6 characters');
    }

    if (form.value.password !== form.value.confirmPassword) {
        validationErrors.value.push('Passwords do not match');
    }

    if (!form.value.acceptTerms) {
        validationErrors.value.push('You must accept the terms and conditions');
    }

    return validationErrors.value.length === 0;
}

async function handleSignup() {
    if (!validateForm()) {
        return;
    }

    try {
        loading.value = true;
        error.value = '';
        validationErrors.value = [];

        const { confirmPassword, acceptTerms, organizationName, ...signupData } = form.value;

        const apiData = organizationName.trim()
            ? { ...signupData, organizationName }
            : signupData;

        await authStore.signup(apiData);
        router.push('/dashboard');
    } catch (err: any) {
        if (err.response?.data?.errors) {
            const errors = err.response.data.errors;
            validationErrors.value = Object.values(errors);
        } else {
            error.value = err.response?.data?.message ||
                'Signup failed. Please try again.';
        }
    } finally {
        loading.value = false;
    }
}
</script>

<style scoped>
.link-hover {
    transition: color 0.2s ease;
}
</style>