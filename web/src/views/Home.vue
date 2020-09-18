<template>
  <div id="home">
    <Navbar/>
    <Admin v-if="views.admin"/>    
    <Lots v-else-if="views.lots"/>
    <Profile v-else-if="views.profile"/>
    <Auctions v-else-if="views.auctions"/>
    
    <div id="homePage" v-else>
      <div id="auctions" @click="renderAuctionsView()">
        <h2 id="auction-title">Subastas</h2>
      </div>
      <div id="right">
        <div id="lots" @click="renderLotsView()">
          <h2 id="lots-title">Artículos</h2>
        </div>
        <div id="text">
          <h2>¿Qué es <span class="green">Value-bay</span>?</h2>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi voluptatibus eius adipisci
          repudiandae, pariatur, nisi ducimus perferendis quis nulla corrupti earum excepturi, hic
          necessitatibus optio porro sunt ipsum. Atque, velit?

          Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi voluptatibus eius adipisci
          repudiandae, pariatur, nisi ducimus perferendis quis nulla corrupti earum excepturi, hic
          necessitatibus optio porro sunt ipsum. Atque, velit?
        </div>
      </div>
    </div>
  </div>    
</template>
<script>
import Navbar from '../components/Navbar';
import Admin from './Admin';
import Lots from './Lots';
import Auctions from './Auctions';
import Profile from './Profile';
import EventBus from '../util/eventBus';

export default {
  name: "Home",
  components: {
    Navbar,
    Admin,
    Lots, 
    Profile,
    Auctions
  },
  data: function(){
    return {
      views: {
        admin: false,
        lots: false,
        profile: false,
        auctions: false
      },
      userData: null,
      coinLots: [],
      billLots: []
    }
  },
  methods: {

    clearData: function(){
      this.$data.coinLots = []
      this.$data.billLots = []
    },

    renderAdminView: function(){
      this.$data.views.admin = true;
      this.$data.views.lots = false;
      this.$data.views.auctions = false;
      this.$data.views.profile = false;
    },
    renderAuctionsView: function(){
      this.$data.views.auctions = true;
      this.$data.views.lots = false;
      this.$data.views.admin = false;
      this.$data.views.profile = false;
    },
    renderLotsView: function(){
      this.$data.views.lots = true;
      this.$data.views.admin = false;
      this.$data.views.auctions = false;
      this.$data.views.profile = false;
    },
    renderProfileView: function(){
      this.$data.views.profile = true;
      this.$data.views.admin = false;
      this.$data.views.auctions = false;
      this.$data.views.lots = false;
    },
    renderHomeView: function(){
      this.$data.views.admin = false;
      this.$data.views.lots = false;
      this.$data.views.auctions = false;
      this.$data.views.profile = false;
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
    EventBus.$on('SHOW_AUCTIONS', () => {currentContext.renderAuctionsView()})
    EventBus.$on('SHOW_PROFILE', () => {currentContext.renderProfileView()})
  }
  
}
</script>
<style scoped>
#home {
  display: flex;
  flex-direction: column;
}
#homePage {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  padding: 4em 0 4em 0;
}
#auctions {
  display: flex;
  justify-content: center;
  padding-top: 2em;
  min-width: 40%;
  height: 75vh;
  background-image: url('../../public/auction.png');
  background-size: cover;
  box-shadow: 0px 0px 30px -2px rgba(0,0,0,.75);
  border-radius: 15px;
  transition: .25s;
}
#auctions:hover {
  cursor: pointer;
  transform: scale(1.05);
}
#auction-title {
  font-size: 6em;
  color: rgba(0,0,0,.7)
}

#right {
  width: 40%;
  padding-bottom: 2em; 
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
#lots {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 35vh;
  background-image: url('../../public/pesetas.jpg');
  background-size: contain;
  box-shadow: 0px 0px 30px -2px rgba(0,0,0,.85);
  border-radius: 15px;
  transition: .25s;
}
#lots:hover {
  cursor: pointer;
  transform: scale(1.1);
}

#lots-title {
  font-size: 4em;
  color: rgba(255,255,255,.8)
}

h2 {
  font-family: 'Oswald', sans-serif;
}
.green {
  color: rgb(122, 179, 132);
}
</style>