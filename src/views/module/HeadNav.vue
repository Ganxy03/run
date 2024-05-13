<!-- <template>
    <div class="head-nav">
        <button @click="toggleDark">切换模式
        is dark: {{ isDark }}
        </button>
    </div>
</template>
<script setup>
// import { useDark, useToggle} from '@vueuse/core'
// const isDark = useDark()
// const toggleDark = useToggle(isDark)
import { useDark, useToggle } from '@vueuse/core'

// 不传递任何参数，让 useDark 自动检测用户的系统偏好
const isDark = useDark()
const toggleDark = useToggle(isDark)

// 为了确保初始状态为 false（明亮模式），我们可以在 mounted 钩子中进行一次切换
import { onMounted } from 'vue'
onMounted(() => {
  toggleDark()
})
</script>
<style>
.head-nav {
    height: 60px;
    /* background-color: blueviolet; */
    /* background: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet); */
    /* background: linear-gradient(to right, rgb(58, 141, 219), rgb(211, 172, 211)); */
    background: linear-gradient(to right, rgba(58, 141, 219, 0.6), rgba(211, 172, 211, 0.6));
}
</style> -->
<template>
    <div class="head-nav">
        <!-- <button @click="toggleDarkMode">切换模式
        is dark: {{ isDark }}
        </button> -->
        <div class="clickMode">
          <TopBtn />
        </div>
    </div>
</template>

<script>
import TopBtn from './TopLable.vue'
import { useDark, useToggle } from '@vueuse/core'

export default {
  components: {
    TopBtn
  },
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

<style>
.head-nav {
  height: 60px;
  background: linear-gradient(to right, rgba(58, 141, 219, 0.6), rgba(211, 172, 211, 0.6));
}
.head-nav .clickMode {
  display: flex;
  justify-content: flex-end;
  margin-right: 12px;
}

</style>
