import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import { useAuthStore } from '../stores/auth.store';

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
    path: '/forget-password',
    name: 'Forget Password',
    component: () => import('../views/auth/ForgetPasswordView.vue')
  },
  {
    path: '/privacy-policy',
    name: 'Signup',
    component: () => import('../views/auth/PrivacyPolicyView.vue')
  },
  {
    path: '/terms-of-service',
    name: 'Terms of Service',
    component: () => import('../views/auth/TermsOfServiceView.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/dashboard/DashboardView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/profile/ProfileView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/error/NotFound.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, _, next) => {
  const authStore = useAuthStore()
  const isAuthenticated = authStore.isAuthenticated

  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
    return
  }

  if (to.meta.requiresGuest && isAuthenticated) {
    next('/dashboard')
    return
  }

  next()
})

export { router }
