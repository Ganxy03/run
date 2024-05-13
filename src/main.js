import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
// import * as Icons from '@element-plus/icons'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

const app = createApp(App)

// for (const [key, component] of Object.entries(Icons)) {
//     app.component(key, component)
// }


app.use(store).use(router).use(ElementPlus, { size: 'small', zIndex: 3000 }).mount('#app')

