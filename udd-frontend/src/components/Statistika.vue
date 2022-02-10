<template>
  <div class="courses-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md11>
        <v-card
          class="elevation-12"
          style="padding: 20px; margin-top: 40px; margin-bottom: 40px"
        >
          <iframe
            src="http://localhost:5601/app/kibana#/visualize/edit/c2dbb150-8a05-11ec-ba69-938ec06fc507?embed=true&_g=(refreshInterval%3A(display%3AOff%2Cpause%3A!f%2Cvalue%3A0)%2Ctime%3A(from%3Anow%2Fw%2Cmode%3Aquick%2Cto%3Anow%2Fw))"
            height="600"
            width="50%"
          ></iframe>
          <iframe
            src="http://localhost:5601/app/kibana#/visualize/edit/23416d50-8a06-11ec-ba69-938ec06fc507?embed=true&_g=(refreshInterval%3A(display%3AOff%2Cpause%3A!f%2Cvalue%3A0)%2Ctime%3A(from%3Anow%2Fw%2Cmode%3Aquick%2Cto%3Anow%2Fw))"
            height="600"
            width="50%"
          ></iframe>
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
  data: () => ({}),
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {},
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