<template>
  <b-modal
    id="modify-bill-modal"
    header-bg-variant="warning"
    header-text-variant="dark"
    title="Modificar billete">

    <template v-slot:default>
      <form ref="form">
        <b-form-select
          id="delete-lot"
          v-model="modSelectedBill"
          required>
          <b-form-select-option :value="null">Seleccionar billete...</b-form-select-option>
          <b-form-select-option
            v-for="bill in billsInfo" 
            :key="bill.lotId"
            :value="bill.lotId">
            ID: {{bill.lotId}} - {{bill.itemValue}} PTS - {{bill.emissionDay}}/{{bill.emissionMonth}}/{{bill.emissionYear}} - {{bill.conservationStatus}}
          </b-form-select-option>
        </b-form-select>
        <hr>
        <b-form-group label="Valor del billete">
          <b-input-group>
            <b-form-input 
              class="prev-value"
              :value="selectedBillInfo != null ? selectedBillInfo.itemValue : '' "
              disabled>
            </b-form-input>
            <b-form-input id="item-value" required></b-form-input>
            <b-input-group-append is-text>Pts</b-input-group-append>
          </b-input-group>
        </b-form-group>
        <b-form-group label="Fecha de emisión" label-for="emission-date">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="
                selectedBillInfo != null ?
                selectedBillInfo.emissionDay + '/' + selectedBillInfo.emissionMonth + '/' + selectedBillInfo.emissionYear : ''"
              disabled>
            </b-form-input>
            <b-form-input type="date" id="emission-date" required></b-form-input>
          </b-input-group>
        </b-form-group>
        <b-form-group label="Estado de conservación" label-for="conservation-status">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="selectedBillInfo != null ? selectedBillInfo.conservationStatus : ''"
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
          :description=" 'ID:' + (selectedBillInfo != null ? + selectedBillInfo.lotId : '')"
          label="Precio"
          label-for="item-price">
          <b-input-group>
            <b-form-input
              class="prev-value"
              :value="selectedBillInfo != null ? selectedBillInfo.price : ''"
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
  name: "ModBillModal",
  data: function() {
    return {
      billsInfo: null,
      modSelectedBill: null,
      disableSelection: false,
      selectedBillInfo: null,
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
    // Whenever modSelectedBill changes, this function will run
    modSelectedBill: function (){
      if(this.$data.modSelectedBill === null){
        this.$data.selectedBillInfo = null
        return
      }
      this.$data.selectedBillInfo = this.getBillInfo(this.$data.modSelectedBill);
    }  
  },
  methods: {
    getBillInfo: function(lotId){

      var currentContext = this
      var ls = localStorage
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.get + lotId,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.$data.selectedBillInfo = res.data 
      })
      .catch(()=> {
        this.$root.customAlert(configAlert.GENERIC_ERROR)
      })
    },

    getBillsInfo: function(){

      var ls = localStorage
      var currentContext = this
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.billsInfo = res.data
      })
    }
  },
  beforeMount: function(){
    this.getBillsInfo();
  },
  mounted: function(){
    var currentContext = this
    EventBus.$on('BILLS_UPDATED', () => {currentContext.getBillsInfo()})
    EventBus.$on('LOAD_MOD_BILL_CONSULT', (lotId) => {
      currentContext.modSelectedBill = lotId
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