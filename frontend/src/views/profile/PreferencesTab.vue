<template>
  <div class="space-y-6">
    <!-- Success/Error Messages -->
    <div v-if="successMessage" class="alert alert-success">
      {{ successMessage }}
    </div>
    
    <div v-if="errorMessage" class="alert alert-error">
      {{ errorMessage }}
    </div>

    <form @submit.prevent="handleSubmit" class="space-y-8">
      <!-- Notification Preferences -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Notification Preferences</h3>
        <div class="space-y-4">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">Email Notifications</p>
              <p class="text-xs text-theme-tertiary">Receive notifications via email</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.emailNotifications"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>

          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">Push Notifications</p>
              <p class="text-xs text-theme-tertiary">Receive push notifications in browser</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.pushNotifications"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>

          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">SMS Notifications</p>
              <p class="text-xs text-theme-tertiary">Receive important alerts via SMS</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.smsNotifications"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>
        </div>
      </div>

      <!-- Appearance -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Appearance</h3>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2 text-theme-secondary">Theme</label>
            <div class="grid grid-cols-3 gap-3">
              <button
                v-for="theme in themes"
                :key="theme.value"
                type="button"
                @click="form.theme = theme.value"
                :class="[
                  'p-4 text-center theme-btn',
                  form.theme === theme.value ? 'theme-active' : ''
                ]"
              >
                <svg class="w-6 h-6 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path v-if="theme.value === 'light'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                  <path v-else-if="theme.value === 'dark'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                  <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                </svg>
                <span class="text-sm font-medium">{{ theme.label }}</span>
              </button>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium mb-2 text-theme-secondary">Language</label>
            <select v-model="form.language" class="input-field">
              <option value="en">English</option>
              <option value="es">Spanish</option>
              <option value="fr">French</option>
              <option value="de">German</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium mb-2 text-theme-secondary">Timezone</label>
            <select v-model="form.timezone" class="input-field">
              <option value="UTC">UTC</option>
              <option value="America/New_York">Eastern Time</option>
              <option value="America/Chicago">Central Time</option>
              <option value="America/Denver">Mountain Time</option>
              <option value="America/Los_Angeles">Pacific Time</option>
              <option value="Europe/London">London</option>
              <option value="Asia/Tokyo">Tokyo</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Privacy Settings -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Privacy Settings</h3>
        <div class="space-y-4">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">Public Profile</p>
              <p class="text-xs text-theme-tertiary">Make your profile visible to everyone</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.profilePublic"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>

          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">Show Email</p>
              <p class="text-xs text-theme-tertiary">Display email address on profile</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.showEmail"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>

          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-theme-primary">Show Online Status</p>
              <p class="text-xs text-theme-tertiary">Let others see when you're online</p>
            </div>
            <label class="relative inline-flex items-center cursor-pointer">
              <input
                v-model="form.showOnlineStatus"
                type="checkbox"
                class="sr-only peer"
              />
              <div class="toggle-switch"></div>
            </label>
          </div>
        </div>
      </div>

      <!-- Actions -->
      <div class="flex justify-end space-x-4 pt-6 border-t border-theme-light">
        <button
          type="button"
          @click="loadPreferences"
          class="btn-secondary"
        >
          Reset
        </button>
        <button
          type="submit"
          :disabled="loading"
          class="btn-primary"
        >
          {{ loading ? 'Saving...' : 'Save Preferences' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '../../stores/user.store';

const userStore = useUserStore();

const form = ref({
  emailNotifications: true,
  pushNotifications: true,
  smsNotifications: false,
  theme: 'light',
  language: 'en',
  timezone: 'UTC',
  profilePublic: false,
  showEmail: false,
  showOnlineStatus: true,
});

const themes = [
  { value: 'light', label: 'Light' },
  { value: 'dark', label: 'Dark' },
  { value: 'auto', label: 'Auto' },
];

const loading = ref(false);
const successMessage = ref('');
const errorMessage = ref('');

onMounted(async () => {
  await loadPreferences();
});

async function loadPreferences() {
  try {
    const prefs = await userStore.fetchPreferences();
    form.value = { ...prefs };
  } catch (error) {
    console.error('Failed to load preferences:', error);
  }
}

async function handleSubmit() {
  try {
    loading.value = true;
    successMessage.value = '';
    errorMessage.value = '';
    
    await userStore.updatePreferences(form.value);
    
    successMessage.value = 'Preferences saved successfully!';
    setTimeout(() => {
      successMessage.value = '';
    }, 3000);
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Failed to save preferences';
  } finally {
    loading.value = false;
  }
}
</script>