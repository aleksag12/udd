<template>
  <div>
    <v-row>
      <v-col cols="12" sm="8">
        <v-text-field
          color="green"
          v-model="searchValue"
          label="Pretraga..."
          clearable
          prepend-icon="search"
          class="ml-3 mt-8"
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
    <p
      v-if="results.totalElements == 0 && pretrazeno"
      class="my-4"
      style="display: flex; justify-content: center"
    >
      Nema rezultata pretrage.
    </p>
    <div v-else class="px-10 py-3">
      <p style="font-size: 12px; color: grey" class="ml-4">
        Око {{ this.results.totalElements }} резултата ({{
          this.brojSekundi.toFixed(2)
        }}
        секунде/и)
      </p>
      <div
        v-for="res in results.content"
        :item="res"
        :key="res.id"
        class="px-4"
      >
        <a style="font-size: 18px; color: blue" @click="dobaviCV(res.cv.url)">{{
          res.informations.split(", ")[0]
        }}</a>
        <p v-html="res.cv.content" style="font-size: 13px; max-width: 75ch"></p>
        <p style="font-size: 12px" class="mt-n4">
          <b style="color: black">Email: </b
          >{{ res.informations.split(", ")[1] }},
          <b style="color: black">Stepen obrazovanja: </b
          >{{ res.informations.split(", ")[2] }},
          <b style="color: black">Mesto: </b
          >{{ res.informations.split(", ")[3] }},
          {{ res.informations.split(", ")[4] }}
        </p>
      </div>
      <div class="text-center">
        <v-pagination
          @input="next"
          color="green"
          v-model="page"
          :length="
            this.results.totalElements == this.size
              ? Math.floor(this.results.totalElements / this.size)
              : Math.floor(this.results.totalElements / this.size) + 1
          "
        ></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";

export default {
  name: "GooglePretraga",
  components: {
    Navbar,
  },
  data: () => ({
    page: 1,
    size: 3,
    brojSekundi: 0,
    searchValue: "",
    results: {
      content: [],
      totalElements: 0,
    },
    pretrazeno: false,
  }),
  methods: {
    dobaviCV: function (url) {
      this.$store
        .dispatch("dobaviCV", url)
        .then((resp) => {
          const blob = new Blob([resp.data], { type: "application/pdf" });
          const url = window.URL.createObjectURL(blob);
          window.open(url, "_blank");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    next: function (page) {
      this.page = page;
      this.$store
        .dispatch("googlePretraga", {
          criterion: this.searchValue,
          page: this.page - 1,
          size: this.size,
        })
        .then((resp) => {
          this.results = resp.data;
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    search: function () {
      var startTime = performance.now();

      if (this.searchValue === "") {
        return;
      }

      this.pretrazeno = true;
      this.page = 1;

      this.$store
        .dispatch("googlePretraga", {
          criterion: this.searchValue,
          page: this.page - 1,
          size: this.size,
        })
        .then((resp) => {
          console.log(resp.data);
          this.results = resp.data;
        })
        .catch((err) => {
          console.log(err.response);
        });

      var endTime = performance.now();
      var time = endTime - startTime;
      this.brojSekundi = time;
    },
  },
  created() {},
};
</script>
<style scoped>
a:hover {
  text-decoration: underline;
}
</style>