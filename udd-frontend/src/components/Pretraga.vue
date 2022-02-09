<template>
  <div class="courses-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md11>
        <v-card
          class="elevation-12"
          style="padding: 20px; margin-top: 40px; margin-bottom: 40px"
        >
          <template>
            <v-card>
              <v-tabs v-model="tab" background-color="green darken-2" centered dark>
                <v-tabs-slider></v-tabs-slider>
                <v-tab href="#tab-1">
                  Pretraga kao Google
                </v-tab>
                <v-tab href="#tab-2">
                  Napredna pretraga
                </v-tab>
                <v-tab href="#tab-3">
                  Pretraga po geolokaciji
                </v-tab>
              </v-tabs>

              <v-tabs-items v-model="tab">
                <v-tab-item value="tab-1">
                  <GooglePretraga></GooglePretraga>
                </v-tab-item>
                <v-tab-item value="tab-2">
                  <NaprednaPretraga></NaprednaPretraga>
                </v-tab-item>
                <v-tab-item value="tab-3">
                  <GeolokacijaPretraga></GeolokacijaPretraga>
                </v-tab-item>
              </v-tabs-items>

            </v-card>
          </template>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";
import GooglePretraga from "./GooglePretraga.vue";
import NaprednaPretraga from "./NaprednaPretraga.vue";
import GeolokacijaPretraga from "./GeolokacijaPretraga.vue";
import { mapState } from "vuex";

export default {
  name: "Pretraga",
  components: {
    Navbar, GooglePretraga, NaprednaPretraga, GeolokacijaPretraga
  },
  data: () => ({
    tab: null,
  }),
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {
    
  },
  created() {
    this.$store.dispatch("getCurrentUser");
  },
};
</script>

<style scoped>
.courses-container {
  background-image: url(../assets/6.png);
  background-repeat: repeat-y;
  height: 100%;
}
</style>