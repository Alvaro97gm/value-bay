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
        modify: '/bills/mod/',
        delete: '/bills/delete/'
      },
      Coin: {
        insert: '/coins/insert',
        get: '/coins/get/',
        getAll: '/coins/get/all',
        modify: '/coins/mod/',
        delete: '/coins/delete/'
      }
    },
    Auction: {
      insert: '/auctions/insert',
      get: '/auctions/get/',
      getAll: '/auctions/get/all',
      modify: '/auctions/mod/',
      delete: '/auctions/delete/'
    }
  }
}
