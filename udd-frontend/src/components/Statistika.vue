<template>
  <div class="courses-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md11>
        <v-card
          class="elevation-12"
          style="padding: 20px; margin-top: 40px; margin-bottom: 40px"
        >
          
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
  name: "Statistika",
  components: {
    Navbar,
  },
  data: () => ({

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