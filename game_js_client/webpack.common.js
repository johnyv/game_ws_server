const path = require('path')
const htmlPlugin = require('html-webpack-plugin')
const copyPlugin = require('copy-webpack-plugin')
// const imageminPlugin = require('imagemin-webpack-plugin').default

module.exports = {
    context: path.join(__dirname, 'src'),
    entry: ['./js/main.js'],
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'game.min.[hash:8].js',
    },
    target: 'web',
    plugins: [
        // new copyPlugin({
        //     patterns: [
        //         {from: 'assets', to: 'assets'}
        //     ]
        // }),

        // new imageminPlugin({
        //     test: /\.(jpe?g|png|gif|svg)$/i,
        //     pngquant: {
        //         verbose: true,
        //         quality: '80-90'
        //     }
        // }),

        new htmlPlugin({
            file: path.join(__dirname, 'dist', 'index.html'),
            template: "./index.html"
        })
    ],
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: [
                            ['@babel/preset-env', {
                                'corejs': '3',
                                'useBuiltIns': 'usage'
                            }]
                        ],
                        plugins: ['@babel/plugin-transform-runtime']
                    }
                }
            },
        ]
    }
};
