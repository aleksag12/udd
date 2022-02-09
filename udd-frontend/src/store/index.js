import Vue from 'vue'
import Vuex from "vuex";
import axios from 'axios'
import VueAxios from 'vue-axios'
import jwt_decode from "jwt-decode";

Vue.use(Vuex)
Vue.use(VueAxios, axios)

Vue.prototype.$axios = axios;
const token = localStorage.getItem('token')
if (token) {
    Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
}

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('token') || '',
        user: {},
        allEquipments: [],
        allCourses: [],
        allConferences: [],
    },
    getters: {
        authenticated: state => !!state.token,
        currentUser: state => state.user,
    },
    mutations: {
        setEquipments(state, equipments) {
            state.allEquipments = equipments;
        },

        auth_success_token(state, token) {
            state.token = token;
        },

        setCurrentUser(state, user) {
            state.user = user;
        },

        logout(state) {
            localStorage.removeItem('token');
            state.token = '';
            state.user = {};
        },
    },
    actions: {

        getAccommodations({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:9015/api/accommodations/' + id, method: 'GET' })
                    .then(resp => {
                        commit('setEquipments', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        addEquipmentToCart({ commit }, equipment) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:9015/api/equipment-shopping-carts/add-item', data: equipment, method: 'POST' })
                    .then(resp => {
                        commit('setEquipments', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        login({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:9015/auth/login', data: user, method: 'POST' })
                    .then(resp => {
                        const token = resp.data.accessToken;
                        const user = { 'email': jwt_decode(resp.data.accessToken).sub, 'role': jwt_decode(resp.data.accessToken).role };
                        localStorage.setItem('token', token);
                        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                        commit('auth_success_token', token);
                        commit('setCurrentUser', user);
                        resolve(resp);
                    })
                    .catch(err => {
                        localStorage.removeItem('token');
                        reject(err);
                    });
            });
        },

        logout({ commit }) {
            return new Promise((resolve) => {
                commit('logout');
                localStorage.removeItem('token');
                delete Vue.prototype.$axios.defaults.headers.common['Authorization'];
                resolve();
            });
        },

        getCurrentUser({ commit }) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:9015/auth/current-user', method: 'GET' })
                    .then(resp => {
                        const user = resp.data;
                        commit('setCurrentUser', user);
                        resolve(resp);
                    })
                    .catch(err => {
                        commit('logout');
                        reject(err);
                    });
            });
        },
    },
    modules: {}
});