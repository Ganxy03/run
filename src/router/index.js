import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/manager',
    name: 'manager',
    component: () => import('../views/ManagerView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
