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
                    <div class="flex items-center">
                        <!-- Profile Dropdown -->
                        <div class="relative" ref="profileDropdownRef">
                            <!-- Avatar Button -->
                            <button @click="toggleProfileDropdown" 
                                    class="flex items-center space-x-2 transition-all duration-200"
                                    :style="{
                                        padding: '4px',
                                        borderRadius: '50%',
                                        border: isProfileDropdownOpen ? '2px solid var(--accent-primary)' : '2px solid transparent'
                                    }">
                                <div class="flex items-center justify-center" 
                                     style="width: 40px; 
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
                            </button>

                            <!-- Dropdown Menu -->
                            <transition name="dropdown">
                                <div v-if="isProfileDropdownOpen" 
                                     @click.stop 
                                     class="absolute right-0 mt-2 w-72 p-2"
                                     style="background-color: var(--bg-elevated);
                                            border-radius: var(--radius-lg);
                                            box-shadow: var(--shadow-lg);
                                            border: 1px solid var(--border-light);
                                            z-index: 50;">
                                    
                                    <!-- User Info Section -->
                                    <div class="px-3 py-3 border-b" style="border-color: var(--border-light);">
                                        <div class="flex items-center space-x-3">
                                            <div class="flex items-center justify-center" 
                                                 style="width: 48px; 
                                                        height: 48px; 
                                                        background-color: var(--bg-tertiary); 
                                                        border-radius: 50%;">
                                                <span style="color: var(--text-primary); 
                                                            font-weight: 600;">
                                                    {{ userInitials }}
                                                </span>
                                            </div>
                                            <div class="flex-1 min-w-0">
                                                <p class="text-sm font-semibold truncate" 
                                                   style="color: var(--text-primary);">
                                                    {{ user?.firstName }} {{ user?.lastName }}
                                                </p>
                                                <p class="text-xs truncate" 
                                                   style="color: var(--text-tertiary);">
                                                    {{ user?.email }}
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Menu Items -->
                                    <div class="py-2">
                                        <!-- Profile Option -->
                                        <button @click="goToProfile" 
                                                class="w-full flex items-center space-x-3 px-3 py-2 text-sm transition-all duration-200"
                                                :style="{
                                                    color: 'var(--text-primary)',
                                                    backgroundColor: hoveredItem === 'profile' ? 'var(--bg-tertiary)' : 'transparent',
                                                    borderRadius: 'var(--radius-md)'
                                                }"
                                                @mouseenter="hoveredItem = 'profile'"
                                                @mouseleave="hoveredItem = null">
                                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" 
                                                 stroke="currentColor" stroke-width="2">
                                                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                                                <circle cx="12" cy="7" r="4" />
                                            </svg>
                                            <span class="font-medium">Profile</span>
                                        </button>

                                        <!-- Theme Option with Submenu -->
                                        <div class="relative">
                                            <button @click="toggleThemeSubmenu" 
                                                    class="w-full flex items-center justify-between px-3 py-2 text-sm transition-all duration-200"
                                                    :style="{
                                                        color: 'var(--text-primary)',
                                                        backgroundColor: hoveredItem === 'theme' ? 'var(--bg-tertiary)' : 'transparent',
                                                        borderRadius: 'var(--radius-md)'
                                                    }"
                                                    @mouseenter="hoveredItem = 'theme'"
                                                    @mouseleave="hoveredItem = null">
                                                <div class="flex items-center space-x-3">
                                                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" 
                                                         stroke="currentColor" stroke-width="2">
                                                        <circle cx="12" cy="12" r="4" />
                                                        <path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41" />
                                                    </svg>
                                                    <span class="font-medium">Theme</span>
                                                </div>
                                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" 
                                                     stroke="currentColor" stroke-width="2"
                                                     :style="{
                                                         transform: isThemeSubmenuOpen ? 'rotate(180deg)' : 'rotate(0deg)',
                                                         transition: 'transform 0.2s'
                                                     }">
                                                    <polyline points="6 9 12 15 18 9" />
                                                </svg>
                                            </button>

                                            <!-- Theme Submenu -->
                                            <transition name="submenu">
                                                <div v-if="isThemeSubmenuOpen" 
                                                     class="mt-1 ml-2 pl-4 border-l-2 max-h-64 overflow-y-auto custom-scrollbar" 
                                                     style="border-color: var(--border-light);">
                                                    <button v-for="theme in availableThemes" 
                                                            :key="theme.id"
                                                            @click="selectTheme(theme.id)"
                                                            @mouseenter="previewTheme(theme.id)"
                                                            @mouseleave="resetPreview"
                                                            class="w-full transition-all duration-200"
                                                            :style="{
                                                                backgroundColor: hoveredTheme === theme.id ? 'var(--bg-tertiary)' : 'transparent',
                                                                borderRadius: 'var(--radius-md)',
                                                                marginBottom: '4px'
                                                            }">
                                                        <div class="flex items-center justify-between px-3 py-2 text-sm">
                                                            <div class="flex items-center space-x-2">
                                                                <!-- Color Preview Dots -->
                                                                <div class="flex space-x-1">
                                                                    <div class="w-3 h-3 rounded-full" 
                                                                         :style="{ backgroundColor: theme.cssVars['--accent-primary'] }">
                                                                    </div>
                                                                    <div class="w-3 h-3 rounded-full" 
                                                                         :style="{ backgroundColor: theme.cssVars['--bg-tertiary'] }">
                                                                    </div>
                                                                </div>
                                                                <span class="font-medium text-xs" style="color: var(--text-primary);">
                                                                    {{ theme.name }}
                                                                </span>
                                                            </div>
                                                            <div v-if="themeStore.currentTheme.id === theme.id"
                                                                 class="w-2 h-2 rounded-full"
                                                                 style="background-color: var(--accent-primary);">
                                                            </div>
                                                        </div>
                                                    </button>
                                                </div>
                                            </transition>
                                        </div>

                                        <!-- Divider -->
                                        <div class="my-2 border-t" style="border-color: var(--border-light);"></div>

                                        <!-- Logout Option -->
                                        <button @click="handleLogout" 
                                                class="w-full flex items-center space-x-3 px-3 py-2 text-sm transition-all duration-200"
                                                :style="{
                                                    color: hoveredItem === 'logout' ? 'var(--status-error)' : 'var(--text-primary)',
                                                    backgroundColor: hoveredItem === 'logout' ? 'rgba(239, 68, 68, 0.08)' : 'transparent',
                                                    borderRadius: 'var(--radius-md)'
                                                }"
                                                @mouseenter="hoveredItem = 'logout'"
                                                @mouseleave="hoveredItem = null">
                                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" 
                                                 stroke="currentColor" stroke-width="2">
                                                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                                                <polyline points="16 17 21 12 16 7" />
                                                <line x1="21" y1="12" x2="9" y2="12" />
                                            </svg>
                                            <span class="font-medium">Logout</span>
                                        </button>
                                    </div>
                                </div>
                            </transition>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Main -->
        <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
            <section class="px-4 py-6 sm:px-0">
                <div class="card">
                    <!-- Header -->
                    <div class="flex justify-between items-center mb-6">
                        <div>
                            <h2 class="text-2xl font-bold" style="color: var(--text-primary);">
                                Welcome back, {{ user?.firstName }}! 👋
                            </h2>
                            <p class="text-sm mt-1" style="color: var(--text-tertiary);">
                                Here's what's happening with your projects today.
                            </p>
                        </div>
                        <div class="text-sm px-3 py-1" 
                             style="background-color: var(--bg-tertiary);
                                    color: var(--text-secondary);
                                    border-radius: var(--radius-md);
                                    border: 1px solid var(--border-light);">
                            {{ themeStore.currentTheme.name }}
                        </div>
                    </div>

                    <!-- Stats -->
                    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
                        <div v-for="stat in stats" 
                             :key="stat.label" 
                             class="p-6 transition-all duration-200 hover:shadow-lg" 
                             :style="{
                                 backgroundColor: stat.bg,
                                 borderRadius: 'var(--radius-md)',
                                 border: `1px solid ${stat.border}`,
                                 cursor: 'default'
                             }">
                            <div class="flex items-center justify-between mb-3">
                                <h3 class="text-sm font-medium"
                                    style="color: var(--text-tertiary); text-transform: uppercase; letter-spacing: 0.05em;">
                                    {{ stat.label }}
                                </h3>
                                <div v-html="stat.icon" 
                                     class="opacity-50"
                                     :style="{ color: stat.valueColor }">
                                </div>
                            </div>

                            <p class="text-3xl font-bold mb-2" :style="{ color: stat.valueColor }">
                                {{ stat.value }}
                            </p>

                            <p class="text-xs" style="color: var(--text-tertiary);">
                                {{ stat.trend }}
                            </p>
                        </div>
                    </div>

                    <!-- Recent Activity & User Info Grid -->
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                        <!-- Recent Activity -->
                        <section class="p-6" 
                                 style="background-color: var(--bg-secondary); 
                                        border-radius: var(--radius-md);
                                        border: 1px solid var(--border-light);">
                            <h3 class="text-lg font-semibold mb-4" style="color: var(--text-primary);">
                                Recent Activity
                            </h3>

                            <div class="space-y-3">
                                <div v-for="activity in recentActivities" 
                                     :key="activity.id"
                                     class="flex items-start space-x-3 p-3 transition-all duration-200 hover:bg-opacity-50"
                                     :style="{
                                         backgroundColor: 'var(--bg-tertiary)',
                                         borderRadius: 'var(--radius-md)',
                                         border: '1px solid var(--border-light)'
                                     }">
                                    <div class="flex-shrink-0 mt-1" 
                                         v-html="activity.icon"
                                         :style="{ color: activity.color }">
                                    </div>
                                    <div class="flex-1 min-w-0">
                                        <p class="text-sm font-medium" style="color: var(--text-primary);">
                                            {{ activity.title }}
                                        </p>
                                        <p class="text-xs mt-1" style="color: var(--text-tertiary);">
                                            {{ activity.time }}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </section>

                        <!-- User Info -->
                        <section class="p-6" 
                                 style="background-color: var(--bg-secondary); 
                                        border-radius: var(--radius-md);
                                        border: 1px solid var(--border-light);">
                            <h3 class="text-lg font-semibold mb-4" style="color: var(--text-primary);">
                                User Information
                            </h3>

                            <dl class="space-y-4">
                                <InfoItem label="Username" :value="user?.username" />
                                <InfoItem label="Email" :value="user?.email" />
                                <InfoItem label="Organization" :value="user?.organizationName || 'No organization'" />

                                <div>
                                    <dt class="text-sm font-medium" style="color: var(--text-tertiary);">
                                        Roles
                                    </dt>
                                    <dd class="mt-2">
                                        <span v-for="role in roles" 
                                              :key="role"
                                              class="inline-block px-3 py-1 text-xs mr-2 mb-1 font-medium" 
                                              style="background-color: var(--bg-tertiary); 
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
                </div>
            </section>
        </main>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import InfoItem from '../../ui/cards/InfoItem.vue'
import { useAuthStore } from '../../stores/auth.store'
import { useThemeStore } from '../../stores/theme.store'
import { themes, applyTheme, type Theme } from '../../config/themes'

const authStore = useAuthStore()
const themeStore = useThemeStore()
const router = useRouter()

// Initialize theme on mount
onMounted(() => {
    themeStore.initTheme()
    document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
    document.removeEventListener('click', handleClickOutside)
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

// Profile dropdown state
const isProfileDropdownOpen = ref(false)
const isThemeSubmenuOpen = ref(false)
const profileDropdownRef = ref<HTMLElement | null>(null)
const hoveredItem = ref<string | null>(null)
const hoveredTheme = ref<string | null>(null)

// Available themes
const availableThemes = ref(themes)

// Theme preview
const previewTimeout = ref<number | null>(null)
const currentPreviewTheme = ref<string | null>(null)

const toggleProfileDropdown = () => {
    isProfileDropdownOpen.value = !isProfileDropdownOpen.value
    if (!isProfileDropdownOpen.value) {
        isThemeSubmenuOpen.value = false
        resetPreview()
    }
}

const toggleThemeSubmenu = () => {
    isThemeSubmenuOpen.value = !isThemeSubmenuOpen.value
    if (!isThemeSubmenuOpen.value) {
        resetPreview()
    }
}

const previewTheme = (themeId: string) => {
    hoveredTheme.value = themeId
    
    // Clear any existing timeout
    if (previewTimeout.value) {
        clearTimeout(previewTimeout.value)
    }
    
    // Set a small delay before applying preview to avoid rapid theme changes
    previewTimeout.value = window.setTimeout(() => {
        const theme = themes.find(t => t.id === themeId)
        if (theme) {
            currentPreviewTheme.value = themeId
            applyTheme(theme)
        }
    }, 150) // 150ms delay for smoother experience
}

const resetPreview = () => {
    hoveredTheme.value = null
    
    // Clear timeout if exists
    if (previewTimeout.value) {
        clearTimeout(previewTimeout.value)
        previewTimeout.value = null
    }
    
    // Reset to current saved theme only if we were previewing a different theme
    if (currentPreviewTheme.value && currentPreviewTheme.value !== themeStore.currentTheme.id) {
        applyTheme(themeStore.currentTheme)
    }
    currentPreviewTheme.value = null
}

const selectTheme = (themeId: string) => {
    themeStore.setTheme(themeId)
    currentPreviewTheme.value = null
    
    // Clear timeout
    if (previewTimeout.value) {
        clearTimeout(previewTimeout.value)
        previewTimeout.value = null
    }
    
    // Close menus after selection
    setTimeout(() => {
        isThemeSubmenuOpen.value = false
        isProfileDropdownOpen.value = false
    }, 200)
}

const handleClickOutside = (event: MouseEvent) => {
    if (profileDropdownRef.value && !profileDropdownRef.value.contains(event.target as Node)) {
        isProfileDropdownOpen.value = false
        isThemeSubmenuOpen.value = false
        resetPreview()
    }
}

const goToProfile = () => {
    isProfileDropdownOpen.value = false
    isThemeSubmenuOpen.value = false
    router.push('/profile')
}

async function handleLogout() {
    try {
        isProfileDropdownOpen.value = false
        await authStore.logout()
        router.push('/login')
    } catch (error) {
        console.error('Logout failed:', error)
        // Force logout even if API call fails
        authStore.logout()
        router.push('/login')
    }
}

const stats = computed(() => [
    {
        label: 'Projects',
        value: 12,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--accent-primary)',
        trend: '+3 this month',
        icon: '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>'
    },
    {
        label: 'Tasks',
        value: 47,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--status-success)',
        trend: '23 completed',
        icon: '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="9 11 12 14 22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>'
    },
    {
        label: 'Team Members',
        value: 8,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--accent-light)',
        trend: '+2 this week',
        icon: '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>'
    },
    {
        label: 'Messages',
        value: 156,
        bg: 'var(--bg-tertiary)',
        border: 'var(--border-light)',
        valueColor: 'var(--status-info)',
        trend: '12 unread',
        icon: '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>'
    }
])

const recentActivities = ref([
    {
        id: 1,
        title: 'New project "Mobile App Redesign" created',
        time: '2 hours ago',
        color: 'var(--accent-primary)',
        icon: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>'
    },
    {
        id: 2,
        title: 'Task "Update documentation" completed',
        time: '5 hours ago',
        color: 'var(--status-success)',
        icon: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="20 6 9 17 4 12"/></svg>'
    },
    {
        id: 3,
        title: 'Sarah Johnson joined your team',
        time: '1 day ago',
        color: 'var(--accent-light)',
        icon: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="8.5" cy="7" r="4"/><line x1="20" y1="8" x2="20" y2="14"/><line x1="23" y1="11" x2="17" y2="11"/></svg>'
    },
    {
        id: 4,
        title: '3 new messages in project chat',
        time: '2 days ago',
        color: 'var(--status-info)',
        icon: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>'
    }
])
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active,
.submenu-enter-active,
.submenu-leave-active {
    transition: all 0.2s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
    opacity: 0;
    transform: translateY(-10px);
}

.submenu-enter-from,
.submenu-leave-to {
    opacity: 0;
    max-height: 0;
    overflow: hidden;
}

.submenu-enter-to,
.submenu-leave-from {
    opacity: 1;
    max-height: 500px;
}

.custom-scrollbar::-webkit-scrollbar {
    width: 6px;
}

.custom-scrollbar::-webkit-scrollbar-track {
    background: var(--bg-secondary);
    border-radius: var(--radius-sm);
}

.custom-scrollbar::-webkit-scrollbar-thumb {
    background: var(--border-medium);
    border-radius: var(--radius-sm);
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
    background: var(--border-dark);
}
</style>