<template>
  <b-modal
    id="modify-coin-modal"
    header-bg-variant="warning"
    header-text-variant="dark"
    title="Modificar coinete">

    <template v-slot:default>
      <form ref="form">
        <b-form-select
          id="delete-lot"
          v-model="modSelectedCoin"
          required>
          <b-form-select-option :value="null">Seleccionar moneda...</b-form-select-option>
          <b-form-select-option
            v-for="coin in coinsInfo" 
            :key="coin.lotId"
            :value="coin.lotId">
            ID: {{coin.lotId}} - {{coin.itemValue}} PTS - {{coin.emissionYear}} - {{coin.conservationStatus}}
          </b-form-select-option>
        </b-form-select>
        <hr>
        <b-form-group label="Valor de la moneda">
          <b-input-group>
            <b-form-input 
              class="prev-value"
              :value="selectedCoinInfo != null ? selectedCoinInfo.itemValue : '' "
              disabled>
            </b-form-input>
            <b-form-input id="item-value" required></b-form-input>
            <b-input-group-append is-text>Pts</b-input-group-append>
          </b-input-group>
        </b-form-group>
        <b-form-group label="Año de emisión" label-for="emission-year">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="
                selectedCoinInfo != null ?
                selectedCoinInfo.emissionYear : ''"
              disabled>
            </b-form-input>
            <b-form-input type="date" id="emission-year" required></b-form-input>
          </b-input-group>
        </b-form-group>
        <b-form-group label="Estado de conservación" label-for="conservation-status">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="selectedCoinInfo != null ? selectedCoinInfo.conservationStatus : ''"
              disabled>
            </b-form-input>
            <b-form-select
              id="conservation-status"
              v-model="conservationStatusSelected"
              :options="conservationStatusOptions"
              required>
            </b-form-select>
          </b-input-group>
        </b-form-group>
        <b-form-group
          :description=" 'ID:' + (selectedCoinInfo != null ? + selectedCoinInfo.lotId : '')"
          label="Precio"
          label-for="item-price">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="selectedCoinInfo != null ? selectedCoinInfo.price : ''"
              disabled>
            </b-form-input>
            <b-form-input type="number" id="item-price"></b-form-input>
            <b-input-group-append is-text>€</b-input-group-append>
          </b-input-group>
        </b-form-group>      
      </form>
    </template>

    <template v-slot:modal-footer="{ cancel }">
      <b-button size="sm" variant="outline-dark" @click="cancel()">Cancelar</b-button>
      <!-- TODO: Gestionar envío de información -->
      <b-button size="sm" variant="warning" @click="ok()">Guardar</b-button>
    </template>
  </b-modal>
</template>
<script>
import axios from 'axios';
import config from '../../util/config';
import configAlert from '../../util/configAlert';
import EventBus from '../../util/eventBus';

export default {
  name: "ModCoinModal",
  data: function() {
    return {
      coinsInfo: null,
      modSelectedCoin: null,
      disableSelection: false,
      selectedCoinInfo: null,
      conservationStatusOptions: [
          { value: null,  text: 'Selecciona una opción...', disabled: true},
          { value: 'BC',  text: 'BC - Buena conservación' },
          { value: 'MBC', text: 'MBC - Muy buena conservación' },
          { value: 'EBC', text: 'EBC - Excelentemente buena conservación' },
          { value: 'SC',  text: 'SC - Sin circular' }
      ],
      conservationStatusSelected: null
    }
  },
  watch: {
    // Whenever modSelectedCoin changes, this function will run
    modSelectedCoin: function (){
      if(this.$data.modSelectedCoin === null){
        this.$data.selectedCoinInfo = null
        return
      }
      this.$data.selectedCoinInfo = this.getCoinInfo(this.$data.modSelectedCoin);
    }  
  },
  methods: {
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
        currentContext.$data.selectedCoinInfo = res.data 
      })
      .catch(()=> {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
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
    }
  },
  beforeMount: function(){
    this.getCoinsInfo();
  },
  mounted: function(){
    var currentContext = this
    EventBus.$on('COINS_UPDATED', () => {currentContext.getCoinsInfo()})
    EventBus.$on('LOAD_MOD_COIN_CONSULT', (lotId) => {
      currentContext.modSelectedCoin = lotId
    })
  }
}
</script>
<style scoped>
.prev-value {
  min-width: 110px !important;
  max-width: 110px;
}
</style>