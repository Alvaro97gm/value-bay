<template>
  <div id="home">
    <Navbar/>
    <Admin v-if="views.admin"/>
    <div id="sandbox" v-else>
      <b-button-group class="buttons">
        <b-button class="button" variant="outline-success" @click="getAllCoins()">Descargar coins</b-button>
        <b-button class="button" variant="outline-success" @click="getAllBills()">Descargar bills</b-button>
        <b-button class="button" variant="outline-success" @click="clearData()">Limpiar</b-button>
      </b-button-group>
      <div id="monedas" v-if="coinLots != null">      
        <TempMiniProduct
          v-for="item in coinLots" :key="item.itemId"
          :itemId="item.itemId"
          itemType="Moneda"
          :itemValue="item.itemValue"
          :itemDate="item.emissionYear"
          :itemConservationStatus="item.conservationStatus"
          :itemConservationStatusDetailed="item.conservationStatusDetailed"
          :itemPrice="item.price"
          />
      </div>
      <div id="billetes" v-if="billLots != null">      
        <TempMiniProduct
          v-for="item in billLots" :key="item.itemId"
          :itemId="item.itemId"
          itemType="Billete"
          :itemValue="item.itemValue"
          :itemDate="item.emissionYear"
          :itemConservationStatus="item.conservationStatus"
          :itemConservationStatusDetailed="item.conservationStatusDetailed"
          :itemPrice="item.price"
          />
      </div>      
    </div>
    
  </div>    
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import Navbar from '../components/Navbar';
import Admin from './Admin';
import TempMiniProduct from '../components/TempMiniProduct';
import EventBus from '../util/eventBus';

export default {
  name: "Home",
  components: {
    Navbar,
    Admin,
    TempMiniProduct
  },
  data: function(){
    return {
      views: {
        admin: false
      },
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
    },

    renderAdminView: function(){
      this.$data.views.admin = true;
    },
    hideAdminView: function(){
      this.$data.views.admin = false;
    }
  },
  beforeMount: function(){
    this.$data.userData = JSON.parse(localStorage.getItem('userData'))
    
  },
  mounted: function(){

    var currentContext = this
    EventBus.$on('SHOW_ADMIN', () => {currentContext.renderAdminView()})
    EventBus.$on('SHOW_HOME', () => {currentContext.hideAdminView()})
  }
  
}
</script>
<style scoped>
#home {
  display: flex;
  flex-direction: column;
}
/* TODO: Eliminar */
#sandbox {
  display: flex;
  flex-direction: column;
}

#monedas {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
#billetes {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.buttons {
  align-self: center;
  margin: 2em;
  width: 50%;
}

.button{
  margin: .5em;
  border-radius: 10px;
}
</style>