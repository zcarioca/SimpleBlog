var CodeBlockParser = {
   
   getLines : function(codeTag) {
      var text = codeTag.innerHTML;
      text = text.replace(/^(\s+)|(\s+)$/, '');
      return text.split('\n');
   },
   
   setupLines : function() {
      setTimeout(function() {
         var preTags = document.getElementsByTagName('pre');
         angular.forEach(preTags, function(preTag) {
            var codeTags = preTag.getElementsByTagName('code');
            angular.forEach(codeTags, function(codeTag) {
               if (!codeTag.hasAttribute('class') || codeTag.getAttribute('class').indexOf('pretty-print') === -1) {
                  hljs.highlightBlock(codeTag, null, false);
                  codeTag.setAttribute('class', codeTag.getAttribute('class') + ' pretty-print');
               }
            });
         });
      });
   }
};
var MainMenuController = [ '$scope', '$location', function($scope, $location) {
   $scope.pages = [];
   $scope.loading = true;
   $scope.activePage = null;
   $scope.prefix = '#!';
   
   $scope.loadMenu = function() {
      var location = $location.path();
      menu.push({
         title: 'Blog',
         type: 'blog',
         id: 'blog',
         slug: 'blog',
         link: '/blog'
      }); 
      menu.push({
         title: 'Projects',
         type: 'proj',
         id: 'proj',
         slug: 'proj',
         link: '/proj'
      });
      $scope.loading = false;
      $scope.pages = menu;
      
      if ($scope.activePage === null) {
         if (location && location !== '') {
            if (location.indexOf('/page') > -1) {
               location = location.substring(location.lastIndexOf('/') + 1);
            } else if (location.indexOf('/blog') > -1) {
               location = 'blog';
            } else {
               location = 'proj';
            }
            $scope.setActive(location);
         } else {
            $scope.setActive(menu[0].slug);
            $location.path(menu[0].link);
         }
      }
   };
   
   $scope.setActive = function(slug) {
      angular.forEach($scope.pages, function(page) {
         if (page.slug === slug) {
            page.state = 'active';
            $scope.activePage = page.link;
         } else {
            page.state = 'inactive';
         }
      })
   };
   
   $scope.loadMenu();
}];

var PageContentController = [ '$scope', 'PageLookupService', '$routeParams', function($scope, PageLookupService, $routeParams) {
   $scope.loading = true;
   PageLookupService.get({pageId: $routeParams.pageName}, function(data) {
      $scope.loading = false;
      $scope.page = data.page;
   });
}];

var ProjRollController = [ '$scope', 'MainLookupService', function($scope, MainLookupService) {
   $scope.posts = [];
   $scope.loading = false;
   $scope.filterQuery = function(post) {
      var query = null;
      if (!$scope.query) {
         return true;
      }
      query = angular.lowercase($scope.query);
      return angular.lowercase(post.title).indexOf(query) !== -1 || angular.lowercase(post.content).indexOf(query) !== -1;
   };
   if ($scope.posts.length === 0) {
      $scope.loading = true;
      MainLookupService.get(function(data) {
         $scope.posts = data.projects;
         angular.forEach($scope.posts, function(post) {
            post.link = '#!/proj/' + post.slug;
         }); 
         $scope.loading = false;
      });
   }
}];

var ProjContentController = [ '$scope', 'ProjectLookupService', '$routeParams', function($scope, ProjectLookupService, $routeParams) {
   $scope.loading = true;
   ProjectLookupService.get({projId: $routeParams.projName}, function(data) {
      $scope.loading = false;
      $scope.page = data.project;
      $scope.page.showNext = false;
      $scope.page.showPrev = false;
      CodeBlockParser.setupLines();
   });
}];

var BlogRollController = [ '$scope', 'MainLookupService', function($scope, MainLookupService) {
   $scope.posts = [];
   $scope.loading = false;
   $scope.filterQuery = function(post) {
      var query = null;
      if (!$scope.query) {
         return true;
      }
      query = angular.lowercase($scope.query);
      return angular.lowercase(post.title).indexOf(query) !== -1 || angular.lowercase(post.content).indexOf(query) !== -1;
   };
   if ($scope.posts.length === 0) {
      $scope.loading = true;
      MainLookupService.get(function(data) {
         $scope.posts = data.posts;
         angular.forEach($scope.posts, function(post) {
            post.link = '#!/blog/' + post.slug;
         }); 
         $scope.loading = false;
      });
   }
}];

var BlogContentController = [ '$scope', 'BlogLookupService', '$routeParams', function($scope, BlogLookupService, $routeParams) {
   $scope.loading = true;
   BlogLookupService.get({blogId: $routeParams.blogName}, function(data) {
      $scope.loading = false;
      $scope.page = data.post;
      $scope.page.next = data.post.next;
      $scope.page.prev = data.post.prev;
      $scope.page.showNext = typeof(data.post.next) === 'undefined' || data.post.next === null ? false : true;
      $scope.page.showPrev = typeof(data.post.prev) === 'undefined' || data.post.prev === null ? false : true;
      CodeBlockParser.setupLines();
   });
}];
