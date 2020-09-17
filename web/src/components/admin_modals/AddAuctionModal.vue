<template>
  <b-modal
    id="add-auction-modal"
    header-bg-variant="success"
    header-text-variant="light"
    title="Añadir subasta">
    <template v-slot:default>
      <form ref="form">
        <b-form-group description="El ID de la subasta será generado automaticamente">
          <b-input-group>
            <b-input-group-prepend
              is-text
              v-b-tooltip.hover.top title="Nombre público de la subasta">
              <b-icon icon="type"></b-icon>
            </b-input-group-prepend>
            <b-form-input
              id="auction-name" 
              v-model="selectedName"
              placeholder="Nombre"
              type="text"
              required>
            </b-form-input>
            <b-input-group-addon
              v-b-tooltip.hover.top title="En horas"
              is-text>
              <b-icon icon="clock"></b-icon>
            </b-input-group-addon>
            <b-form-input
              id="auction-duration"              
              v-model="selectedDuration"
              placeholder="Duración"
              type="number"
              required></b-form-input>            
          </b-input-group>
        </b-form-group>
        <b-form-group>
          <b-input-group>
            <b-form-checkbox button-variant="success" v-model="initActive"/>
            <label> 
              Marcar 
              <span 
                v-b-tooltip.hover.bottom title=
                  "Las pujas podrán comenzar inmediatamente después de crear la subasta."
                class="green">
                activa
              </span>
              desde el inicio
            </label>
          </b-input-group>
          <b-input-group>
            <b-form-checkbox button-variant="success" v-model="scheduleActive"/>
            <label> 
              Programar
              <span 
                v-b-tooltip.hover.bottom title=
                  "Momento en que podrán comenzar las pujas"
                class="green">
                activación
              </span>              
            </label>
            <b-input-group v-if="scheduleActive">
              <b-form-datepicker
                placeholder="Fecha"
                :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                selected-variant="success">
                </b-form-datepicker>
              <b-form-timepicker selected-variant="success" placeholder="Hora"></b-form-timepicker>
            </b-input-group>
          </b-input-group>
        </b-form-group>

        <h4>Lotes</h4>
        <hr>        
        <ul v-if="selectedLots.length > 0" class="list-inline d-inline-block">
          <li v-for="lot in selectedLots" :key="lot.lotId" class="list-inline-item">
            <b-badge variant="success">
              {{lot.lotId}}
              <b-icon
                class="del-lot-icon"
                icon="x-circle"
                variant="light"                
                v-b-tooltip.hover.top title="Eliminar"
                @click="removeLot(lot)">
              </b-icon>
            </b-badge>
          </li>
        </ul>
        <b-dropdown id="first-dropdown" size="md" variant="outline-success" block menu-class="w-100">
          <template v-slot:button-content>
            <b-icon icon="cash"></b-icon> Seleccionar billetes
          </template>
          <b-dropdown-item-button
            v-for="lot in availableBills"
            :key="lot.lotId"
            @click="selectLot(lot)">
            ID: {{lot.lotId}} - {{lot.itemValue}} PTS - {{lot.emissionDay}}/{{lot.emissionMonth}}/{{lot.emissionYear}} - {{lot.conservationStatus}}
          </b-dropdown-item-button>
          <b-dropdown-text v-if="availableBills.length === 0">
            No existen lotes por añadir
          </b-dropdown-text>            
        </b-dropdown>
        <b-dropdown size="md" variant="outline-success" block menu-class="w-100">
          <template v-slot:button-content>
            <b-icon icon="slash-circle-fill"></b-icon> Seleccionar monedas
          </template>
          <b-dropdown-item-button
            v-for="lot in availableCoins"
            :key="lot.lotId"
            @click="selectLot(lot)">
            ID: {{lot.lotId}} - {{lot.itemValue}} PTS - {{lot.emissionYear}} - {{lot.conservationStatus}}
          </b-dropdown-item-button>
          <b-dropdown-text v-if="availableCoins.length === 0">
            No existen lotes por añadir
          </b-dropdown-text>
        </b-dropdown>                        
      </form>
    </template>

    <template v-slot:modal-footer="{ cancel }">
      <b-button size="sm" variant="outline-dark" @click="cancel()">
        Cancelar
      </b-button>
      <!-- Gestionar envío de información -->
      <b-button size="sm" variant="success" @click="addBill()">
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
  name: "AddAuctionModal",
  data: function() {
    return {      
      selectedName: null,
      selectedDuration: null,
      initActive: false,
      scheduleActive: false,
      availableBills: [],
      availableCoins: [],
      selectedLots: []
    }
  },
  watch: {
    initActive: function (){
      if(this.initActive)
        this.scheduleActive = false
    },
    scheduleActive: function(){
      if(this.scheduleActive)
        this.initActive = false
    }
  },
  methods: {
    selectLot(lot) {
      this.selectedLots.push(lot)
      if(lot.emissionDay === undefined){
        this.availableCoins.splice(this.availableCoins.indexOf(lot), 1)
      } else {
        this.availableBills.splice(this.availableBills.indexOf(lot), 1) 
      } 
    },

    removeLot: function(lot){
      this.selectedLots.splice(this.selectedLots.indexOf(lot), 1)
      if(lot.emissionDay === undefined){
        this.availableCoins.push(lot)  
      } else {
        this.availableBills.push(lot)
      }
    },

// ==============================================
// TODO: Modelar las subastas para soportar timing, nombre, duración...
// ==============================================

    // TODO: Los que no tengan subasta - Nuevo endpoint 
    getFreeBills: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.getFree,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.availableBills = res.data
      })
    },

    // TODO: Los que no tengan subasta - Nuevo endpoint 
    getFreeCoins: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.getFree,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.availableCoins = res.data
      })
    },

    addBill: function(){      
      var reqParams = this.createParams();
      if(reqParams === null){
        this.$root.customAlert(configAlert.FORM_NOT_COMPLETE)
        return
      }
      
      var currentContext = this
      var ls = localStorage
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.insert,
        params: reqParams,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        if(res.status === 200){
          this.$root.customAlert(configAlert.ADD_OK);
          EventBus.$emit('BILLS_UPDATED')
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR);
        }
        currentContext.resetValues()
      }).catch(() => {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },
    updateData: function(){
      this.getFreeBills();
      this.getFreeCoins();
    }
  },
  beforeMount: function(){
    this.updateData()
    var currentContext = this
    EventBus.$on('BILLS_UPDATED', () => {currentContext.updateData()})
  }
}
</script>
<style scoped>
.green {
  color: rgb(122, 179, 132);
}
h4 {
  text-align: center;
  font-family: 'Oswald', sans-serif;
}
#auction-name {
  max-width: 50%;
}
#auction-duration {
  max-width: 30%;
}
.del-lot-icon {
  margin-left: .25em;
}
.del-lot-icon:hover {
  cursor: pointer;
}
#first-dropdown {
  margin-bottom: .5em;
}
</style>