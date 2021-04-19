const {merge} = require('webpack-merge')
const common = require('./webpack.common')

module.exports = merge(common, {
    'mode': 'production',
    devtool: 'source-map',
    // module: {
    //     rules: [
    //         {
    //             test: /\.js$/,
    //             exclude: /node_modules/,
    //             use: {
    //                 loader: "babel-loader",
    //                 options: {
    //                     presets: [
    //                         ['@babel/preset-env', {
    //                             'corejs': '3',
    //                             'useBuiltIns': 'usage'
    //                         }]
    //                     ],
    //                     plugins: ['@babel/plugin-transform-runtime']
    //                 }
    //             }
    //         },
    //         {
    //             test: /\.(glsl|vs|fs|vert|frag)$/,
    //             exclude: /node_modules/,
    //             use: ['raw-loader', 'glslify-loader']
    //         }
    //     ]
    // }
});
