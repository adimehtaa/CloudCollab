<template>
  <div class="min-h-screen flex justify-center" 
       style="background-color: var(--bg-secondary);">
    <div class="w-full max-w-[1200px] min-h-screen mx-4" 
         style="background-color: var(--bg-elevated); 
                box-shadow: var(--shadow-sm);">
      <slot />
    </div>
  </div>
</template>

<script lang="ts" setup>
</script>