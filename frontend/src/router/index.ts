import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'; 

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../components/Home.vue'),
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/dashboard/DashboardView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/LoginView.vue')
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('../views/auth/SignupView.vue')
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/error/NotFound.vue'),
  },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})
