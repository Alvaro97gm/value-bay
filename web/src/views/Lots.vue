<template>
  <div>
    <h2>Listado de <span class="green">Artículos</span></h2>
    <hr>
      <div id="details-trigger" v-if="selectedArticle != null">
        <h4>Artículo seleccionado # {{selectedArticleId}}</h4>
        <b-button 
          v-b-tooltip.hover.right title="Mostrar detalles del artículo."
          v-b-toggle.lot-sidebar 
          id="details-button"
          variant="outline-success">
          Detalles
        </b-button>
        <b-sidebar 
          id="lot-sidebar"
          title="Detalles"
          width="40%"
          backdrop
          backdrop-variant="success"
          text-variant="success"
          shadow
          right>
          <div id="sidebar-content">
            <div ref="img" id="img"></div>
            <hr id="divider">
            <h4 id="description-title">
              {{ selectedArticle.type }} de {{ selectedArticle.itemValue }} pesetas de {{ selectedArticle.emissionYear }}          
            </h4>
            <span class="small-text green">ID: {{ selectedArticleId }}</span>
            <b-badge v-if="auctionStatus === 'active'" pill variant="success">En subasta</b-badge>                    
            <b-badge v-else-if="auctionStatus === 'next'" pill variant="warning">Programado para subasta</b-badge>
            <b-badge v-else-if="auctionStatus === 'closed'" pill variant="dark">Adjudicado</b-badge>
            <b-badge v-else pill variant="danger">Próximamente en subasta</b-badge>

            <b-card class="info-card" no-body>
              <b-card-header role="tab">
                <b-button 
                  v-b-toggle.lot-info
                  block 
                  variant="dark"
                  @click="toogleImage(0)">
                  <span class="bold">Descripción</span>
                </b-button>
              </b-card-header>
              <b-collapse id="lot-info" accordion="lotInfo" role="tabpanel">
                <b-list-group flush>
                  <b-list-group-item><code class="green bold">Tipo de lote: </code>{{selectedArticle.type}}</b-list-group-item>
                  <b-list-group-item><code class="green bold">Valor: </code>{{selectedArticle.itemValue}} Pesetas</b-list-group-item>
                  <b-list-group-item>
                    <code class="green bold">Emisión: </code>
                    {{
                      selectedArticle.type === 'Moneda' ? 
                        selectedArticle.emissionYear :
                        selectedArticle.emissionDay + ' de ' 
                          + monthToString(selectedArticle.emissionMonth) + ' de ' 
                          + selectedArticle.emissionYear
                    }} 
                  </b-list-group-item>
                  <b-list-group-item>
                    <code class="green bold">Estado de conservación:</code>
                    {{selectedArticle.conservationStatusDetailed}} <code class="green bold">({{selectedArticle.conservationStatus}})</code>
                  </b-list-group-item>
                  <b-list-group-item><code class="green bold">Precio inicial: </code>{{selectedArticle.price}} €</b-list-group-item>
                </b-list-group>
                
              </b-collapse>
            </b-card>
          </div>
        </b-sidebar>
      </div>      
      <h4 v-else>Selecciona un artículo</h4>
    <hr>    
    <LotsTable id="lots-table"/>    
  </div>    
</template>
<script>
import axios from 'axios'
import LotsTable from '../components/LotsTable'
import EventBus from '../util/eventBus'
import config from '../util/config'
import configAlert from '../util/configAlert'

export default {
  name: "Lots",
  components: {
    LotsTable
  },
  data: function(){
    return {
      selectedArticleId: null,
      selectedArticleType: null,
      selectedArticle: null,
      lastSelectedInfo: null,
      auctionStatus: null,  
    }
  },
  methods: {
    monthToString: function(monthNumber){
      switch(monthNumber){
        case 1:
          return 'Enero'
        case 2:
          return 'Febrero'
        case 3:
          return 'Marzo'
        case 4:
          return 'Abril'
        case 5:
          return 'Mayo'
        case 6:
          return 'Junio'
        case 7:
          return 'Julio'
        case 8:
          return 'Agosto'
        case 9:
          return 'Septiembre'
        case 10:
          return 'Octubre'
        case 11:
          return 'Noviembre'
        case 12:
          return 'Diciembre'
      }
    },
    toogleImage: function(id){

      var imgElem = this.$refs.img

      // Accordeon opens. Image hides
      if(this.lastSelectedInfo === null){
        if(imgElem.classList.contains('slide-reverse-rotate-hor-top'))
          imgElem.classList.remove('slide-reverse-rotate-hor-top')
        
        imgElem.classList.add('slide-rotate-hor-top')
        this.lastSelectedInfo = id
        return
      }
      // Accordeon closes. Image shows
      if(this.lastSelectedInfo == id){
        if(imgElem.classList.contains('slide-rotate-hor-top'))
          imgElem.classList.remove('slide-rotate-hor-top')
        
        imgElem.classList.add('slide-reverse-rotate-hor-top')
        this.lastSelectedInfo = null        
      }else{
        this.lastSelectedInfo = id;
      }
      
      
    } ,
    getArticle: function(lotId){

      var routedPath, isBill
      var currentContext = this
      var localData = this.$data
      var ls = localStorage
      

      if(localData.selectedArticleType === 'Billete'){
        routedPath = config.APIEndpoints.Lot.Bill.get + lotId
        isBill = true
      } else {
        routedPath = config.APIEndpoints.Lot.Coin.get + lotId
        isBill = false
      }
      
      axios({
        method: 'get',
        url: config.serverURL + routedPath,
        headers: {
          "Authorization": ls.getItem('jwt'),
          'Content-Type': "application/json"
        }
      })
      .then(res => {
        if(res.status === 200){
          localData.selectedArticle = res.data
          if(isBill)
            localData.selectedArticle.type = "Billete"
          else
            localData.selectedArticle.type = "Moneda"
          currentContext.getAuctionStatus();
        }else{
          this.$root.customAlert(configAlert.NOT_FOUND) 
        }
      })
    },
    getAuctionStatus: function(){
      if(this.$data.selectedArticle === null || this.$data.selectedArticle.fkAuction === null){
        this.$data.auctionStatus = null
        return
      }

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Auction.getStatus + currentContext.$data.selectedArticle.fkAuction,
        headers: {
          "Authorization": ls.getItem('jwt'),
          'Content-Type': "application/json"
        }
      })
      .then(res => {
        if(res.status === 200){
          currentContext.$data.auctionStatus = res.data          
        }else{
          this.$root.customAlert(configAlert.NOT_FOUND) 
        }
      })    
    }
  },
  mounted: function(){
    var currentContext = this
    EventBus.$on('SELECT_ARTICLE', (lotId, lotType) => {
      currentContext.$data.selectedArticleId = lotId
      currentContext.$data.selectedArticleType = lotType
      currentContext.$data.selectedArticle = currentContext.getArticle(lotId) 
    })
  }
}
</script>
<style scoped>
h2 {
  font-family: 'Oswald', sans-serif;
  font-size: 2.25em;
  margin: .75em 0 0 1.25em;
}
.green {
  color: rgb(122, 179, 132);
}

.intense-green {
  color: rgb(29, 167, 54); 
}

.small-text {
  margin-top: .25em;
  font-size: .75em;
}
h4 {
  margin: 0 0 0 2em;
}
#lots-table {
  margin: 0 3em 0 3em;
}
#details-trigger {
  margin: 0;
  display: flex;  
  flex-direction: row;  
  align-items: center;
}
#details-button {
  margin-left: 1em;
}

#sidebar-content {
  padding: 0em 1em 1em 1em;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  color: black;
}

#img {
  margin: 1em 0 .5em 0;
  width: 250px;
  height: 250px;
  border-radius: 10px;
  box-shadow: 0px 0px 15px -1px rgba(0,0,0,.35);
  background-color: rgba(0,0,0,.5);
}

#divider {
  width: 100%;
  margin: 1em 0 1em 0; 
}

#description-title {
  margin-left: 0;
  font-family: 'Oswald', sans-serif;
  font-size: 2em;
  color: rgba(0,0,0,.8)
}

.info-card {
  width: 100%;
  margin-top: 1em;
}

.bold {
  font-weight: bold;
}

.slide-rotate-hor-top {
	animation: slide-rotate-hor-top 0.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

.slide-reverse-rotate-hor-top {
  animation: slide-reverse-rotate-hor-top 0.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

@keyframes slide-rotate-hor-top {
  0% {    
    transform: translateY(0) rotateX(0deg);
    height: 250px;
  }
  100% {    
    transform: translateY(-150px) rotateX(-90deg);
    height: 0;
  }
}

@keyframes slide-reverse-rotate-hor-top {
  0% {    
    transform: translateY(-150px) rotateX(-90deg);    
    height: 0;
  }
  100% {    
    transform: translateY(0) rotateX(0deg);    
    height: 250px;
  }
}
</style>