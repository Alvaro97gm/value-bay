<template>
  <b-modal
    id="consult-bill-modal"
    header-bg-variant="dark"
    header-text-variant="light"
    size="lg"
    title="Consultar billetes"
    scrollable>

    <template v-slot:default>
      <div id="bill-info">
        <div id="cubo"></div>
        <form id="info-fields">
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">ID</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedBill != null ? loadedBill.lotId : ''"
              id="input-bill-insert-id"
              disabled>
            </b-form-input>
            <b-input-group-prepend is-text>
              <span class="bold">Valor</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedBill != null ? loadedBill.itemValue + ' Pts.' : ''"
              id="input-bill-value"
              disabled>
            </b-form-input>            
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Fecha de emisión</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedBill != null ?
                loadedBill.emissionDay + '/' + loadedBill.emissionMonth +  '/' + loadedBill.emissionYear
                : ''"
              id="input-bill-emission"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Conservación</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedBill != null ?
                loadedBill.conservationStatus + ' - ' + loadedBill.conservationStatusDetailed : ''"
              id="input-bill-conservation"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Precio</span>
            </b-input-group-prepend>
            <b-form-input
              :value="loadedBill != null ?
                loadedBill.price + '€': ''"
              id="input-bill-price"              
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group>
            <b-button @click="loadModBillModal()" class="action-button" size="sm" variant="outline-warning">
              Modificar
            </b-button> 
            <b-button @click="delSelectedBill()" class="action-button" size="sm" variant="outline-danger">
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
            id="input-bill-id"
            type="number"
            placeholder="Introducir ID...">
          </b-form-input>
          <b-input-group-append>
            <b-button @click="manualSearch()" size="sm" variant="dark">Buscar</b-button>
          </b-input-group-append>
        </b-input-group>
        <b-input-group id="show-bills-listing">
          <b-form-checkbox 
            v-b-tooltip.hover.right title="Mostar listado de billetes"
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
        v-if="billsInfo != [] & showListing"
        responsive="md"
        :items="processedBillsInfo"
        :fields="fields"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        @row-selected="setConsultSelectedBill($event)">
      </b-table>
      <h6 id="no-bills" v-else-if="showListing">No existen billetes en el sistema.</h6>      
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
  name: "ConsultBillModal",
  data: function(){
    return {
      manualSearchValue: null,
      billsInfo: [],
      fields: [
        { key: "ID", sortable: true },
        { key: "Valor", sortable: true },
        { key: "Fecha de emisión", sortable: false },
        { key: "Conservación", sortable: true },
        { key: "Precio", sortable: true }
      ],
      processedBillsInfo: [],
      loadedBill: null,
      sortBy: 'ID',
      sortDesc: false,
      consultSelectedBill: null,
      showListing: false
    }
  },
  methods:{
    setConsultSelectedBill: function(event){
      if (event.length > 0){
        this.consultSelectedBill = event[0].ID
        this.getBillInfo(this.consultSelectedBill);
      }      
    },

    manualSearch: function(){
      var lotId = this.manualSearchValue
      if(lotId === 0){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return        
      }
      this.consultSelectedBill = lotId;
      this.getBillInfo(lotId);  
    },

    processBillsInfo: function(){
      this.processedBillsInfo = []
      if(this.billsInfo != []){
        this.billsInfo.forEach(billInfo => {
          this.processedBillsInfo.push({
            "ID": billInfo.lotId,
            "Valor": billInfo.itemValue,
            "Fecha de emisión": billInfo.emissionDay + '/' + billInfo.emissionMonth + '/' + billInfo.emissionYear,
            "Conservación": billInfo.conservationStatus,
            "Precio": billInfo.price
          })
        })  
      }      
    },

    loadModBillModal: function(){
      if(this.consultSelectedBill === null){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return
      }

      EventBus.$emit('LOAD_MOD_BILL_CONSULT', this.consultSelectedBill)
    },

    delSelectedBill: function(){
      if(this.consultSelectedBill === null){
        this.$root.customAlert(configAlert.NOT_SELECTED_ITEM)
        return
      }

      var currentContext = this
      var ls = localStorage
      axios({
        method: 'delete',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.delete + this.consultSelectedBill,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        if(res.status === 200){
          currentContext.$data.loadedBill = null;
          currentContext.$data.consultSelectedBill = null;
          EventBus.$emit('BILLS_UPDATED')
          this.$root.customAlert(configAlert.DELETE_OK)
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR)
        }
      })
    },

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
        if (res.status === 200)
          currentContext.$data.loadedBill = res.data
      })
      .catch(res => {
        if (res.status === 404){
          this.$root.customAlert(configAlert.NOT_FOUND)
        }else{
          this.$root.customAlert(configAlert.GENERIC_ERROR)
        }
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
      .then(() => {
        currentContext.processBillsInfo();
      })
    }
  },

  beforeMount: function(){
    this.getBillsInfo();
    EventBus.$on('BILLS_UPDATED', () => {this.getBillsInfo()});
  }
}
</script>
<style scoped>
#consult-bill-modal {
  padding: 0 5em 0 5em;
}

#bill-info {
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

#no-bills {
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