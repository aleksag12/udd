import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Pretraga from "../components/Pretraga.vue";
import Statistika from "../components/Statistika.vue";
import Prijava from "../components/Prijava.vue";
import Homepage from "../components/Homepage.vue";
//import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Homepage",
    component: Homepage
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/pretraga",
    name: "Pretraga",
    component: Pretraga
  },
  {
    path: "/statistika",
    name: "Statistika",
    component: Statistika
  },
  {
    path: "/prijava",
    name: "Prijava",
    component: Prijava
  },
];

const router = new VueRouter({
  mode: 'history',
  routes
});

// router.beforeEach((to, from, next) => {
//   if (to.path === '/') next({ name: 'Login' })
//   if ((to.name !== 'Login') && !store.getters.authenticated) next({ name: 'Login' })
//   else next()
//   if ((to.name === 'Login') && store.getters.authenticated) next({ name: from.name })
//   else next()
// })

export default router;
