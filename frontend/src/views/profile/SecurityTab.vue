<template>
  <div class="space-y-6">
    <div>
      <h3 class="text-lg font-semibold mb-4 text-theme-primary">Change Password</h3>
      <p class="text-sm mb-6 text-theme-secondary">
        Ensure your account is using a strong password to stay secure.
      </p>
    </div>

    <!-- Success/Error Messages -->
    <div v-if="successMessage" class="alert alert-success">
      {{ successMessage }}
    </div>
    
    <div v-if="errorMessage" class="alert alert-error">
      {{ errorMessage }}
    </div>

    <form @submit.prevent="handleChangePassword" class="space-y-6">
      <div>
        <label class="block text-sm font-medium mb-1 text-theme-secondary">
          Current Password *
        </label>
        <input
          v-model="form.currentPassword"
          type="password"
          required
          class="input-field"
          placeholder="Enter your current password"
        />
      </div>

      <div>
        <label class="block text-sm font-medium mb-1 text-theme-secondary">
          New Password *
        </label>
        <input
          v-model="form.newPassword"
          type="password"
          required
          minlength="6"
          class="input-field"
          placeholder="Enter new password (min. 6 characters)"
        />
      </div>

      <div>
        <label class="block text-sm font-medium mb-1 text-theme-secondary">
          Confirm New Password *
        </label>
        <input
          v-model="form.confirmPassword"
          type="password"
          required
          class="input-field"
          placeholder="Re-enter new password"
        />
      </div>

      <!-- Password Strength Indicator -->
      <div v-if="form.newPassword" class="space-y-2">
        <div class="flex items-center space-x-2">
          <div class="flex-1 bg-theme-tertiary rounded-theme-sm h-2">
            <div
              class="h-2 rounded-theme-sm transition-all"
              :style="{ 
                width: passwordStrength.width,
                backgroundColor: passwordStrength.color
              }"
            ></div>
          </div>
          <span class="text-sm font-medium" :style="{ color: passwordStrength.color }">
            {{ passwordStrength.label }}
          </span>
        </div>
        <ul class="text-xs space-y-1 text-theme-secondary">
          <li :class="{ 'text-success': form.newPassword.length >= 6 }">
            ✓ At least 6 characters
          </li>
          <li :class="{ 'text-success': /[A-Z]/.test(form.newPassword) }">
            ✓ Contains uppercase letter
          </li>
          <li :class="{ 'text-success': /[0-9]/.test(form.newPassword) }">
            ✓ Contains number
          </li>
        </ul>
      </div>

      <div class="flex justify-end space-x-4">
        <button
          type="button"
          @click="resetPasswordForm"
          class="btn-secondary"
        >
          Cancel
        </button>
        <button
          type="submit"
          :disabled="loading || !isPasswordValid"
          class="btn-primary"
        >
          {{ loading ? 'Changing...' : 'Change Password' }}
        </button>
      </div>
    </form>

    <!-- Recent Security Activity -->
    <div class="mt-8 pt-8 border-t border-theme-light">
      <h3 class="text-lg font-semibold mb-4 text-theme-primary">Recent Security Activity</h3>
      <div class="space-y-3">
        <div class="flex items-center justify-between p-4 bg-theme-secondary rounded-theme-md">
          <div class="flex items-center space-x-3">
            <div class="p-2 rounded-full" style="background-color: #d1fae5;">
              <svg class="w-5 h-5 text-success" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div>
              <p class="text-sm font-medium text-theme-primary">Last Login</p>
              <p class="text-xs text-theme-tertiary">You logged in successfully</p>
            </div>
          </div>
          <span class="text-xs text-theme-tertiary">{{ lastLoginAt }}</span>
        </div>
        <div class="flex items-center justify-between p-4 bg-theme-secondary rounded-theme-md">
          <div class="flex items-center space-x-3">
            <div class="p-2 rounded-full" style="background-color: #d1fae5;">
              <svg class="w-5 h-5 text-success" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div>
              <p class="text-sm font-medium text-theme-primary">Password changed</p>
              <p class="text-xs text-theme-tertiary">Successfully updated your password</p>
            </div>
          </div>
          <span class="text-xs text-theme-tertiary">2 hours ago</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useUserStore } from '../../stores/user.store';
import { calculateHourDay } from '../../utils/datetime';

const userStore = useUserStore();
const lastLoginAt = computed(()=>{
  return calculateHourDay(userStore.profile.lastLoginAt);
})

const form = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
});

const loading = ref(false);
const successMessage = ref('');
const errorMessage = ref('');

const passwordStrength = computed(() => {
  const password = form.value.newPassword;
  let strength = 0;
  
  if (password.length >= 6) strength++;
  if (password.length >= 10) strength++;
  if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
  if (/[0-9]/.test(password)) strength++;
  if (/[^a-zA-Z0-9]/.test(password)) strength++;
  
  if (strength <= 2) {
    return { width: '33%', color: '#ef4444', label: 'Weak' };
  } else if (strength <= 3) {
    return { width: '66%', color: '#f59e0b', label: 'Medium' };
  } else {
    return { width: '100%', color: '#10b981', label: 'Strong' };
  }
});

const isPasswordValid = computed(() => {
  return (
    form.value.currentPassword &&
    form.value.newPassword.length >= 6 &&
    form.value.newPassword === form.value.confirmPassword
  );
});

async function handleChangePassword() {
  if (form.value.newPassword !== form.value.confirmPassword) {
    errorMessage.value = 'Passwords do not match';
    return;
  }
  
  try {
    loading.value = true;
    successMessage.value = '';
    errorMessage.value = '';
    
    await userStore.changePassword(form.value);
    
    successMessage.value = 'Password changed successfully!';
    resetPasswordForm();
    
    setTimeout(() => {
      successMessage.value = '';
    }, 3000);
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Failed to change password';
  } finally {
    loading.value = false;
  }
}

function resetPasswordForm() {
  form.value = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  };
  errorMessage.value = '';
}
</script>