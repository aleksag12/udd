<template>
  <div>
    <div style="height: 20px"></div>
    <v-row v-for="param in paramList" :item="param" :key="param.paramName">
      <v-col cols="12" sm="3">
        <v-select
          v-if="paramList.indexOf(param) == 0"
          style="visibility: hidden;"
          color="green"
          :items="tipovi"
          v-model="param.type"
          item-text="prikaz"
          item-value="tip"
          class="ml-3 mb-n5"
          outlined
          dense
        ></v-select>
        <v-select
          v-else
          color="green"
          :items="tipovi"
          v-model="param.type"
          item-text="prikaz"
          item-value="tip"
          class="ml-3 mb-n5"
          outlined
          dense
        ></v-select>
      </v-col>
      <v-col cols="12" sm="3">
        <v-select
          color="green"
          :items="polja"
          v-model="param.name"
          @change="promenaPolja(paramList.indexOf(param), $event)"
          item-text="prikaz"
          item-value="tip"
          class="ml-3 mb-n5"
          outlined
          dense
        ></v-select>
      </v-col>
      <v-col cols="12" sm="3" v-if="param.name != 'education'">
        <v-text-field
          color="green"
          v-model="param.value"
          :label="param.preview"
          clearable
          class="ml-3 mb-n5"
          full-width
          dense
          outlined
        ></v-text-field>
      </v-col>
      <v-col cols="12" sm="3" v-else>
        <v-select
          color="green"
          :items="degrees"
          label="Od"
          v-model="param.value"
          item-text="name"
          item-value="value"
          class="ml-3 mb-n5"
          outlined
          dense
        ></v-select>
        <v-select
          color="green"
          :items="degrees"
          label="Do"
          v-model="param.value2"
          item-text="name"
          item-value="value"
          class="ml-3 mb-n5"
          outlined
          dense
        ></v-select>
      </v-col>
      <v-col cols="12" sm="3">
        <v-btn
          color="red darken-3 mb-n5"
          style="width: 85%; color: white"
          v-on:click="ukloni(paramList.indexOf(param))"
          >Ukloni polje</v-btn
        >
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" sm="3">
      </v-col>
      <v-col cols="12" sm="3">
      </v-col>
      <v-col cols="12" sm="3">
      </v-col>
      <v-col cols="12" sm="3">
        <v-btn
          color="green mb-2"
          style="width: 85%; color: white"
          v-on:click="dodajPolje()"
          >Dodaj polje</v-btn
        >
      </v-col>
    </v-row>
    <v-btn
      color="green mx-4 my-3"
      style="width: 97%; color: white"
      v-on:click="search"
      >Pretraži</v-btn
    >
    <v-divider></v-divider>
  </div>
</template>

<script>
/* eslint-disable */
import Navbar from "./Navbar.vue";

export default {
  name: "NaprednaPretraga",
  components: {
    Navbar,
  },
  data: () => ({
    searchValue: '',
    paramList: [
      {
        preview: 'Ime',
        name: 'firstName',
        value: '',
        value2: '',
        type: 'AND',
      },
    ],
    polja: [
      {prikaz: "Ime", tip: "firstName"},
      {prikaz: "Prezime", tip: "lastName"},
      {prikaz: "Stepen obrazovanja", tip: "education"},
      {prikaz: "CV", tip: "cv"},
    ],
    tipovi: [
      {prikaz: "I", tip: "AND"},
      {prikaz: "ILI", tip: "OR"},
    ],
    degrees: [
      {name: "Osnovno obrazovanje", value: "1"},
      {name: "Srednje obrazovanje", value: "2"},
      {name: "Osnovne akademske studije", value: "3"},
      {name: "Master akademske studije", value: "4"},
      {name: "Doktorske studije", value: "5"},
    ],
    searchParams: [],
  }),
  methods: {
    search: function () {
      this.pretrazeno = true;

      this.searchParams = [];
      this.page = 1;

      if (this.paramList.length > 1) {
        this.paramList[0].type = this.paramList[1].type;
      }

      for (let index = 0; index < this.paramList.length; index++) {
        let name = this.paramList[index].name;
        let value = this.paramList[index].value;
        let value2 = this.paramList[index].value2;
        let type = this.paramList[index].type;
        let phraseQuery = this.paramList[index].value[0] == "\"" && this.paramList[index].value[this.paramList[index].value.length - 1] == "\"";
        if (value == '') {
          return;
        }
        if (name != "education") {
          this.searchParams.push({
            "name": name,
            "value": value,
            "phrase": phraseQuery,
            "operator": type
          });
        } else {
          if (!value || !value2) {
            console.log(value);
            console.log(value2);
            return;
          }
          this.searchParams.push({
            "name": name,
            "value": value,
            "value2": value2,
            "phrase": phraseQuery,
            "operator": type
          });
        }
      }

      if (this.searchParams.length == 0) {
        return;
      }

      console.log(this.searchParams);

    },
    dodajPolje: function () {
      this.paramList.push({
        preview: 'Ime',
        name: 'firstName',
        value: '',
        value2: '',
        type: 'AND'
      });
    },
    ukloni: function (indeks) {
      this.paramList.splice(indeks, 1);
    },
    promenaPolja: function (index, event) {
      if (event == 'firstName') {
        this.paramList[index].preview = 'Ime'
        this.paramList[index].value = ''
      } else if (event == 'lastName') {
        this.paramList[index].preview = 'Prezime'
        this.paramList[index].value = ''
      } else if (event == 'cv') {
        this.paramList[index].preview = 'CV'
        this.paramList[index].value = ''
      } else if (event == 'education') {
        this.paramList[index].value = '1';
        this.paramList[index].value2 = '1';
      }
    },
  },
  created() {
    
  },
};
</script>