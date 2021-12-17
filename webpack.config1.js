const path = require('path');

module.exports = {
 /*optimization: {
    runtimeChunk: true
 },*/
  mode: 'none',
  optimization: { concatenateModules: false, providedExports: false, usedExports: false },
  entry: [
    'E:\\MyIntellijIDEA\\JSOCKET\\build\\js\\packages\\JSOCKET\\kotlin\\JSOCKET.js',
  ],
  output: {
	library: 'jsocket',
    libraryTarget: 'umd',
	libraryExport: 'default',
    path: 'E:\\MyIntellijIDEA\\JSOCKET\\build\\distributions',
    filename: 'index.js'
  },
  /*devtool: "source-map",*/
  module: {
    rules: [{
        test: /\.js$/,
        include: path.resolve('E:\\MyIntellijIDEA\\JSOCKET\\build\\js'),
        use: {
          loader: 'babel-loader',
          options: {
            presets: 'env'
          }
        }
      },
    ]
  },
  plugins: [
  ]
};