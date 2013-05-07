class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
      
      "/json/post/category/$id" (controller: 'rest', action: 'loadPostsByCategory')
      "/json/post/archive/$year/$month" (controller: 'rest', action: 'loadPostsByDate')
      "/json/post/author/$name" (controller: 'rest', action: 'loadPostsByAuthor')
      "/json/project/$id" (controller: 'rest', action: 'loadProject')
      "/json/post/$id" (controller: 'rest', action: 'loadPost')
      "/json/page/$id" (controller: 'rest', action: 'loadPage')
      "/json/$action" (controller: 'rest')

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
