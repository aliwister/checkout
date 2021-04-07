// Imports: Dependencies
const path = require('path');
const { CleanWebpackPlugin } = require('clean-webpack-plugin'); // installed via npm
const HtmlWebpackPlugin = require('html-webpack-plugin')

// Webpack Configuration
// Exports
module.exports = env => {
  
  // Entry
  if(env.production) {
    return {
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
		  minSize: 30000,
		  maxSize: 50000,
        },
      },
      // Loaders
      module: {
        rules: [
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
      })]
    }
  }

  return {
    entry: './src/index.js',
    // Output
    output: {
      path: path.resolve(__dirname, './'),
      filename: 'app.js',
    },
    // Loaders
    module: {
      rules: [
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
              target: 'http://127.0.0.1:8081/graphql',
              secure: false,
              prependPath: false
            }
      },
    }
  }
};
