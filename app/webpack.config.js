// Imports: Dependencies
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin')

// Webpack Configuration
const config = {
  
  // Entry
  entry: './src/index.js',
  // Output
  output: {
    publicPath: '/',
    path: path.resolve(__dirname, '../src/main/resources/static'),
    filename: 'app.[chunkhash].js',
  },
  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },
  // Loaders
  module: {
    rules : [
      // JavaScript/JSX Files
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: ['babel-loader'],
      },
      {
        test: /\.svg$/,
        use: [
          {
            loader: 'svg-url-loader',
            options: {
              limit: 10000,
            },
          },
        ],
      },
      // CSS Files
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      }
    ]
  },
  // Plugins
  plugins: [new HtmlWebpackPlugin({
    template: '!!html-loader!../src/main/resources/templates/checkout.template.html',
    filename: '../templates/checkout.html'
  })],
  devServer: {
    port: 3003,
    contentBase: './',
    hot: true,
    proxy: {
      "/graphql":
          {
            target: 'http://localhost:8081/graphql',
            secure: false,
            prependPath: false
          }
    },
  }
};
// Exports
module.exports = config;