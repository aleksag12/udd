const fs = require('fs');

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 8070, // CHANGE YOUR PORT HERE!
    hotOnly: false,
    disableHostCheck: true
  }
}
