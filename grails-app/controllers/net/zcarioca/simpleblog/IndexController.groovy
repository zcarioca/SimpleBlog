package net.zcarioca.simpleblog

import org.apache.commons.lang.StringUtils;

class IndexController {

   def restService

   def index() {
      def map = [ 
         isBot      : false,
         isTextOnly : false, 
         isUser     : true,
         menuItems  : restService.getPagePreviews()
      ]
      def userAgent = request.getHeader('User-Agent')?.toLowerCase()
      println "UserAgent: ${userAgent}"
      boolean textBrowser = userAgent?.contains('links') || userAgent?.contains('lynx')
      boolean google = userAgent?.contains('google')
      
      if (google) {
         def queryFragment = params['_escaped_fragment_']
         
         def queryData = fetchQueryFragmentData(queryFragment)
         
         map.isBot = true
         map.isUser = false
         
         queryData.each { key, value ->
            map[key] = value
         }
      } else if (textBrowser) {
         def queryFragment = params['page']
         
         def queryData = fetchQueryFragmentData(queryFragment)
         
         map.isTextOnly = true
         
         queryData.each { key, value ->
            map[key] = value
         }
      } 
      return map
   }
   
   private def fetchQueryFragmentData(query) {
      if (StringUtils.isBlank(query)) {
         def pages = Page.list(['sort':'menuOrder'])
         return [ type: 'page', title : pages[0].title, body : restService.toHTML(pages[0].body) ]
      }
      if (StringUtils.startsWith(query, "/page")) {
         def page = Page.findBySlug(query.substring(query.lastIndexOf('/') + 1))
         return [ type : 'page', title: page.title, body : restService.toHTML(page.body) ]
      }
      if (StringUtils.startsWith(query, "/proj")) {
         // is this the blog roll, or just the blog entry
         if (query.lastIndexOf('/') == 0) {
            // blog roll
            def projects = restService.getProjectPreviews()
            def map = [ type: 'blog-roll', title: 'Projects', body: null ]
            
            def body = new StringBuilder()
            projects.each { proj ->
               body << '<div>'
               body << "<a href=\"#!/proj/${proj.slug}\">${proj.title}</a>"
               body << proj.content
               body << '</div>'
            }
            map.body = body.toString()
            return map
         } else {
            def page = Project.findBySlug(query.substring(query.lastIndexOf('/') + 1))
            return [ type : 'page', title: page?.title, body : restService.toHTML(page?.body) ]
         }
      }
      if (StringUtils.startsWith(query, "/blog")) {
         // is this the blog roll, or just the blog entry
         if (query.lastIndexOf('/') == 0) {
            // blog roll
            def posts = restService.getPostPreviews()
            def map = [ type: 'blog-roll', title: 'Blog', body: null ]
            
            def body = new StringBuilder()
            posts.each { post ->
               body << '<div>'
               body << "<a href=\"#!/blog/${post.slug}\">${post.title}</a>"
               body << post.content
               body << '</div>'
            }
            map.body = body.toString()
            return map
         } else {
         def page = Post.findBySlug(query.substring(query.lastIndexOf('/') + 1))
            return [ type : 'page', title: page.title, body : restService.toHTML(page.body) ]
         }
      }
   }
}
