<template>
  <b-modal
    id="delete-coin-modal"
    header-bg-variant="danger"
    header-text-variant="light"
    title="Eliminar moneda">

    <template v-slot:default>
      <form ref="form">
        <b-form-group 
          :description="'ID: ' + (delSelectedCoin != null ? + delSelectedCoin : '')"
          label="Selecciona el artículo a eliminar"
          label-for="delete-lot">
          <b-form-select
            id="delete-lot"
            v-model="delSelectedCoin"
            required>
            <b-form-select-option :value="null">Artículos...</b-form-select-option>
            <b-form-select-option
              v-for="coin in coinsInfo" 
              :key="coin.lotId"
              :value="coin.lotId">
              ID: {{coin.lotId}} - {{coin.itemValue}} PTS - {{coin.emissionYear}} - {{coin.conservationStatus}}
            </b-form-select-option>
          </b-form-select>
        </b-form-group>     
      </form>
    </template>

    <template v-slot:modal-footer="{ cancel }">
      <b-button size="sm" variant="outline-dark" @click="cancel()">
        Cancelar
      </b-button>
      <b-button size="sm" variant="danger" @click="deleteBill()">
        Eliminar
      </b-button>        
    </template>
  </b-modal>
</template>
<script>
import axios from 'axios';
import EventBus from '../../util/eventBus';
import config from '../../util/config';
import configAlert from '../../util/configAlert';


export default {
  name: "DelCoinModal",
  data: function(){
    return {
      delSelectedCoin: null,
      coinsInfo: null
    }    
  },
  methods: {
    deleteBill: function(){
      if(this.delSelectedCoin === null){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return
      }
      
      var currentContext = this
      var ls = localStorage
      axios({
        method: 'delete',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.delete + this.delSelectedCoin,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      }).then(res => {
        if(res.status === 200){
          this.$root.customAlert(configAlert.DELETE_OK)
          currentContext.getBillsInfo();
          currentContext.delSelectedCoin = null;
          EventBus.$emit('COINS_UPDATED')
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR)
        }
      }).catch(()=> {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },
    getBillsInfo: function(){

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
    this.getBillsInfo();
  },
  mounted: function(){
    var currentContext = this
    EventBus.$on('COINS_UPDATED', () => {currentContext.getBillsInfo()})
  }
}
</script>
<style scoped></style>