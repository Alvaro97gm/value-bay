<template>
  <b-modal
      id="add-coin-modal"
      header-bg-variant="success"
      header-text-variant="light"
      title="Añadir moneda">

      <template v-slot:default>
        <form ref="form">
          <b-form-group label="Valor de la moneda">
            <b-input-group>
              <b-form-input v-model="selectedValue" type="number" id="item-value" required></b-form-input>
              <b-input-group-append is-text>Pts</b-input-group-append>
            </b-input-group>
          </b-form-group>
          <b-form-group label="Año de emisión" label-for="emission-year">
            <b-form-input v-model="selectedYear" type="number" id="emission-year" required></b-form-input>
          </b-form-group>
          <b-form-group label="Estado de conservación" label-for="conservation-status">          
            <b-form-select
              id="conservation-status"
              v-model="selectedConsStatus"
              :options="options"
              required>
            </b-form-select>
          </b-form-group>
          <b-form-group
            description="El ID del artículo será generado automaticamente"
            label="Precio"
            label-for="item-price">
            <b-input-group>
              <b-form-input
                v-model="selectedPrice"
                type="number"
                id="item-price"
                @keyup.enter="addCoin()"
                required>
              </b-form-input>
              <b-input-group-append is-text>€</b-input-group-append>
            </b-input-group>
          </b-form-group>      
        </form>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button size="sm" variant="outline-dark" @click="cancel()">
          Cancelar
        </b-button>
        <b-button size="sm" variant="success" @click="addCoin()">
          Guardar
        </b-button>        
      </template>
    </b-modal>
</template>
<script>
import axios from 'axios';
import config from '../../util/config';
import configAlert from '../../util/configAlert';
import EventBus from '../../util/eventBus';

export default {
  name: "AddCoinModal",
  data: function() {
    return {      
      options: [
          { value: null,  text: 'Selecciona una opción...', disabled: true},
          { value: 'BC',  text: 'BC - Buena conservación' },
          { value: 'MBC', text: 'MBC - Muy buena conservación' },
          { value: 'EBC', text: 'EBC - Excelentemente buena conservación' },
          { value: 'SC',  text: 'SC - Sin circular' }
      ],
      selectedValue: null,      
      selectedYear: null,            
      selectedConsStatus: null, 
      selectedPrice: null,    
    }
  },
  methods: {

    addCoin: function(){      
      var reqParams = this.createParams();
      if(reqParams === null){
        this.$root.customAlert(configAlert.FORM_NOT_COMPLETE)
        return
      }
      
      var currentContext = this
      var ls = localStorage
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.insert,
        params: reqParams,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        if(res.status === 200){
          this.$root.customAlert(configAlert.ADD_OK);
          EventBus.$emit('COINS_UPDATED')
          currentContext.resetValues()
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR);
        }        
      }).catch(() => {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },
    validateForm: function(){
      return (
        this.selectedValue != null        && 
        this.selectedYear != null         &&
        this.selectedConsStatus != null   &&
        this.selectedPrice != null
      )
    },
    createParams: function(){

      if(this.validateForm()){
        return {
          itemValue: this.selectedValue,
          emissionYear: this.selectedYear,
          conservationStatus: this.selectedConsStatus,
          price: this.selectedPrice
        }
      }
      return null;
    },
    resetValues: function(){
      this.selectedValue = null
      this.selectedYear = null
      this.selectedConsStatus = null
      this.selectedPrice = null
    }
  }
}
</script>
<style scoped></style>