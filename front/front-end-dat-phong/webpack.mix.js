const mix = require('laravel-mix');

/*
 |--------------------------------------------------------------------------
 | Mix Asset Management
 |--------------------------------------------------------------------------
 |
 | Mix provides a clean, fluent API for defining some Webpack build steps
 | for your Laravel application. By default, we are compiling the Sass
 | file for the application as well as bundling up all the JS files.
 |
 */
mix
    .js('resources/js/admin-list-class.js', 'public/js/admin-list-class.js')
    .js('resources/js/admin-list-class-rooms.js', 'public/js/admin-list-class-rooms.js')
    .js('resources/js/admin-list-student.js', 'public/js/admin-list-student.js')
    .js('resources/js/admin-list-user.js', 'public/js/admin-list-user.js')
    .js('resources/js/user-list-class-rooms.js', 'public/js/user-list-class-rooms.js')
    .js('resources/js/user-my-request.js', 'public/js/user-my-request.js')
    .options({
        processCssUrls: false
    })
