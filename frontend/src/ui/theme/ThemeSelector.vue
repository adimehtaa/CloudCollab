<template>
    <div class="relative">
        <!-- Theme Button -->
        <button
            @click="isOpen = !isOpen"
            class="flex items-center space-x-2 px-3 py-2 text-sm font-medium"
            :style="{
                color: isOpen ? 'var(--accent-primary)' : 'var(--text-secondary)',
                backgroundColor: isOpen ? 'var(--bg-tertiary)' : 'transparent',
                borderRadius: 'var(--radius-md)',
                transition: 'all 0.2s ease',
                border: '1px solid transparent'
            }"
        >
            <!-- Icon -->
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10" />
                <circle cx="12" cy="12" r="1" />
                <circle cx="8" cy="8" r="1" />
                <circle cx="16" cy="8" r="1" />
                <circle cx="8" cy="16" r="1" />
                <circle cx="16" cy="16" r="1" />
            </svg>
            <span class="hidden sm:inline">Theme</span>
        </button>

        <!-- Dropdown -->
        <transition name="dropdown">
            <div
                v-if="isOpen"
                v-click-outside="closeDropdown"
                class="absolute right-0 mt-2 w-80 p-4"
                style="
                    background-color: var(--bg-elevated);
                    border-radius: var(--radius-lg);
                    box-shadow: var(--shadow-lg);
                    border: 1px solid var(--border-light);
                    z-index: 50;
                "
            >
                <h3 class="text-sm font-semibold mb-3" style="color: var(--text-primary);">
                    Choose Theme
                </h3>

                <div class="grid grid-cols-2 gap-3 max-h-96 overflow-y-auto custom-scrollbar">
                    <button
                        v-for="theme in themeStore.availableThemes"
                        :key="theme.id"
                        @click="selectTheme(theme.id)"
                        class="text-left p-3 transition-all duration-200"
                        :style="{
                            backgroundColor: isCurrentTheme(theme.id)
                                ? 'var(--bg-tertiary)'
                                : 'var(--bg-secondary)',
                            borderRadius: 'var(--radius-md)',
                            border: isCurrentTheme(theme.id)
                                ? '2px solid var(--accent-primary)'
                                : '2px solid transparent'
                        }"
                    >
                        <div class="flex space-x-1 mb-2">
                            <div class="w-6 h-6 rounded" :style="{ backgroundColor: theme.cssVars['--bg-primary'] }" />
                            <div class="w-6 h-6 rounded" :style="{ backgroundColor: theme.cssVars['--accent-primary'] }" />
                            <div class="w-6 h-6 rounded" :style="{ backgroundColor: theme.cssVars['--text-primary'] }" />
                        </div>

                        <p
                            class="text-sm font-semibold mb-1"
                            :style="{ color: isCurrentTheme(theme.id)
                                ? 'var(--accent-primary)'
                                : 'var(--text-primary)' }"
                        >
                            {{ theme.name }}
                        </p>

                        <p class="text-xs" style="color: var(--text-tertiary);">
                            {{ theme.description }}
                        </p>

                        <div
                            v-if="isCurrentTheme(theme.id)"
                            class="flex items-center mt-2 text-xs font-medium"
                            style="color: var(--accent-primary);"
                        >
                            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" class="mr-1">
                                <polyline points="20 6 9 17 4 12" />
                            </svg>
                            Active
                        </div>
                    </button>
                </div>
            </div>
        </transition>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useThemeStore } from '../../stores/theme.store'

/* ---------------- STATE ---------------- */
const themeStore = useThemeStore()
const isOpen = ref(false)

/* ---------------- METHODS ---------------- */
const isCurrentTheme = (themeId: string) =>
    themeStore.currentThemeId === themeId


const selectTheme = (themeId: string) => {
    themeStore.setTheme(themeId)
    isOpen.value = false
}

const closeDropdown = () => {
    isOpen.value = false
}

/* ---------------- CLICK OUTSIDE DIRECTIVE ---------------- */
interface ClickOutsideHTMLElement extends HTMLElement {
    clickOutsideEvent?: (event: Event) => void
}

const vClickOutside = {
    mounted(el: ClickOutsideHTMLElement, binding: { value: () => void }) {
        el.clickOutsideEvent = (event: Event) => {
            if (!el.contains(event.target as Node)) {
                binding.value()
            }
        }
        document.addEventListener('click', el.clickOutsideEvent)
    },
    unmounted(el: ClickOutsideHTMLElement) {
        if (el.clickOutsideEvent) {
            document.removeEventListener('click', el.clickOutsideEvent)
        }
    }
}
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
    transition: all 0.2s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
    opacity: 0;
    transform: translateY(-10px);
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
