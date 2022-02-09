<template>
  <div>
    <v-app-bar color="green darken-2" dark>
      <v-toolbar-title class="mx-4">IT firma</v-toolbar-title>
      <v-btn
        class="mx-4"
        outlined
        @click="goToPretraga()"
        v-if="
          token
        "
      >
        Pretraga
      </v-btn>
      <v-btn
        class="mx-4"
        outlined
        @click="goToStatistika()"
        v-if="
          token
        "
      >
        Statistika
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        class="mx-4"
        outlined
        @click="goToLogin()"
        v-if="
          !token
        "
      >
        Uloguj se
      </v-btn>
      <v-menu
        v-else
        open-on-click
        transition="slide-y-transition"
        bottom
        left
        offset-y
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            style="margin-right: 1px"
            class="account"
            icon
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-account</v-icon>
          </v-btn>
        </template>
        <v-card class="mx-auto" max-width="300" tile>
          <v-list dense>
            <v-subheader class="log-out mr-2" @click="logOut()"
              ><v-icon class="mr-2">mdi-exit-to-app</v-icon
              ><b>Odjavi se</b></v-subheader
            >
          </v-list>
        </v-card>
      </v-menu>
    </v-app-bar>
  </div>
</template>

<script>
/* eslint-disable */
import { mapState } from "vuex";

export default {
  name: "Navbar",
  methods: {
    logOut: function () {
      this.$store.dispatch("logout").then(() => {
        this.$router.push("/login");
      });
    },
    goToPretraga: function () {
      let path = "/pretraga";
      if (this.$route.path !== path) this.$router.push(path);
    },
    goToStatistika: function () {
      let path = "/statistika";
      if (this.$route.path !== path) this.$router.push(path);
    },
    goToLogin: function () {
      let path = "/login";
      if (this.$route.path !== path) this.$router.push(path);
    },
  },
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
      token: (state) => state.token,
    }),
  },
  created: function () {
    this.$store.dispatch("getCurrentUser");
  },
};
</script>

<style scoped>
.log-out {
  cursor: pointer;
}
</style>