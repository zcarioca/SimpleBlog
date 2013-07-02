angular.module('zcariocaServices', ['ngResource']).
factory('MainLookupService',['$resource', function($resource) {
   return $resource(baseUrl + '/main', {}, {
      query: {method: 'GET', params: {}, isArray: false}
   });
}]).
factory('PageLookupService', ['$resource', function($resource) {
   return $resource(baseUrl + '/page/:pageId', {}, {});
}]).
factory('ProjectLookupService', ['$resource', function($resource) {
   return $resource(baseUrl + '/project/:projId', {}, {});
}]).
factory('BlogLookupService', ['$resource', function($resource) {
   return $resource(baseUrl + '/post/:blogId', {}, {});
}]);
