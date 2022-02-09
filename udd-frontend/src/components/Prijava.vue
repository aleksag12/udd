<template>
  <div class="courses-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md5>
        <v-card class="elevation-12 mt-12" style="padding: 20px">
          <v-card-text>
            <h1 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              Aplikacija za posao
            </h1>
            <v-form class="mt-4" v-model="isValid">
              <v-text-field
                color="green"
                prepend-icon="person"
                name="ime"
                label="Ime"
                type="text"
                v-model="application.firstName"
                :rules="[(v) => !!v || 'Ime je obavezno']"
                required
              ></v-text-field>
              <v-text-field
                color="green"
                prepend-icon="person"
                name="prezime"
                label="Prezime"
                type="text"
                v-model="application.lastName"
                :rules="[(v) => !!v || 'Prezime je obavezno']"
                required
              ></v-text-field>
              <v-text-field
                color="green"
                prepend-icon="email"
                name="email"
                label="Email"
                type="text"
                v-model="application.email"
                :rules="[(v) => !!v || 'Email je obavezan']"
                required
              ></v-text-field>
              <v-text-field
                color="green"
                prepend-icon="location_city"
                name="city"
                label="Grad"
                type="text"
                v-model="application.city"
                :rules="[(v) => !!v || 'Grad je obavezan']"
                required
              ></v-text-field>
              <v-text-field
                color="green"
                prepend-icon="flag"
                name="country"
                label="Drzava"
                type="text"
                v-model="application.country"
                :rules="[(v) => !!v || 'Drzava je obavezna']"
                required
              ></v-text-field>
              <v-select
                color="green"
                prepend-icon="mdi-school"
                :items="degrees"
                v-model="application.education"
                label="Stepen obrazovanja"
                item-text="name"
                item-value="value"
                :rules="[(v) => !!v || 'Stepen obrazovanja je obavezan']"
                required
                single-line
              ></v-select>
              <v-file-input color="green" required show-size counter chips accept=".pdf" label="Dodaj CV" ref="myfile" v-model="files" :rules="[(v) => !!v || 'CV je obavezan']"></v-file-input>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn
              color="green"
              class="my-2"
              style="width: 95%; color: white"
              :disabled="!isValid"
              v-on:click="apliciraj"
              >Apliciraj</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
    <v-snackbar v-model="wrong" top color="red darken-3">
      {{this.message}}
    </v-snackbar>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";
import { mapState } from "vuex";

export default {
  name: "Prijava",
  components: {
    Navbar,
  },
  data: () => ({
    application: {
      firstName: '',
      lastName: '',
      email: '',
      education: '',
      city: '',
      country: '',
      cv: ''
    },
    isValid: true,
    message: '',
    wrong: '',
    files: null,
    degrees: [
      {name: "Osnovno obrazovanje", value: "1"},
      {name: "Srednje obrazovanje", value: "2"},
      {name: "Osnovne akademske studije", value: "3"},
      {name: "Master akademske studije", value: "4"},
      {name: "Doktorske studije", value: "5"},
    ],
  }),
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {
    apliciraj: function () {
      let formData = new FormData();

      formData.append("firstName", this.application.firstName);
      formData.append("lastName", this.application.lastName);
      formData.append("email", this.application.email);
      formData.append("education", this.application.education);
      formData.append("city", this.application.city);
      formData.append("country", this.application.country);
      formData.append("cv", this.files, this.files.name);

      console.log(this.files);
    },
    getPosition() {
      return new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resp => {
          resolve({ lng: resp.coords.longitude, lat: resp.coords.latitude });
        },
          err => {
            reject(err);
          });
      });
    },
  },
  created() {
    this.$store.dispatch("getCurrentUser");
    if (navigator.geolocation) {
      this.getPosition().then(pos => {
        console.log(`Positon: ${pos.lng} ${pos.lat}`);
      });
    } else {
      console.log("Geolocation is not supported by this browser.");
    }
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