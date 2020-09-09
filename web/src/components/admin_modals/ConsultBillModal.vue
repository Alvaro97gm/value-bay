<template>
  <b-modal
    id="consult-bill-modal"
    header-bg-variant="dark"
    header-text-variant="light"
    size="lg"
    title="Consultar billetes"
    scrollable>

    <template v-slot:default="{ ok }">
      <div id="bill-info">
        <div id="cubo"></div>
        <form id="info-fields">
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">ID</span>
            </b-input-group-prepend>
            <b-form-input
              id="input-bill-id"
              disabled>
            </b-form-input>            
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Fecha de emisión</span>
            </b-input-group-prepend>
            <b-form-input
              id="input-bill-emission"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Conservación</span>
            </b-input-group-prepend>
            <b-form-input
              id="input-bill-conservation"
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group class="info-field">
            <b-input-group-prepend is-text>
              <span class="bold">Precio</span>
            </b-input-group-prepend>
            <b-form-input
              id="input-bill-price"              
              disabled>
            </b-form-input>
          </b-input-group>
          <b-input-group>
            <b-button class="action-button" size="sm" variant="outline-warning">
              Modificar
            </b-button> 
            <b-button class="action-button" size="sm" variant="outline-danger">
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
            id="input-bill-id"
            type="number"
            placeholder="Introducir ID..."
            @keyup.enter="ok()">
          </b-form-input>
          <b-input-group-append>
            <b-button size="sm" variant="dark">Buscar</b-button>
          </b-input-group-append>
        </b-input-group>
        <b-input-group id="show-bills-listing">
          <b-form-checkbox 
            v-b-tooltip.hover.right title="Mostar listado de billetes"
            v-model="showListing"
            button-variant="outline-success" button>
            <b-icon icon="card-list"></b-icon>
          </b-form-checkbox>
        </b-input-group>
      </form>
      <hr v-if="showListing">    
      <b-table
        hover
        selectable
        sticky-header
        select-mode="single"
        v-if="showListing"
        responsive="md"
        :items="processedBillsInfo"
        :fields="fields"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc">
      </b-table>      
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

export default {
  name: "ConsultBillModal",
  data: function(){
    return {
      billsInfo: [],
      fields: [
        { key: "ID", sortable: true, isRowHeader: true, variant: 'outline-success' },
        { key: "Valor", sortable: true },
        { key: "Fecha de emisión", sortable: false },
        { key: "Conservación", sortable: true },
        { key: "Precio", sortable: true }
      ],
      processedBillsInfo: [],
      sortBy: 'ID',
      sortDesc: false,
      consultSelectedBill: null,
      showListing: false
    }
  },
  methods:{
    processBillsInfo: function(){
      
      this.billsInfo.forEach(billInfo => {
        this.processedBillsInfo.push({
          "ID": billInfo.lotId,
          "Valor": billInfo.itemValue,
          "Fecha de emisión": billInfo.emissionDay + '/' + billInfo.emissionMonth + '/' + billInfo.emissionYear,
          "Conservación": billInfo.conservationStatus,
          "Precio": billInfo.price
        })
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
    },
    onRowSelected: function(){
      this.consultSelectedBill = null
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