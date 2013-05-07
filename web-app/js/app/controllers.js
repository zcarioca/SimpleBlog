var MainMenuController = [ '$scope', 'DataLookupService', 'MainLookupService', function($scope, DataLookupService, MainLookupService) {
   $scope.pages = [];
   $scope.loading = true;
   MainLookupService.query(function(data) {
      var blog = {
         title: 'Blog',
         type: 'blog',
         id: 'blog',
         slug: 'blog',
         link: '#/blog'
      }, proj = {
         title: 'Projects',
         type: 'proj',
         id: 'proj',
         slug: 'proj',
         link: '#/proj'
      };
      $scope.loading = false;
      angular.forEach(data.pages, function(page) {
         page.link = '#/page/' + page.slug;
         if (page.id === $scope.activePage) {
            page.state = 'active';
         }
         DataLookupService.addPage(page);
      });
      
      if (blog.id === $scope.activePage) {
         blog.state = 'active';
      }
      DataLookupService.addPage(proj);
      DataLookupService.addPage(blog);
      $scope.pages = DataLookupService.getPages();
      if (!DataLookupService.hasActivePage()) {
         DataLookupService.activatePage($scope.pages[0].slug);
      }

      // do posts
      angular.forEach(data.projects, function(proj) {
         DataLookupService.addProject(proj);
      });
      angular.forEach(data.posts, function(post) {
         DataLookupService.addPost(post);
      });
   });
   $scope.setActive = function(page) {
      DataLookupService.activatePage(page);
   };
}];

var PageContentController = [ '$scope', 'DataLookupService', 'PageLookupService', '$routeParams', function($scope, DataLookupService, PageLookupService, $routeParams) {
   $scope.loading = true;
   DataLookupService.getPageBySlug($routeParams.pageName, function(page) {
      PageLookupService.get({pageId: page.id}, function(data) {
         $scope.loading = false;
         $scope.page = data.page;
         DataLookupService.activatePage(data.page.slug);
      });
   });
}];

var ProjRollController = [ '$scope', 'DataLookupService', function($scope, DataLookupService) {
   $scope.posts = DataLookupService.getProjects();
   $scope.loading = false;
   $scope.filterQuery = function(post) {
      var query = null;
      if (!$scope.query) {
         return true;
      }
      query = angular.lowercase($scope.query);
      return angular.lowercase(post.title).indexOf(query) !== -1 || angular.lowercase(post.content).indexOf(query) !== -1;
   };
   DataLookupService.activatePage('proj');
}];

var ProjContentController = [ '$scope', 'DataLookupService', 'ProjectLookupService', '$routeParams', function($scope, DataLookupService, ProjectLookupService, $routeParams) {
   $scope.loading = true;
   DataLookupService.getProjectBySlug($routeParams.projName, function(post) {
      ProjectLookupService.get({projId: post.id}, function(data) {
         $scope.loading = false;
         $scope.page = data.project;
         $scope.page.showNext = false;
         $scope.page.showPrev = false;
         DataLookupService.activatePage('proj');
      });
   });
}];

var BlogRollController = [ '$scope', 'DataLookupService', function($scope, DataLookupService) {
   $scope.posts = DataLookupService.getPosts();
   $scope.loading = false;
   $scope.filterQuery = function(post) {
      var query = null;
      if (!$scope.query) {
         return true;
      }
      query = angular.lowercase($scope.query);
      return angular.lowercase(post.title).indexOf(query) !== -1 || angular.lowercase(post.content).indexOf(query) !== -1;
   };
   DataLookupService.activatePage('blog');
}];

var BlogContentController = [ '$scope', 'DataLookupService', 'BlogLookupService', '$routeParams', function($scope, DataLookupService, BlogLookupService, $routeParams) {
   $scope.loading = true;
   DataLookupService.getPostBySlug($routeParams.blogName, function(post) {
      BlogLookupService.get({blogId: post.id}, function(data) {
         $scope.loading = false;
         $scope.page = data.post;
         $scope.page.next = post.next;
         $scope.page.prev = post.prev;
         $scope.page.showNext = post.next === null ? false : true;
         $scope.page.showPrev = post.prev === null ? false : true;
         DataLookupService.activatePage('blog');
      });
   });
}];
