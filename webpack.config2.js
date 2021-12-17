var config = {
  mode: 'none',
  /*optimization: {
    runtimeChunk: true
 },*/
  optimization: { concatenateModules: false, providedExports: false, usedExports: false },
  resolve: {
    modules: [
	  "E:/MyIntellijIDEA/JSOCKET/src/jsMain/kotlin/js",
      "node_modules" 
    ]
  },
  plugins: [],
  module: {
    rules: []
  }
};

// entry
if (!config.entry) config.entry = [];
//config.entry.push("E:\\MyIntellijIDEA\\JSOCKET\\src\\jsMain\\kotlin\\js\\GetBrowserFinger.js");
config.entry.push("E:\\MyIntellijIDEA\\JSOCKET\\build\\js\\packages\\JSOCKET\\kotlin\\JSOCKET.js");
config.output = {
	library: 'avaclub',
    libraryTarget: 'var',
    path: "E:\\MyIntellijIDEA\\JSOCKET\\build\\distributions",
    filename: "JSOCKET.js"
};

// source maps
/*
config.module.rules.push({
        test: /\.js$/,
        use: ["kotlin-source-map-loader"],
        enforce: "pre"
});
config.devtool = 'eval-source-map';
*/

// Report progress to console
// noinspection JSUnnecessarySemicolon
;(function(config) {
    const webpack = require('webpack');
    const handler = (percentage, message, ...args) => {
        let p = percentage * 100;
        let msg = `${Math.trunc(p / 10)}${Math.trunc(p % 10)}% ${message} ${args.join(' ')}`;
        msg = msg.replace(new RegExp("E:\\MyIntellijIDEA\\JSOCKET\\build\\js", 'g'), '');;
        console.log(msg);
    };

    config.plugins.push(new webpack.ProgressPlugin(handler))
})(config);
module.exports = config
