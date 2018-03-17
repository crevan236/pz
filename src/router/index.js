import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import Info from '@/components/Info'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/info',
      name: 'info',
      component: Info
    }
  ]
})
