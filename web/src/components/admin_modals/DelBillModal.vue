<template>
  <b-modal
    id="delete-bill-modal"
    header-bg-variant="danger"
    header-text-variant="light"
    title="Eliminar billete">

    <template v-slot:default>
      <form ref="form">
        <b-form-group 
          :description="'ID: ' + (delSelectedBill != null ? + delSelectedBill : '')"
          label="Selecciona el artículo a eliminar"
          label-for="delete-lot">
          <b-form-select
            id="delete-lot"
            v-model="delSelectedBill"
            required>
            <b-form-select-option :value="null">Artículos...</b-form-select-option>
            <b-form-select-option
              v-for="bill in billsInfo" 
              :key="bill.lotId"
              :value="bill.lotId">
              ID: {{bill.lotId}} - {{bill.itemValue}} PTS - {{bill.emissionDay}}/{{bill.emissionMonth}}/{{bill.emissionYear}} - {{bill.conservationStatus}}
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
  name: "DelBillModal",
  data: function(){
    return {
      delSelectedBill: null,
      billsInfo: null
    }    
  },
  methods: {
    deleteBill: function(){

      var currentContext = this
      var ls = localStorage
      axios({
        method: 'delete',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.delete + this.delSelectedBill,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      }).then(res => {
        if(res.status === 200){
          this.$root.customAlert(configAlert.DELETE_OK)
          currentContext.getBillsInfo();
          currentContext.delSelectedBill = null;
          EventBus.$emit('BILLS_UPDATED')
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
  }
}
</script>
<style scoped></style>