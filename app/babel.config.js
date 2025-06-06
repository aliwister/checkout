module.exports = function(api) {
    api.cache(true);
    return {
        presets: [

            require("@babel/preset-env"),
            require("@babel/preset-react")

        ],

        plugins: [
            ["@babel/plugin-transform-runtime"],
            [
                'babel-plugin-import',
                {
                    'libraryName': '@material-ui/core',
                    // Use "'libraryDirectory': ''," if your bundler does not support ES modules
                    'libraryDirectory': 'esm',
                    'camel2DashComponentName': false
                },
                'core'
            ],
            [
                'babel-plugin-import',
                {
                    'libraryName': '@material-ui/icons',
                    // Use "'libraryDirectory': ''," if your bundler does not support ES modules
                    'libraryDirectory': 'esm',
                    'camel2DashComponentName': false
                },
                'icons'
            ]
        ]
    }
};