modules = {
    'application' {
        resource url:'js/application.js'
    }
    
    'maincss' {
       resource url: 'css/reset.css', bundle: 'bundle_maincss'
       resource url: 'css/app-main.css', bundle: 'bundle_maincss'
    }
    
    'angular' {
       resource url: 'js/angular.min.js', disposition: 'head'
       resource url: 'js/angular-resource.min.js', disposition: 'head'
    }
    
    'app' {
       resource url: 'css/screen.css', disposition: 'head'
       resource url: 'js/app/modules.js', disposition: 'head'
       resource url: 'js/app/services.js', disposition: 'head'
       resource url: 'js/app/controllers.js', disposition: 'head'
    }
}