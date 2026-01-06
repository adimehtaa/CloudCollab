<template>
    <div class="min-h-screen" style="background-color: var(--bg-secondary);">
        <!-- Navbar -->
        <nav style="background-color: var(--bg-elevated); 
                    border-bottom: 1px solid var(--border-light);">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="flex justify-between items-center" style="height: 64px;">
                    <!-- Logo/Brand -->
                    <div class="flex items-center space-x-3">
                        <div class="flex items-center justify-center" style="width: 36px; 
                                    height: 36px; 
                                    background-color: var(--accent-primary); 
                                    border-radius: var(--radius-md);">
                            <span style="color: var(--text-inverse); 
                                        font-weight: 700; 
                                        font-size: 1.25rem;">
                                C
                            </span>
                        </div>
                        <h1 class="text-xl font-bold" style="color: var(--text-primary); letter-spacing: -0.025em;">
                            CloudCollab
                        </h1>
                    </div>

                    <!-- User Actions -->
                    <div class="flex items-center space-x-4">
                        <!-- Theme Selector -->
                        <!-- <ThemeSelector /> -->

                        <!-- User Info -->
                        <div v-if="user" class="flex items-center space-x-3">
                            <div class="text-right hidden md:block">
                                <p class="text-sm font-medium" style="color: var(--text-primary);">
                                    {{ user.firstName }} {{ user.lastName }}
                                </p>
                                <p class="text-xs" style="color: var(--text-tertiary);">
                                    {{ user.email }}
                                </p>
                            </div>

                            <!-- Avatar -->
                            <div class="flex items-center justify-center" style="width: 40px; 
                                        height: 40px; 
                                        background-color: var(--bg-tertiary); 
                                        border-radius: 50%; 
                                        border: 2px solid var(--border-light);">
                                <span style="color: var(--text-primary); 
                                            font-weight: 600; 
                                            font-size: 0.875rem;">
                                    {{ userInitials }}
                                </span>
                            </div>
                        </div>

                        <!-- Logout Button -->
                        <button @click="handleLogout" class="text-sm font-medium px-4 py-2" :style="{
                            color: isLogoutHovered ? 'var(--status-error)' : 'var(--text-secondary)',
                            backgroundColor: isLogoutHovered ? 'rgba(239, 68, 68, 0.08)' : 'transparent',
                            borderRadius: 'var(--radius-md)',
                            transition: 'all 0.2s ease',
                            border: '1px solid transparent'
                        }" @mouseenter="isLogoutHovered = true" @mouseleave="isLogoutHovered = false">
                            Logout
                        </button>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Main -->
        <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
            <section class="px-4 py-6 sm:px-0">
                <div class="card">
                    <!-- Header with Theme Info -->
                    <div class="flex justify-between items-center mb-6">
                        <h2 class="text-2xl font-bold" style="color: var(--text-primary);">
                            Dashboard
                        </h2>
                        <div class="text-sm px-3 py-1" style="background-color: var(--bg-tertiary);
                                    color: var(--text-secondary);
                                    border-radius: var(--radius-md);
                                    border: 1px solid var(--border-light);">
                            {{ themeStore.currentTheme.name }}
                        </div>
                    </div>

                    <!-- Stats -->
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                        <div v-for="stat in stats" :key="stat.label" class="p-6 transition-all duration-200" :style="{
                            backgroundColor: stat.bg,
                            borderRadius: 'var(--radius-md)',
                            border: `1px solid ${stat.border}`,
                            cursor: 'default'
                        }">
                            <h3 class="text-sm font-medium mb-2"
                                style="color: var(--text-tertiary); text-transform: uppercase; letter-spacing: 0.05em;">
                                {{ stat.label }}
                            </h3>

                            <p class="text-3xl font-bold" :style="{ color: stat.valueColor }">
                                {{ stat.value }}
                            </p>
                        </div>
                    </div>

                    <!-- User Info -->
                    <section class="p-6" style="background-color: var(--bg-secondary); 
                                    border-radius: var(--radius-md);
                                    border: 1px solid var(--border-light);">
                        <h3 class="text-lg font-semibold mb-4" style="color: var(--text-primary);">
                            User Information
                        </h3>

                        <dl class="grid grid-cols-1 sm:grid-cols-2 gap-4">
                            <InfoItem label="Username" :value="user?.username" />
                            <InfoItem label="Email" :value="user?.email" />
                            <InfoItem label="Organization" :value="user?.organizationName || 'No organization'" />

                            <div>
                                <dt class="text-sm font-medium" style="color: var(--text-tertiary);">
                                    Roles
                                </dt>
                                <dd class="mt-1">
                                    <span v-for="role in roles" :key="role"
                                        class="inline-block px-3 py-1 text-xs mr-2 mb-1 font-medium" style="background-color: var(--bg-tertiary); 
                                               color: var(--text-primary); 
                                               border-radius: var(--radius-sm);
                                               border: 1px solid var(--border-light);">
                                        {{ role }}
                                    </span>
                                </dd>
                            </div>
                        </dl>
                    </section>
                </div>
            </section>
        </main>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import InfoItem from '../../ui/cards/InfoItem.vue'
// import ThemeSelector from '../../ui/theme/ThemeSelector.vue'
import { authAPI } from '../../api/auth.api'
import { useAuthStore } from '../../stores/auth.store'
import { useThemeStore } from '../../stores/theme.store'

const authStore = useAuthStore()
const themeStore = useThemeStore()
const router = useRouter()

// Initialize theme on mount
onMounted(() => {
    themeStore.initTheme()
})

const user = computed(() => authStore.currentUser)

const userInitials = computed(() => {
    if (!user.value) return '?'
    const first = user.value.firstName?.[0] || ''
    const last = user.value.lastName?.[0] || ''
    return (first + last).toUpperCase() || user.value.username?.[0]?.toUpperCase() || '?'
})

const roles = computed(() =>
    authStore.userRoles.map(role =>
        role.replace('ROLE_', '')
    )
)

const isLogoutHovered = ref(false)

const stats = computed(() => [
    {
        label: 'Projects',
        value: 0,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--accent-primary)'
    },
    {
        label: 'Tasks',
        value: 0,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--status-success)'
    },
    {
        label: 'Team Members',
        value: 1,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--accent-light)'
    }
])

async function handleLogout() {
    try {
        await authAPI.logout()
    } finally {
        router.push('/login')
    }
}
</script>