export default {
  serverURL: 'http://localhost:8081',
  APIEndpoints: {
    Security: {
      login:    '/login',
      signin:   '/signin'
    },
    Lot: {
      Bill: {
        insert: '/bills/insert',      
        get: '/bills/get/',           
        getAll: '/bills/get/all',
        getFree: '/bills/get/free',  
        modify: '/bills/mod/',
        delete: '/bills/delete/'
      },
      Coin: {
        insert: '/coins/insert',
        get: '/coins/get/',
        getFree: '/coins/get/free',
        getAll: '/coins/get/all',
        modify: '/coins/mod/',
        delete: '/coins/delete/'
      }
    },
    Auction: {
      insert: '/auctions/insert',
      get: '/auctions/get/',
      getAll: '/auctions/get/all',
      getNext: '/auctions/get/next',
      getActive: '/auctions/get/active',
      getClosed: '/auctions/get/closed',
      modify: '/auctions/mod/',
      delete: '/auctions/delete/'
    }
  }
}
