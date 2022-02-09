<template>
  <div>
    <v-row>
      <v-col cols="12" sm="5">
        <v-text-field
          color="green"
          v-model="lokacija"
          label="Lokacija..."
          clearable
          prepend-icon="search"
          class="ml-3 mt-8"
          full-width
          dense
          outlined
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="3">
        <v-text-field
          label="Radius"
          color="green"
          v-model="radius"
          type="number"
          min=0
          clearable
          class="mt-8"
          full-width
          dense
          outlined
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="3">
        <v-btn
          color="green mt-8"
          style="width: 100%; color: white"
          v-on:click="search"
          >Pretraži</v-btn
        >
      </v-col>
    </v-row>
    <v-divider></v-divider>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";

export default {
  name: "GeolokacijaPretraga",
  components: {
    Navbar,
  },
  data: () => ({
    lokacija: '',
    radius: 0,
    pretrazeno: false,
  }),
  methods: {
    search: function () {
      if (this.lokacija === '' || !this.radius || this.radius < 1) {
        return;
      }
      this.pretrazeno = true;
      
      console.log(this.lokacija);
    }
  },
  created() {
    
  },
};
</script>