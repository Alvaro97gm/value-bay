<template>
  <div id="home">
    <Navbar/>
    <Admin v-if="views.admin"/>
    <Lots v-if="views.lots"/>
    <div id="home" v-else>
      ASDF
    </div>
  </div>    
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import Navbar from '../components/Navbar';
import Admin from './Admin';
import Lots from './Lots';
import EventBus from '../util/eventBus';

export default {
  name: "Home",
  components: {
    Navbar,
    Admin,
    Lots
  },
  data: function(){
    return {
      views: {
        admin: false,
        lots: false
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
      this.$data.views.lots = false;
      this.$data.views.admin = true;
    },
    renderLotsView: function(){
      this.$data.views.lots = true
      this.$data.views.admin = false
    },
    renderHomeView: function(){
      this.$data.views.admin = false;
      this.$data.views.lots = false;
    }
  },
  beforeMount: function(){
    this.$data.userData = JSON.parse(localStorage.getItem('userData'))
    
  },
  mounted: function(){

    var currentContext = this
    EventBus.$on('SHOW_ADMIN', () => {currentContext.renderAdminView()})
    EventBus.$on('SHOW_LOTS', () => {currentContext.renderLotsView()})
    EventBus.$on('SHOW_HOME', () => {currentContext.renderHomeView()})
  }
  
}
</script>
<style scoped>
#home {
  display: flex;
  flex-direction: column;
}
</style>