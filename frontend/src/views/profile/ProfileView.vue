<template>
  <div class="min-h-screen bg-theme-secondary">
    <div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="mb-6">
        <h1 class="text-3xl font-bold text-theme-primary">My Profile</h1>
        <p class="text-theme-secondary">Manage your personal information</p>
      </div>
      
      <!-- Tabs -->
      <div class="bg-theme-elevated shadow-theme-md rounded-theme-lg mb-6">
        <div class="border-b border-theme-light">
          <nav class="flex -mb-px">
            <button
              v-for="tab in tabs"
              :key="tab.id"
              @click="activeTab = tab.id"
              :class="[
                'py-4 px-6 text-sm font-medium border-b-2 tab-button',
                activeTab === tab.id ? 'tab-active' : 'border-transparent text-theme-secondary'
              ]"
            >
              {{ tab.name }}
            </button>
          </nav>
        </div>
      </div>
      
      <!-- Tab Content -->
      <div class="card">
        <ProfileTab v-if="activeTab === 'profile'" />
        <SecurityTab v-else-if="activeTab === 'security'" />
        <PreferencesTab v-else-if="activeTab === 'preferences'" />
        <ActivityTab v-else-if="activeTab === 'activity'" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '../../stores/user.store';
import ProfileTab from '../profile/ProfileTab.vue';
import SecurityTab from '../profile/SecurityTab.vue';
import PreferencesTab from '../profile/PreferencesTab.vue';
import ActivityTab from '../profile/ActivityTab.vue';

const userStore = useUserStore();

const tabs = [
  { id: 'profile', name: 'Profile Information' },
  { id: 'security', name: 'Security' },
  { id: 'preferences', name: 'Preferences' },
  { id: 'activity', name: 'Activity Log' },
];

const activeTab = ref('profile');


onMounted(async () => {
  await userStore.fetchProfile();
});
</script>