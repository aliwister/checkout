// Imports: Dependencies
const path = require('path');
const { CleanWebpackPlugin } = require('clean-webpack-plugin'); // installed via npm
const HtmlWebpackPlugin = require('html-webpack-plugin')
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const TerserPlugin = require('terser-webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');


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
        minimizer: [new TerserPlugin({ /* additional options here */ })],

        splitChunks: {
          chunks: 'all',
          minSize: 100000,
          maxSize: 200000,
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
          },
          {
            test: /\.s[ac]ss$/i,
            use: ['style-loader', 'css-loader','sass-loader'],
          },
          {
            test: /\.(png|jpe?g|gif)$/i,
            use: [
              {
                loader: 'file-loader',
              },
            ],
          },
        ]
      },
      resolve: {
        modules: ['node_modules', 'src'],
        // ...
      },
      mode: 'production',

      // Plugins
      plugins: [new HtmlWebpackPlugin({
        template: '!!html-loader!../src/main/resources/templates/checkout.template.html',
        filename: '../templates/checkout.html'
      }),
        new CompressionPlugin({
          test: /\.js(\?.*)?$/i,
          algorithm: "gzip",
        }),
        new BundleAnalyzerPlugin(),

      ]
    }
  }

  return {
    entry: './src/index.js',
    // Output
    output: {
      path: path.resolve(__dirname, './'),
      filename: 'app.js',
    },
    resolve: {
      modules: ['node_modules', 'src'],
      // ...
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
        },
        {
          test: /\.s[ac]ss$/i,
          use: ['style-loader', 'css-loader','sass-loader'],
        },
        {
          test: /\.(png|jpe?g|gif)$/i,
          use: [
            {
              loader: 'file-loader',
            },
          ],
        },
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
