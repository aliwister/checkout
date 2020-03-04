// Imports: Dependencies
const path = require('path');

// Webpack Configuration
const config = {
  
  // Entry
  entry: './src/index.js',
  // Output
  output: {
    path: path.resolve(__dirname, './'),
    filename: 'app.js',
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
  plugins: [],
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