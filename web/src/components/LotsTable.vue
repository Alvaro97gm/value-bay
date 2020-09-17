<template>
  <div>
    <b-table
      id="lots"
      hover
      selectable
      
      no-border-collapse
      sticky-header="75vh"
      selected-variant="success"        
      select-mode="single"
      v-if="lotsInfo != []"
      responsive="md"
      :items="processedLotsInfo"
      :fields="fields"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      @row-selected="setSelectedArticle($event)">
    </b-table>
  </div>
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import EventBus from '../util/eventBus';

export default {
  name: "LotsTable",
  data: function() {
    return {
      sortBy: 'ID',
      sortDesc: false,
      fields: [
        { key: "ID", sortable: true, isRowHeader: true },
        { key: "Tipo", sortable: true},
        { key: "Valor", sortable: true },
        { key: "Fecha", sortable: false },
        { key: "Conservación", sortable: true },
        { key: "Precio", sortable: true },
        { key: "Subasta", sortable: true }
      ],
      lotsInfo: null,
      processedLotsInfo: null,
      selectedLotId: null
    }
  },
  methods: {
    setSelectedArticle(event){
      this.selectedLotId = event[0].ID; 
      EventBus.$emit('SELECT_ARTICLE', this.selectedLotId, event[0].Tipo)
    },
    showSidebar(){
      return
    },
    processLotsInfo: function(){
      this.processedLotsInfo = []
      var processedLot = {}
      this.lotsInfo.forEach(lot => {
        
        // Bill
        if (lot.emissionDay != null) {
          processedLot = {
            "ID": lot.lotId,
            "Tipo": "Billete",
            "Valor": lot.itemValue,
            "Fecha": lot.emissionDay + '/' + lot.emissionMonth + '/' + lot.emissionYear,
            "Conservación": lot.conservationStatus + ' - ' + lot.conservationStatusDetailed,
            "Precio": lot.price,
            "Subasta": "NOT_IMPLEMENTED"
          }
        // Coin
        } else {
            processedLot = {
            "ID": lot.lotId,
            "Tipo": "Moneda",
            "Valor": lot.itemValue,
            "Fecha": lot.emissionYear,
            "Conservación": lot.conservationStatus + ' - ' + lot.conservationStatusDetailed,
            "Precio": lot.price,
            "Subasta": "NOT_IMPLEMENTED"
          }
        }
        
        this.processedLotsInfo.push(processedLot)
      })
    },
    getLotsInfo: function(){
      
      this.lotsInfo = []
      var currentContext = this
      var ls = localStorage

      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Coin.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-type': 'application/json'
        }
      })
      .then(res => {
        res.data.forEach(coin => {
          currentContext.$data.lotsInfo.push(coin)  
        });
      })
      .then(() => {
        axios({
          method: 'get',
          url: config.serverURL + config.APIEndpoints.Lot.Bill.getAll,
          headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-type': 'application/json'
          }
        })
        .then(res => {
          res.data.forEach(bill => {
            currentContext.$data.lotsInfo.push(bill)  
          });              
        })
        .then(() => {
          currentContext.processLotsInfo();
        })
      })
    }
  },
  mounted: function(){
    this.getLotsInfo()
  }
}
</script>
<style scoped></style>