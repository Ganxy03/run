<template>
    <div class="env-box" style="height: 100%;">
           <el-switch v-model="isDark" size="large">
               
           </el-switch>
       </div>
</template>

<script>
import { useDark, useToggle } from '@vueuse/core'

export default {
  setup() {
    const isDark = useDark()
    const toggleDark = useToggle(isDark)

    function toggleDarkMode() {
      toggleDark()
    }

    return {
      isDark,
      toggleDarkMode
    }
  }
}
</script>

<style scoped>

</style>
