<template>
  <fix-screen-width>
    <router-view />
  </fix-screen-width>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import FixScreenWidth from './ui/layout/FixScreenWidth.vue'
import { useThemeStore } from './stores/theme.store'

const themeStore = useThemeStore()

onMounted(() => {
  themeStore.initTheme()
})
</script>

<style>
:root {
  transition: background-color 0.3s ease, color 0.3s ease;
}

* {
  transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease;
}
</style>