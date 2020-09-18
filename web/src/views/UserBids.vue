<template>
  <div id="container">
    <h1>Mis Pujas</h1>
    <div class="card">
      <b-table
      id="lots"
      hover
      sticky-header
      responsive="md"
      :items="bids">
    </b-table>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import config from '../util/config';

export default {
  name: 'UserBids',
  data: function(){
    return {
      bids: [],
      fields: [
        { key: "ID", isRowHeader: true },
        { key: "Lote"},
        { key: "Cantidad (€)" }
      ]
    }
  },
  methods: {
    processBids: function(bids){
      console.log(bids)
      bids.forEach(bid => {
        let urlPath
        if(bid.fkCoin != null){
          urlPath = config.APIEndpoints.Lot.Coin.get + bid.fkCoin      
        }else{
          urlPath = config.APIEndpoints.Lot.Bill.get + bid.fkBill
        }

        var currentContext = this
        var ls = localStorage
        axios({
          method: 'get',
          url: config.serverURL + urlPath,
          headers: {
            'Authorization': ls.getItem('jwt'),
            'Content-Type': 'application/json'
          }
        }).then(res => {
          var lot
          if(res.data.emissionDay === undefined){
            lot = 'Moneda de ' + res.data.itemValue + ' pesetas de ' + res.data.emissionYear
          }else{
            lot = 'Billete de ' + res.data.itemValue + ' pesetas de ' + res.data.emissionYear
          }

          currentContext.$data.bids.push({
            'ID': bid.bidId,
            'Lote': lot,
            'Cantidad (€)': bid.amount
          })
        })
        
      });
    },
    getUserBids: function(){
      var currentContext = this
      var ls = localStorage
      var userData = JSON.parse(ls.getItem('userData'))
      console.log(userData)
      axios({
        get: 'get',
        url: config.serverURL + config.APIEndpoints.Bid.getByUserId + userData.userId,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        currentContext.processBids(res.data)
      })
    }
  },
  mounted: function(){
    this.getUserBids()
  }
}
</script>
<style scoped>
#container {
  margin-top: 2em;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: rgba(0, 0, 0, .25);
}
.card {
  width: 60%;
  height: 70vh;
}
h1 {
  margin-bottom: 1em;
  font-family: 'Oswald', sans-serif;
}
</style>