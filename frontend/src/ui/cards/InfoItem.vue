<template>
  <div>
    <dt class="text-sm font-medium" style="color: var(--text-tertiary);">
      {{ label }}
    </dt>
    <dd class="text-sm mt-1" style="color: var(--text-primary);">
      {{ value || '-' }}
    </dd>
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  label: string
  value?: string
}>()
</script>