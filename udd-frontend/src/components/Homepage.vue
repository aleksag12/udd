<template>
  <div class="courses-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center align-content-center>
      <v-flex xs12 sm8 md11>
        <v-card
          class="elevation-12"
          style="padding: 330px; margin-top: 40px; margin-bottom: 40px"
        >
          <v-card-actions class="justify-center">
            <v-btn
              color="green"
              class="mx-auto"
              style="width: 50%; color: white; padding: 25px"
              v-on:click="apliciraj"
              >Apliciraj za posao
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";
import { mapState } from "vuex";

export default {
  name: "Homepage",
  components: {
    Navbar,
  },
  data: () => ({}),
  methods: {
    apliciraj: function () {
      this.$router.push("/prijava");
    },
  },
  created() {},
};
</script>

<style scoped>
.courses-container {
  background-image: url(../assets/6.png);
  background-repeat: repeat-y;
  height: 100%;
}
</style>