<template>
  <b-modal
    id="consult-coin-modal"
    header-bg-variant="dark"
    header-text-variant="light"
    size="lg"
    title="Consultar monedas"
    scrollable>

    <template v-slot:default>
      <div id="coin-info">
        <div id="cubo"></div>
        <form id="info-fields">
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">ID</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedCoin != null ? loadedCoin.lotId : ''"
              id="input-coin-insert-id"
              disabled>
            </b-form-input>
            <b-input-group-prepend is-text>
              <span class="bold">Valor</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedCoin != null ? loadedCoin.itemValue + ' Pts.' : ''"
              id="input-coin-value"
              disabled>
            </b-form-input>            
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Año de emisión</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedCoin != null ? loadedCoin.emissionYear : ''"
              id="input-coin-emission"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Conservación</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedCoin != null ?
                loadedCoin.conservationStatus + ' - ' + loadedCoin.conservationStatusDetailed : ''"
              id="input-coin-conservation"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Precio</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedCoin != null ?
                loadedCoin.price + '€': ''"
              id="input-coin-price"              
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group>
            <b-button @click="loadModCoinModal()" class="action-button" size="sm" variant="outline-warning">
              Modificar
            </b-button> 
            <b-button @click="delSelectedCoin()" class="action-button" size="sm" variant="outline-danger">
              Eliminar
            </b-button> 
          </b-input-group>
        </form>
      </div>
      
      <hr>
      <form id="search">
        <b-input-group id="manual-id-search">
          <b-input-group-prepend is-text>           
            <b-icon variant="dark" icon="search"></b-icon>
          </b-input-group-prepend>
          <b-form-input
            v-model="manualSearchValue"
            id="input-coin-id"
            type="number"
            placeholder="Introducir ID...">
          </b-form-input>
          <b-input-group-append>
            <b-button @click="manualSearch()" size="sm" variant="dark">Buscar</b-button>
          </b-input-group-append>
        </b-input-group>
        <b-input-group id="show-coins-listing">
          <b-form-checkbox 
            v-b-tooltip.hover.right title="Mostar listado de coinetes"
            v-model="showListing"
            button-variant="outline-success"
            button>
            <b-icon icon="card-list"></b-icon>
          </b-form-checkbox>
        </b-input-group>
      </form>
      <hr v-if="showListing">    
      <b-table
        hover
        selectable
        sticky-header
        selected-variant="success"        
        select-mode="single"
        v-if="coinsInfo != [] & showListing"
        responsive="md"
        :items="processedCoinsInfo"
        :fields="fields"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        @row-selected="setConsultSelectedCoin($event)">
      </b-table>
      <h6 id="no-coins" v-else-if="showListing">No existen monedas en el sistema.</h6>      
    </template>

    <template v-slot:modal-footer="{ cancel }">
      <b-button size="sm" variant="dark" @click="cancel()">
        Salir
      </b-button>        
    </template>
  </b-modal>
</template>
<script>
import axios from 'axios';
import config from '../../util/config';
import EventBus from '../../util/eventBus';
import configAlert from '../../util/configAlert';

export default {
  name: "ConsultCoinModal",
  data: function(){
    return {
      manualSearchValue: null,
      coinsInfo: [],
      fields: [
        { key: "ID", sortable: true },
        { key: "Valor", sortable: true },
        { key: "Año de emisión", sortable: false },
        { key: "Conservación", sortable: true },
        { key: "Precio", sortable: true }
      ],
      processedCoinsInfo: [],
      loadedCoin: null,
      sortBy: 'ID',
      sortDesc: false,
      consultSelectedCoin: null,
      showListing: false
    }
  },
  methods:{
    setConsultSelectedCoin: function(event){
      if (event.length > 0){
        this.consultSelectedCoin = event[0].ID
        this.getCoinInfo(this.consultSelectedCoin);
      }      
    },

    manualSearch: function(){
      var lotId = this.manualSearchValue
      if(lotId === 0){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return        
      }
      this.consultSelectedCoin = lotId;
      this.getCoinInfo(lotId);  
    },

    processCoinsInfo: function(){
      this.processedCoinsInfo = []
      if(this.coinsInfo != []){
        this.coinsInfo.forEach(coinInfo => {
          this.processedCoinsInfo.push({
            "ID": coinInfo.lotId,
            "Valor": coinInfo.itemValue,
            "Año de emisión": coinInfo.emissionYear,
            "Conservación": coinInfo.conservationStatus,
            "Precio": coinInfo.price
          })
        })  
      }      
    },

    loadModCoinModal: function(){
      if(this.consultSelectedCoin === null){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return
      }

      EventBus.$emit('LOAD_MOD_COIN_CONSULT', this.consultSelectedCoin)
    },

    delSelectedCoin: function(){
      if(this.consultSelectedCoin === null){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return
      }

      var currentContext = this
      var ls = localStorage
      axios({
        method: 'delete',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.delete + this.consultSelectedCoin,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        if(res.status === 200){
          currentContext.$data.loadedCoin = null;
          currentContext.$data.consultSelectedCoin = null;
          EventBus.$emit('COINS_UPDATED')
          this.$root.customAlert(configAlert.DELETE_OK)
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR)
        }
      })
    },

    getCoinInfo: function(lotId){
      var currentContext = this
      var ls = localStorage
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.get + lotId,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        if (res.status === 200)
          currentContext.$data.loadedCoin = res.data
      })
      .catch(res => {
        if (res.status === 404){
          this.$root.customAlert(configAlert.NOT_FOUND)
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR)
        }
      })
    },

    getCoinsInfo: function(){
      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.coinsInfo = res.data
      })
      .then(() => {
        currentContext.processCoinsInfo();
      })
    }
  },

  beforeMount: function(){
    this.getCoinsInfo();
    EventBus.$on('COINS_UPDATED', () => {this.getCoinsInfo()});
  }
}
</script>
<style scoped>
#consult-coin-modal {
  padding: 0 5em 0 5em;
}

#coin-info {
  display: flex;
}

#info-fields {
  width: 60%;
}

.info-field {
  margin: .5em 0 .5em 0;
}

.action-button {
  margin-right: 1em;
  margin: .25em 1em 0 0;
}

#search {
  display: flex;
  justify-content: center;
}

#manual-id-search {
  margin-right: 1em;
}

#no-coins {
  margin: auto;
}

#cubo {  
  width: 200px;
  height: 230px; 
  border-radius: 10px;
  background-color: rgb(0,0,0,.25);
  margin-right: 1.5em;
  box-shadow: 0px 0px 10px 0px rgba(0,0,0,.4);
}

.bold {
  font-weight: bold;
}
</style>