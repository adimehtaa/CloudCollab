export interface Theme {
    id: string
    name: string
    description: string
    cssVars: Record<string, string>
}

export const themes: Theme[] = [
    {
        id: 'slate-mono',
        name: 'Slate Mono',
        description: 'Professional monochrome theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#f8f9fa',
            '--bg-tertiary': '#e9ecef',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#1a202c',
            '--text-secondary': '#4a5568',
            '--text-tertiary': '#718096',
            '--text-inverse': '#ffffff',

            '--border-light': '#e2e8f0',
            '--border-medium': '#cbd5e0',
            '--border-dark': '#a0aec0',

            '--accent-primary': '#2d3748',
            '--accent-hover': '#1a202c',
            '--accent-light': '#4a5568',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'ocean-blue',
        name: 'Ocean Blue',
        description: 'Calm and professional blue theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#f0f9ff',
            '--bg-tertiary': '#e0f2fe',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#0c4a6e',
            '--text-secondary': '#075985',
            '--text-tertiary': '#0284c7',
            '--text-inverse': '#ffffff',

            '--border-light': '#bae6fd',
            '--border-medium': '#7dd3fc',
            '--border-dark': '#38bdf8',

            '--accent-primary': '#0ea5e9',
            '--accent-hover': '#0284c7',
            '--accent-light': '#38bdf8',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(14, 165, 233, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(14, 165, 233, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(14, 165, 233, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'forest-green',
        name: 'Forest Green',
        description: 'Natural and calming green theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#f0fdf4',
            '--bg-tertiary': '#dcfce7',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#14532d',
            '--text-secondary': '#166534',
            '--text-tertiary': '#16a34a',
            '--text-inverse': '#ffffff',

            '--border-light': '#bbf7d0',
            '--border-medium': '#86efac',
            '--border-dark': '#4ade80',

            '--accent-primary': '#22c55e',
            '--accent-hover': '#16a34a',
            '--accent-light': '#4ade80',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(34, 197, 94, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(34, 197, 94, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(34, 197, 94, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'sunset-orange',
        name: 'Sunset Orange',
        description: 'Warm and energetic orange theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#fff7ed',
            '--bg-tertiary': '#ffedd5',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#7c2d12',
            '--text-secondary': '#9a3412',
            '--text-tertiary': '#c2410c',
            '--text-inverse': '#ffffff',

            '--border-light': '#fed7aa',
            '--border-medium': '#fdba74',
            '--border-dark': '#fb923c',

            '--accent-primary': '#f97316',
            '--accent-hover': '#ea580c',
            '--accent-light': '#fb923c',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(249, 115, 22, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(249, 115, 22, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(249, 115, 22, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'royal-purple',
        name: 'Royal Purple',
        description: 'Elegant and sophisticated purple theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#faf5ff',
            '--bg-tertiary': '#f3e8ff',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#581c87',
            '--text-secondary': '#6b21a8',
            '--text-tertiary': '#7e22ce',
            '--text-inverse': '#ffffff',

            '--border-light': '#e9d5ff',
            '--border-medium': '#d8b4fe',
            '--border-dark': '#c084fc',

            '--accent-primary': '#a855f7',
            '--accent-hover': '#9333ea',
            '--accent-light': '#c084fc',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(168, 85, 247, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(168, 85, 247, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(168, 85, 247, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'midnight-dark',
        name: 'Midnight Dark',
        description: 'Dark theme for reduced eye strain',
        cssVars: {
            '--bg-primary': '#1e1e2e',
            '--bg-secondary': '#181825',
            '--bg-tertiary': '#11111b',
            '--bg-elevated': '#1e1e2e',
            '--bg-overlay': 'rgba(0, 0, 0, 0.7)',

            '--text-primary': '#cdd6f4',
            '--text-secondary': '#bac2de',
            '--text-tertiary': '#a6adc8',
            '--text-inverse': '#1e1e2e',

            '--border-light': '#313244',
            '--border-medium': '#45475a',
            '--border-dark': '#585b70',

            '--accent-primary': '#89b4fa',
            '--accent-hover': '#74c7ec',
            '--accent-light': '#b4befe',

            '--status-success': '#a6e3a1',
            '--status-warning': '#f9e2af',
            '--status-error': '#f38ba8',
            '--status-info': '#89b4fa',

            '--shadow-sm': '0 1px 2px 0 rgba(0, 0, 0, 0.3)',
            '--shadow-md': '0 4px 6px -1px rgba(0, 0, 0, 0.4)',
            '--shadow-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.5)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'rose-pink',
        name: 'Rose Pink',
        description: 'Soft and modern pink theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#fff1f2',
            '--bg-tertiary': '#ffe4e6',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#881337',
            '--text-secondary': '#9f1239',
            '--text-tertiary': '#be123c',
            '--text-inverse': '#ffffff',

            '--border-light': '#fecdd3',
            '--border-medium': '#fda4af',
            '--border-dark': '#fb7185',

            '--accent-primary': '#f43f5e',
            '--accent-hover': '#e11d48',
            '--accent-light': '#fb7185',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(244, 63, 94, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(244, 63, 94, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(244, 63, 94, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'amber-gold',
        name: 'Amber Gold',
        description: 'Luxurious and warm gold theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#fffbeb',
            '--bg-tertiary': '#fef3c7',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#78350f',
            '--text-secondary': '#92400e',
            '--text-tertiary': '#b45309',
            '--text-inverse': '#ffffff',

            '--border-light': '#fde68a',
            '--border-medium': '#fcd34d',
            '--border-dark': '#fbbf24',

            '--accent-primary': '#f59e0b',
            '--accent-hover': '#d97706',
            '--accent-light': '#fbbf24',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(245, 158, 11, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(245, 158, 11, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(245, 158, 11, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'teal-cyan',
        name: 'Teal Cyan',
        description: 'Fresh and modern teal theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#f0fdfa',
            '--bg-tertiary': '#ccfbf1',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#134e4a',
            '--text-secondary': '#115e59',
            '--text-tertiary': '#0f766e',
            '--text-inverse': '#ffffff',

            '--border-light': '#99f6e4',
            '--border-medium': '#5eead4',
            '--border-dark': '#2dd4bf',

            '--accent-primary': '#14b8a6',
            '--accent-hover': '#0d9488',
            '--accent-light': '#2dd4bf',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(20, 184, 166, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(20, 184, 166, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(20, 184, 166, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    },
    {
        id: 'indigo-navy',
        name: 'Indigo Navy',
        description: 'Deep and professional navy theme',
        cssVars: {
            '--bg-primary': '#ffffff',
            '--bg-secondary': '#eef2ff',
            '--bg-tertiary': '#e0e7ff',
            '--bg-elevated': '#ffffff',
            '--bg-overlay': 'rgba(0, 0, 0, 0.5)',

            '--text-primary': '#312e81',
            '--text-secondary': '#3730a3',
            '--text-tertiary': '#4338ca',
            '--text-inverse': '#ffffff',

            '--border-light': '#c7d2fe',
            '--border-medium': '#a5b4fc',
            '--border-dark': '#818cf8',

            '--accent-primary': '#6366f1',
            '--accent-hover': '#4f46e5',
            '--accent-light': '#818cf8',

            '--status-success': '#10b981',
            '--status-warning': '#f59e0b',
            '--status-error': '#ef4444',
            '--status-info': '#3b82f6',

            '--shadow-sm': '0 1px 2px 0 rgba(99, 102, 241, 0.05)',
            '--shadow-md': '0 4px 6px -1px rgba(99, 102, 241, 0.1)',
            '--shadow-lg': '0 10px 15px -3px rgba(99, 102, 241, 0.1)',

            '--radius-sm': '4px',
            '--radius-md': '8px',
            '--radius-lg': '12px'
        }
    }
]

export function getThemeById(id: string): Theme | undefined {
    return themes.find(theme => theme.id === id)
}

export function applyTheme(theme: Theme): void {
    const root = document.documentElement
    Object.entries(theme.cssVars).forEach(([key, value]) => {
        root.style.setProperty(key, value)
    })
}

const THEME_STORAGE_KEY = 'cloudcollab-theme'

export function saveThemePreference(themeId: string): void {
    try {
        localStorage.setItem(THEME_STORAGE_KEY, themeId)
    } catch (error) {
        console.error('Failed to save theme preference:', error)
    }
}

export function getThemePreference(): string {
    try {
        return localStorage.getItem(THEME_STORAGE_KEY) || 'slate-mono'
    } catch (error) {
        console.error('Failed to get theme preference:', error)
        return 'slate-mono'
    }
}