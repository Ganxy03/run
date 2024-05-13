const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    port: 40505,
    client: {
      webSocketURL: 'ws://0.0.0.0:40505/ws',
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    },
    historyApiFallback: true,
    allowedHosts: "all",
    proxy: {
      '/api': {
        target: process.env.VUE_APP_BASE_URI,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
