import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { themes, getThemeById, applyTheme, saveThemePreference, getThemePreference, type Theme } from '../config/themes'
import { useUserStore } from './user.store'

export const useThemeStore = defineStore('theme', () => {
    const currentThemeId = ref<string>(getThemePreference())
    const userStore = useUserStore()

    const currentTheme = computed<Theme>(() => {
        return getThemeById(currentThemeId.value) ?? themes[0]!
    })

    const availableThemes = computed(() => themes)

    const setTheme = async (themeId: string, saveToBackend: boolean = true) => {
        const theme = getThemeById(themeId)
        if (theme) {
            currentThemeId.value = themeId
            applyTheme(theme)
            saveThemePreference(themeId)
            
            // Save to backend if user is logged in
            if (saveToBackend && userStore.preferences) {
                try {
                    await userStore.updatePreferences({
                        ...userStore.preferences,
                        theme: themeId
                    })
                } catch (error) {
                    console.error('Failed to save theme to backend:', error)
                }
            }
        }
    }

    const initTheme = () => {
        // First check if user has backend preference
        if (userStore.preferences?.theme) {
            currentThemeId.value = userStore.preferences.theme
        }
        
        const theme = currentTheme.value
        applyTheme(theme)
    }

    return {
        currentThemeId,
        currentTheme,
        availableThemes,
        setTheme,
        initTheme
    }
})