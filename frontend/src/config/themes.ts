export interface Theme {
  id: string
  name: string
  description: string
  cssVars: {
    // Backgrounds
    '--bg-primary': string
    '--bg-secondary': string
    '--bg-tertiary': string
    '--bg-elevated': string
    '--bg-overlay': string
    
    // Text
    '--text-primary': string
    '--text-secondary': string
    '--text-tertiary': string
    '--text-inverse': string
    
    // Borders
    '--border-light': string
    '--border-medium': string
    '--border-dark': string
    
    // Accent
    '--accent-primary': string
    '--accent-hover': string
    '--accent-light': string
    
    // Status
    '--status-success': string
    '--status-warning': string
    '--status-error': string
    '--status-info': string
    
    // Shadows
    '--shadow-sm': string
    '--shadow-md': string
    '--shadow-lg': string
    
    // Radius
    '--radius-sm': string
    '--radius-md': string
    '--radius-lg': string
  }
}

export const themes: Theme[] = [
  {
    id: 'slate-mono',
    name: 'Slate Mono',
    description: 'Clean minimalist gray theme',
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
    id: 'dark-slate',
    name: 'Dark Slate',
    description: 'Professional dark theme',
    cssVars: {
      '--bg-primary': '#0f172a',
      '--bg-secondary': '#1e293b',
      '--bg-tertiary': '#334155',
      '--bg-elevated': '#1e293b',
      '--bg-overlay': 'rgba(0, 0, 0, 0.7)',
      '--text-primary': '#f1f5f9',
      '--text-secondary': '#cbd5e1',
      '--text-tertiary': '#94a3b8',
      '--text-inverse': '#0f172a',
      '--border-light': '#334155',
      '--border-medium': '#475569',
      '--border-dark': '#64748b',
      '--accent-primary': '#60a5fa',
      '--accent-hover': '#3b82f6',
      '--accent-light': '#93c5fd',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#3b82f6',
      '--shadow-sm': '0 1px 2px 0 rgba(0, 0, 0, 0.3)',
      '--shadow-md': '0 4px 6px -1px rgba(0, 0, 0, 0.4)',
      '--shadow-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.5)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'ocean-breeze',
    name: 'Ocean Breeze',
    description: 'Calm blue-teal theme',
    cssVars: {
      '--bg-primary': '#f0f9ff',
      '--bg-secondary': '#e0f2fe',
      '--bg-tertiary': '#bae6fd',
      '--bg-elevated': '#ffffff',
      '--bg-overlay': 'rgba(8, 47, 73, 0.5)',
      '--text-primary': '#0c4a6e',
      '--text-secondary': '#0e7490',
      '--text-tertiary': '#06b6d4',
      '--text-inverse': '#ffffff',
      '--border-light': '#7dd3fc',
      '--border-medium': '#38bdf8',
      '--border-dark': '#0284c7',
      '--accent-primary': '#0891b2',
      '--accent-hover': '#0e7490',
      '--accent-light': '#06b6d4',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#0ea5e9',
      '--shadow-sm': '0 1px 2px 0 rgba(8, 145, 178, 0.1)',
      '--shadow-md': '0 4px 6px -1px rgba(8, 145, 178, 0.15)',
      '--shadow-lg': '0 10px 15px -3px rgba(8, 145, 178, 0.2)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'sunset-glow',
    name: 'Sunset Glow',
    description: 'Warm orange-red theme',
    cssVars: {
      '--bg-primary': '#fff7ed',
      '--bg-secondary': '#ffedd5',
      '--bg-tertiary': '#fed7aa',
      '--bg-elevated': '#ffffff',
      '--bg-overlay': 'rgba(124, 45, 18, 0.5)',
      '--text-primary': '#7c2d12',
      '--text-secondary': '#9a3412',
      '--text-tertiary': '#c2410c',
      '--text-inverse': '#ffffff',
      '--border-light': '#fca5a5',
      '--border-medium': '#fb923c',
      '--border-dark': '#ea580c',
      '--accent-primary': '#ea580c',
      '--accent-hover': '#c2410c',
      '--accent-light': '#f97316',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#dc2626',
      '--status-info': '#3b82f6',
      '--shadow-sm': '0 1px 2px 0 rgba(234, 88, 12, 0.1)',
      '--shadow-md': '0 4px 6px -1px rgba(234, 88, 12, 0.15)',
      '--shadow-lg': '0 10px 15px -3px rgba(234, 88, 12, 0.2)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'forest-green',
    name: 'Forest Green',
    description: 'Natural green theme',
    cssVars: {
      '--bg-primary': '#f0fdf4',
      '--bg-secondary': '#dcfce7',
      '--bg-tertiary': '#bbf7d0',
      '--bg-elevated': '#ffffff',
      '--bg-overlay': 'rgba(20, 83, 45, 0.5)',
      '--text-primary': '#14532d',
      '--text-secondary': '#166534',
      '--text-tertiary': '#15803d',
      '--text-inverse': '#ffffff',
      '--border-light': '#86efac',
      '--border-medium': '#4ade80',
      '--border-dark': '#22c55e',
      '--accent-primary': '#16a34a',
      '--accent-hover': '#15803d',
      '--accent-light': '#22c55e',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#3b82f6',
      '--shadow-sm': '0 1px 2px 0 rgba(22, 163, 74, 0.1)',
      '--shadow-md': '0 4px 6px -1px rgba(22, 163, 74, 0.15)',
      '--shadow-lg': '0 10px 15px -3px rgba(22, 163, 74, 0.2)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'royal-purple',
    name: 'Royal Purple',
    description: 'Elegant purple theme',
    cssVars: {
      '--bg-primary': '#faf5ff',
      '--bg-secondary': '#f3e8ff',
      '--bg-tertiary': '#e9d5ff',
      '--bg-elevated': '#ffffff',
      '--bg-overlay': 'rgba(88, 28, 135, 0.5)',
      '--text-primary': '#581c87',
      '--text-secondary': '#6b21a8',
      '--text-tertiary': '#7e22ce',
      '--text-inverse': '#ffffff',
      '--border-light': '#d8b4fe',
      '--border-medium': '#c084fc',
      '--border-dark': '#a855f7',
      '--accent-primary': '#9333ea',
      '--accent-hover': '#7e22ce',
      '--accent-light': '#a855f7',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#8b5cf6',
      '--shadow-sm': '0 1px 2px 0 rgba(147, 51, 234, 0.1)',
      '--shadow-md': '0 4px 6px -1px rgba(147, 51, 234, 0.15)',
      '--shadow-lg': '0 10px 15px -3px rgba(147, 51, 234, 0.2)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'midnight-blue',
    name: 'Midnight Blue',
    description: 'Deep blue night theme',
    cssVars: {
      '--bg-primary': '#0a0e27',
      '--bg-secondary': '#1a1f3a',
      '--bg-tertiary': '#2d3450',
      '--bg-elevated': '#1a1f3a',
      '--bg-overlay': 'rgba(0, 0, 0, 0.8)',
      '--text-primary': '#e0e7ff',
      '--text-secondary': '#c7d2fe',
      '--text-tertiary': '#a5b4fc',
      '--text-inverse': '#0a0e27',
      '--border-light': '#3730a3',
      '--border-medium': '#4f46e5',
      '--border-dark': '#6366f1',
      '--accent-primary': '#6366f1',
      '--accent-hover': '#818cf8',
      '--accent-light': '#a5b4fc',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#60a5fa',
      '--shadow-sm': '0 1px 2px 0 rgba(99, 102, 241, 0.2)',
      '--shadow-md': '0 4px 6px -1px rgba(99, 102, 241, 0.3)',
      '--shadow-lg': '0 10px 15px -3px rgba(99, 102, 241, 0.4)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  },
  {
    id: 'cherry-blossom',
    name: 'Cherry Blossom',
    description: 'Soft pink theme',
    cssVars: {
      '--bg-primary': '#fdf2f8',
      '--bg-secondary': '#fce7f3',
      '--bg-tertiary': '#fbcfe8',
      '--bg-elevated': '#ffffff',
      '--bg-overlay': 'rgba(131, 24, 67, 0.5)',
      '--text-primary': '#831843',
      '--text-secondary': '#9f1239',
      '--text-tertiary': '#be123c',
      '--text-inverse': '#ffffff',
      '--border-light': '#f9a8d4',
      '--border-medium': '#f472b6',
      '--border-dark': '#ec4899',
      '--accent-primary': '#db2777',
      '--accent-hover': '#be123c',
      '--accent-light': '#ec4899',
      '--status-success': '#10b981',
      '--status-warning': '#f59e0b',
      '--status-error': '#ef4444',
      '--status-info': '#ec4899',
      '--shadow-sm': '0 1px 2px 0 rgba(219, 39, 119, 0.1)',
      '--shadow-md': '0 4px 6px -1px rgba(219, 39, 119, 0.15)',
      '--shadow-lg': '0 10px 15px -3px rgba(219, 39, 119, 0.2)',
      '--radius-sm': '4px',
      '--radius-md': '8px',
      '--radius-lg': '12px'
    }
  }
]

export const getThemeById = (id: string): Theme | undefined => {
  return themes.find(theme => theme.id === id)
}

export const applyTheme = (theme: Theme): void => {
  const root = document.documentElement
  Object.entries(theme.cssVars).forEach(([key, value]) => {
    root.style.setProperty(key, value)
  })
}

export const saveThemePreference = (themeId: string): void => {
  localStorage.setItem('app-theme', themeId)
}

export const getThemePreference = (): string => {
  return localStorage.getItem('app-theme') || 'slate-mono'
}