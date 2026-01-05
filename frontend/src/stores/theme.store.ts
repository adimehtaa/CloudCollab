import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { themes, getThemeById, applyTheme, saveThemePreference, getThemePreference, type Theme } from '../config/themes'

export const useThemeStore = defineStore('theme', () => {
    const currentThemeId = ref<string>(getThemePreference())

    const currentTheme = computed<Theme>(() => {
        return getThemeById(currentThemeId.value) ?? themes[0]!
    })


    const availableThemes = computed(() => themes)

    const setTheme = (themeId: string) => {
        const theme = getThemeById(themeId)
        if (theme) {
            currentThemeId.value = themeId
            applyTheme(theme)
            saveThemePreference(themeId)
        }
    }

    const initTheme = () => {
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