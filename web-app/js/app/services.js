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
}]).
factory('DataLookupService',[ '$location', function($location) {
   return {
      activePage : null,
      pages : [],
      posts: [],
      projects: [],
      callbacks : {},
      
      getItemBySlug : function(items, slug, callback) {
         var i;
         for (i = 0; i < items.length; i+=1) {
            if (items[i].slug === slug) {
               if (callback) { callback(items[i]); }
               return items[i];
            }
         }
         if (callback) { this.callbacks[slug] = callback; }
         return null;
      },
      runCallback : function(item) {
         var callback;
         if (this.callbacks[item.slug]) {
            callback = this.callbacks[item.slug];
            delete this.callbacks[item.slug];
            callback(item);
         }
      },
      addPage : function(page) {
         if (page.slug === this.activePage) {
            page.state = 'active';
         } else {
            page.state = null;
         }
         this.pages.push(page);
         this.runCallback(page);
      },
      gotoPage : function(page) {
         if (page === 'blog') {
            $location.path('/blog');
         } else {
            $location.path('/page/'+page);
         }
      },
      hasActivePage : function() {
         return typeof(this.activePage) !== 'undefined' && this.activePage !== null;
      },
      activatePage : function(slug) {
         this.activePage = slug;
         angular.forEach(this.pages, function(page) {
            if (page.slug === slug) {
               page.state = 'active';
            } else { 
               page.state = null;
            }
         });
         if ($location.$$path === '') {
            this.gotoPage(slug);
         }
      },
      getPages : function() {
         return this.pages;
      },
      getPageBySlug : function(slug, callback) {
         return this.getItemBySlug(this.pages, slug, callback);
      },
      isProject : function(post) {
         var i;
         for (i = 0; i < post.categories.length; i+=1) {
            if (post.categories[i].slug === 'projects') {
               return true;
            }
         }
         return false;
      },
      addPost : function(post) {
         post.prev = null;
         post.next = null;
         var next = this.posts.length === 0 ? null : this.posts[this.posts.length - 1];
         if (next) {
            next.prev = {id:post.id, slug:post.slug};
            post.next = {id:next.id, slug:next.slug};
         }
         post.link = "#!/blog/" + post.slug;
         this.posts.push(post);
         this.runCallback(post);
      },
      getPosts : function() {
         return this.posts;
      },
      getPostBySlug : function(slug, callback) {
         return this.getItemBySlug(this.posts, slug, callback);
      },
      addProject : function(project) {
         project.prev = null;
         project.next = null;
         var next = this.projects.length === 0 ? null : this.projects[this.projects.length - 1];
         if (next) {
            next.prev = {id:project.id, slug:project.slug};
            project.next = {id:next.id, slug:next.slug};
         }
         project.link = '#!/proj/' + project.slug;
         this.projects.push(project);
         this.runCallback(project);
      },
      getProjects : function() {
         return this.projects;
      },
      getProjectBySlug : function(slug, callback) {
         return this.getItemBySlug(this.projects, slug, callback);
      }
   };
}]);
