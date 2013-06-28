/* website module */
angular.module('zcarioca', ['zcariocaServices']).
   config(['$routeProvider','$locationProvider',function($routeProvider, $locationProvider) {
   $locationProvider.html5Mode(false).hashPrefix('!');
   $routeProvider.
      when('/page/:pageName', {templateUrl: partials.page, controller: PageContentController}).
      when('/blog/:blogName', {templateUrl: partials.blog, controller: BlogContentController}).
      when('/proj/:projName', {templateUrl: partials.project, controller: ProjContentController}).
      when('/blog', {templateUrl: partials.blogRoll, controller: BlogRollController}).
      when('/proj', {templateUrl: partials.projectRoll, controller: ProjRollController});
}]).
run(['$rootScope','$location', function($rootScope, $location) {
   $rootScope.$on('$viewContentLoaded', function(evt) {
      _gaq.push(['_trackPageview', $location.path()]);
   });
}]).
directive('hideon', function() {
   return function(scope, elements, attrs) {
      scope.$watch(attrs.hideon, function(value, oldValue) {
         angular.forEach(elements, function(element) {
            if (value === true) {
               element.style.display="block";
            } else {
               element.style.display="none";
            }
         });
      }, true);
   }
});