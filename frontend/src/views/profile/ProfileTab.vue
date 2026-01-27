<template>
  <div class="space-y-6">
    <!-- Success/Error Messages -->
    <div v-if="successMessage" class="alert alert-success">
      {{ successMessage }}
    </div>
    
    <div v-if="errorMessage" class="alert alert-error">
      {{ errorMessage }}
    </div>

    <!-- Avatar Section -->
    <div class="flex items-center space-x-6">
      <div class="relative">
        <img
          :src="avatarUrl || defaultAvatar"
          alt="Avatar"
          class="w-24 h-24 rounded-full object-cover border-4 border-theme-light"
        />
        <label
          for="avatar-upload"
          class="absolute bottom-0 right-0 p-2 rounded-full cursor-pointer avatar-upload-btn"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
        </label>
        <input
          id="avatar-upload"
          type="file"
          accept="image/*"
          @change="handleAvatarUpload"
          class="hidden"
        />
      </div>
      
      <div>
        <h3 class="text-lg font-semibold text-theme-primary">Profile Picture</h3>
        <p class="text-sm text-theme-secondary">JPG, PNG or GIF. Max size 5MB</p>
      </div>
    </div>

    <!-- Profile Form -->
    <form @submit.prevent="handleSubmit" class="space-y-6">
      <!-- Basic Information -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Basic Information</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              First Name *
            </label>
            <input
              v-model="form.firstName"
              type="text"
              required
              class="input-field"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Last Name *
            </label>
            <input
              v-model="form.lastName"
              type="text"
              required
              class="input-field"
            />
          </div>
          
          <div class="md:col-span-2">
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Bio
            </label>
            <textarea
              v-model="form.bio"
              rows="4"
              class="input-field"
              placeholder="Tell us about yourself..."
            ></textarea>
          </div>
        </div>
      </div>

      <!-- Contact Information -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Contact Information</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Phone Number
            </label>
            <input
              v-model="form.phoneNumber"
              type="tel"
              class="input-field"
              placeholder="+1 (555) 123-4567"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Location
            </label>
            <input
              v-model="form.location"
              type="text"
              class="input-field"
              placeholder="City, Country"
            />
          </div>
        </div>
      </div>

      <!-- Professional Information -->
      <div>
        <h3 class="text-lg font-semibold mb-4 text-theme-primary">Professional Information</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Job Title
            </label>
            <input
              v-model="form.jobTitle"
              type="text"
              class="input-field"
              placeholder="Software Engineer"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Department
            </label>
            <input
              v-model="form.department"
              type="text"
              class="input-field"
              placeholder="Engineering"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium mb-1 text-theme-secondary">
              Date of Birth
            </label>
            <input
              v-model="form.dateOfBirth"
              type="date"
              class="input-field"
            />
          </div>
        </div>
      </div>

      <!-- Actions -->
      <div class="flex justify-end space-x-4">
        <button
          type="button"
          @click="resetForm"
          class="btn-secondary"
        >
          Cancel
        </button>
        <button
          type="submit"
          :disabled="loading"
          class="btn-primary"
        >
          {{ loading ? 'Saving...' : 'Save Changes' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useUserStore } from '../../stores/user.store';

const userStore = useUserStore();

const form = ref({
  firstName: '',
  lastName: '',
  bio: '',
  phoneNumber: '',
  location: '',
  jobTitle: '',
  department: '',
  dateOfBirth: '',
});

const loading = ref(false);
const successMessage = ref('');
const errorMessage = ref('');

const defaultAvatar = 'https://ui-avatars.com/api/?name=User&background=2d3748&color=fff&size=200';

const avatarUrl = computed(() => {
  const avatar = userStore.profile?.avatarUrl;
  if (!avatar) return null;

  if (avatar.startsWith('/')) {
    return (
      import.meta.env.VITE_API_BASE_URL.replace('/v1', '') + avatar
    );
  }
  return avatar;
});


watch(
  () => userStore.profile,
  (profile) => {
    if (profile) loadProfileData();
  },
  { immediate: true }
);


function loadProfileData() {
  const profile = userStore.profile;
  form.value = {
    firstName: profile.firstName || '',
    lastName: profile.lastName || '',
    bio: profile.bio || '',
    phoneNumber: profile.phoneNumber || '',
    location: profile.location || '',
    jobTitle: profile.jobTitle || '',
    department: profile.department || '',
    dateOfBirth: profile.dateOfBirth || '',
  };
}

async function handleSubmit() {
  try {
    loading.value = true;
    successMessage.value = '';
    errorMessage.value = '';
    
    await userStore.updateProfile(form.value);
    
    successMessage.value = 'Profile updated successfully!';
    setTimeout(() => {
      successMessage.value = '';
    }, 3000);
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Failed to update profile';
  } finally {
    loading.value = false;
  }
}

async function handleAvatarUpload(event) {
  const file = event.target.files[0];
  if (!file) return;
  
  if (file.size > 5 * 1024 * 1024) {
    errorMessage.value = 'File size must be less than 5MB';
    return;
  }
  
  if (!file.type.startsWith('image/')) {
    errorMessage.value = 'Please upload an image file';
    return;
  }
  
  try {
    loading.value = true;
    errorMessage.value = '';
    successMessage.value = '';
    
    await userStore.uploadAvatar(file);
    
    successMessage.value = 'Avatar uploaded successfully!';
    setTimeout(() => {
      successMessage.value = '';
    }, 3000);
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Failed to upload avatar';
  } finally {
    loading.value = false;
  }
}

function resetForm() {
  loadProfileData();
  successMessage.value = '';
  errorMessage.value = '';
}
</script>