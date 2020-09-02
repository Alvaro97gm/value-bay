<template>
  <div>
    <Navbar/>
    <b-button-group>
      <b-button variant="outline-danger" @click="getAllCoins()">Descargar coins</b-button>
      <b-button variant="outline-danger" @click="getAllBills()">Descargar bills</b-button>
      <b-button variant="outline-danger" @click="clearData()">Limpiar</b-button>
    </b-button-group>
    <div v-if="coinLots != null">
      <h2>MONEDAS</h2>
      <h5 v-for="lot in coinLots" :key="lot.lotId">
        ID:{{lot.lotId}} - Moneda de {{lot.itemValue}} pesetas - {{lot.emissionYear}} - {{lot.conservationStatusDetailed}} -> {{lot.price}} €
      </h5>
    </div>
    <div v-if="billLots != null">
      <h2>BILLETES</h2>
      <h5 v-for="lot in billLots" :key="lot.lotId">
        ID:{{lot.lotId}} - Billete de {{lot.itemValue}} pesetas - {{lot.emissionYear}} - {{lot.conservationStatusDetailed}} -> {{lot.price}} €
      </h5>
    </div>
  </div>    
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import Navbar from '../components/Navbar';

export default {
  name: "Home",
  components: {
    Navbar
  },
  data: function(){
    return {
      userData: null,
      coinLots: [],
      billLots: []
    }
  },
  methods: {
    getAllCoins: function(){
      var currentContext = this 
      var ls = localStorage
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.$data.coinLots = res.data 
      })
    },

    getAllBills: function(){
      var currentContext = this 
      var ls = localStorage
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.$data.billLots = res.data 
      })
    }, 

    clearData: function(){
      this.$data.coinLots = []
      this.$data.billLots = []
    }
  },
  beforeMount: function(){
    this.$data.userData = JSON.parse(localStorage.getItem('userData'))
  }
}
</script>
<style scoped>
/* body {
  margin: 0;
  padding: 0;
} */
</style>