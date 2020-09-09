<template>
  <div id="root">
    <h2>Bienvenido, <span class="green-text">{{adminInfo != null ? adminInfo.firstName : ''}}</span></h2>
    <hr>
    <div class="row">
      <!-- Bills management -->
      <div class="actions-management">
        <h4> 
          <b-icon class="title-icon" variant="success" scale=1.25 icon="cash-stack"></b-icon>
          Gestión de <span class="green-text">Billetes</span>
        </h4>
        <div class="actions">
          <div v-b-modal.add-bill-modal class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div v-b-modal.delete-bill-modal class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div v-b-modal.modify-bill-modal class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div v-b-modal.consult-bill-modal class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
      <!-- Coins management -->
      <div class="actions-management">
        <h4> 
          <b-icon class="title-icon" scale=1 variant="success" icon="slash-circle-fill"></b-icon>
          Gestión de <span class="green-text">Monedas</span>
        </h4>
        <div class="actions">
          <div class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <!-- Auctions management -->
      <div class="actions-management">
        <h4> 
          <b-icon class="title-icon" variant="success" scale=1.25 icon="award"></b-icon>
          Gestión de <span class="green-text">Subastas</span>
        </h4>
        <div class="actions">
          <div class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
      <!-- Reports management -->
      <div class="actions-management">
        <h4>
          <b-icon class="title-icon" variant="success" scale=1.25 icon="receipt"></b-icon>
            Gestión de <span class="green-text">Reportes</span>
        </h4>
        <div class="actions">
          <div class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
    </div>
    <!-- MODALS -->
    <AddBillModal/>
    <DelBillModal/>
    <ModBillModal/>
    <ConsultBillModal/> 
  
  </div>
</template>
<script>
import axios from 'axios';
import config from '../util/config';
import AddBillModal from '../components/admin_modals/AddBillModal';
import ModBillModal from '../components/admin_modals/ModBillModal';
import DelBillModal from '../components/admin_modals/DelBillModal';
import ConsultBillModal from '../components/admin_modals/ConsultBillModal' 

export default {
  name: "Admin",
  components: {
    AddBillModal, ModBillModal, DelBillModal, ConsultBillModal,
  },
  data: function(){
    return {
      adminInfo: null,
      delSelectedBill: null,            
    }
  },
  methods: {
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
    },
    getAdminInfo: function(){
      return JSON.parse(localStorage.getItem('userData'))
    }
  },
  mounted: function(){
    this.$data.adminInfo = this.getAdminInfo()
    this.getBillsInfo()
  }  
}
</script>
<style scoped>
#root {
  margin: 2em;
  padding: 0 2em 0 2em;
}

.actions-management {
  margin: .5em 0 1em 0;
  width: 47.5%;
  padding: 2em;
  border-radius: 10px;
  box-shadow: 0px 0px 15px 0px rgba(0,0,0,.5);
  background-color:rgba(211, 250, 218, 0.5)
}

.title-icon {
  margin-right: .5em;
}


.row {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.actions {
  margin-top: 2em;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.action {
  width: 7.5em;
  height: 7.5em;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0px 0px 20px -5px rgba(0,0,0,.5);
  background-color: #fff;
  font-family: 'Oswald', sans-serif;
  transition: .25s;
}

.action:hover {
  cursor: pointer;
  transform: scale(1.15);
}



h2 {
  font-family: 'Oswald', sans-serif;
  font-size: 2.5em;
}

h3 {
  margin: 1em 0 0 0;
  font-size: 1.25em;
}

h4 {
  font-size: 1.75em;
}

hr{
  margin-bottom: 1.5em;
}

.green-text {
  color: rgb(122, 179, 132);
  margin-right: .5em;
}

@media(max-width: 850px) {
  .actions-management {
    width: 100%;
  }
}
</style>