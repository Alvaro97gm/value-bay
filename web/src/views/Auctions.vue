<template>
  <div id="container">
    <div class="section">
      <h2 class="green">Subastas activas</h2>
      <div class="auctions">
        <div class="auction is-active"
          v-for="auction in activeAuctions"
          :key="auction.auctionId">
          <p><span class="auction-title">{{auction.name}}</span></p>
          <p class="bold"><span class="green">{{auction.lotIds.length}}</span> Articulos</p> 
        </div>
      </div>
    </div>
    <hr>
    <div class="section">
      <h2 class="green">Próximas subastas</h2>
      <div class="auctions">
        <div class="auction is-next"
          v-for="auction in nextAuctions"
          :key="auction.auctionId">
          <p><span class="auction-title">{{auction.name}}</span></p>
          <p class="bold"><span class="green">{{auction.lotIds.length}}</span> Articulos</p>
        </div>
      </div>
    </div>
    <hr>
    <div class="section">
      <h2 class="green">Subastas cerradas</h2>
      <div class="auctions">
        <div class="auction is-closed"
          v-for="auction in closedAuctions"
          :key="auction.auctionId">
          <p><span class="auction-title">{{auction.name}}</span></p>
          <p class="bold"><span class="green">{{auction.lotIds.length}}</span> Articulos</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import configAlert from '../util/configAlert';

export default {
  name: "Auctions",
  data: function(){
    return {
      activeAuctions: [],
      nextAuctions: [],
      closedAuctions: []
    }
  },
  methods: {
    getActiveAuctions: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Auction.getActive,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        currentContext.$data.activeAuctions = res.data
      })
      .catch((err) => {
        console.error(err)
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },
    getNextAuctions: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Auction.getNext,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        currentContext.$data.nextAuctions = res.data
      })
      .catch(() => {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },
    getClosedAuctions: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Auction.getClosed,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        currentContext.$data.closedAuctions = res.data
      })
      .catch(() => {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    } 
  },
  beforeMount: function(){
    this.getActiveAuctions()
    this.getNextAuctions()
    this.getClosedAuctions()
  }
}
</script>
<style scoped>
#container {
  padding: 1.5em 2em 0 2em;
  width: 100%;
  height: 80vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.section {
  text-align: center;
}
.auctions {
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
}
.auction {
  padding: .75em;
  width: 20em;
  height: 6em;
  border-radius: 15px;
  box-shadow: 0px 0px 20px -5px rgba(0,0,0,.5);
  margin: 1em;
  transition: .25s;
}

.auction.is-active {
  background-color: rgba(211, 250, 218, 0.5);
}

.auction.is-next {
  background-color: rgba(241, 240, 179, .4);
}

.auction.is-closed {
  background-color: rgba(0,0,0,.25);
}

.auction:hover{
  transform: scale(1.1);
  cursor: pointer;
}
.auction-title {
  font-family: 'Oswald', sans-serif;
  font-size: 1.2em;
}
.bold {
  font-weight: bold;
}
.green {
  color:rgb(122, 179, 132);
}
h2 {
  font-family: 'Oswald', sans-serif;
}
hr {
  width: 90%;
}
</style>