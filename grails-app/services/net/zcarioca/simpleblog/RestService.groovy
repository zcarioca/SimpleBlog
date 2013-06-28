package net.zcarioca.simpleblog

import java.text.SimpleDateFormat

import com.petebevin.markdown.MarkdownProcessor
import org.apache.commons.lang.WordUtils
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib


class RestService {
   SynopsisService synopsisService
   def grailsApplication
   def g = new ApplicationTagLib()
   
   def getResponse(content) {
      def resp = ['header' : getHeader()]
      
      if (content) {
         content.each { name, body ->
            resp.put(name, body)
         }
      }
      
      resp['sidebar'] = [
         'archives':getArchives(),
         'categories' : getCategories()
      ]
      return resp
   }
   
   def getArchives = {
      def dates = []
      def sdf = new SimpleDateFormat('MMMM yyyy')
      
      Set<String> dateSet = new HashSet<String>()
      Post.findAllByPublished(true,['sort':'publishedDate','order':'desc']).each {
         def dateString = sdf.format(it.publishedDate)
         Calendar cal = Calendar.getInstance()
         cal.setTime(it.publishedDate)
         
         if (!dateSet.contains(dateString)) {
            dateSet.add(dateString)
            def year = cal.getAt(Calendar.YEAR)
            def month = cal.getAt(Calendar.MONTH) + 1
            dates << [
               'month': month,
               'year': year,
               'name': dateString,
               'url': "${g.resource(dir:'/')}json/post/archive/${year}/${month}"
            ]
         }
      }
      
      return dates
   }
   
   def getCategories = {
      def categories = []
      Category.list(['sort':'name']).each {
         categories << [
            'id' : it.id,
            'name' : it.name,
            'url' : "${g.resource(dir:'/')}json/post/category/${it.id}"
         ]
      }
      return categories
   }
   
   def getHeader() {
      def header = grailsApplication.config.simpleblog.header
      header['forms'] = [:]
      header['forms'].put('admin', [
         'method':'get',
         'parameters': [:],
         'url': g.createLink(controller:'admin')
      ])
      return header
   }
   
   def getProjectById(projId) {
      return projBlock(Project.get(projId))
   }
   
   def getPostById(postId) {
      return postBlock(Post.get(postId))
   }
   
   def getPageById(pageId) {
      return pageBlock(Page.get(pageId))
   }
   
   def getPostsByAuthor(username) {
      User user = User.findByUsername(username)
      
      return Post.findAllByPublishedAndAuthor(true, user, ['sort': 'publishedDate', 'order':'desc']).collect(postPreviewBlock)
   }
   
   def getPostsByDate(month, year) {
      SimpleDateFormat formatter = new SimpleDateFormat("Myyyy")
      Calendar start = Calendar.getInstance()
      start.time = formatter.parse("${month}${year}")
      
      Calendar end = Calendar.getInstance()
      end.time = start.time
      
      end.set(Calendar.DAY_OF_MONTH, 31)
      while (end.get(Calendar.MONTH) != start.get(Calendar.MONTH)) {
         end.add(Calendar.DAY_OF_MONTH, -1)
      }
      
      def postList = Post.withCriteria {
         between("publishedDate", start.time, end.time)
         order("publishedDate", "desc")
      }
      return postList.collect(postPreviewBlock)
   }
   
   def getPostsByCategory(catId) {
      Category cat = Category.get(catId)
      def postList = Post.withCriteria {
         categories {
            idEq Long.parseLong(catId)
         }
         order 'publishedDate', 'desc'
      }
      return postList.collect(postPreviewBlock)
   }
   
   def getPostPreviews() {
      return Post.findAllByPublished(true, ['sort': 'publishedDate', 'order': 'desc']).collect(postPreviewBlock)
   }
   
   def getProjectPreviews() {
      return Project.findAllByPublished(true, ['sort':'title']).collect(projectPreviewBlock)
   }
   
   def getPagePreviews() {
      return Page.findAllByPublished(true,['sort':'menuOrder','order':'asc']).collect(pagePreviewBlock)
   }
   
   private def postPreviewBlock = { post ->
      SimpleDateFormat sdf = newSDF()
      return [
         'id':post.id,
         'version': post.version,
         'title': post.title,
         'slug' : post.slug ?: toSlug(post.title),
         'author' : [
            'name':post.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${post.author.username}"
         ],
         'date': sdf.format(post.publishedDate),
         'url' : "${g.resource(dir: '/')}json/post/${post.id}",
         'content': synopsisService.makeSynopsis(post.body)
      ]
   }
   
   private def projectPreviewBlock = { proj ->
      SimpleDateFormat sdf = newSDF()
      return [
         'id':proj.id,
         'version': proj.version,
         'title': proj.title,
         'slug' : proj.slug ?: toSlug(proj.title),
         'author' : [
            'name':proj.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${proj.author.username}"
         ],
         'date': sdf.format(proj.publishedDate),
         'url' : "${g.resource(dir: '/')}json/project/${proj.id}",
         'content': synopsisService.makeSynopsis(proj.body)
      ]
   }
   
   private def pagePreviewBlock = { page ->
      SimpleDateFormat sdf = newSDF()
      return [
         'title': page.title,
         'slug' : page.slug ?: toSlug(page.title),
         'author': [
            'name': page.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${page.author.username}"
         ],
         'date': sdf.format(page.publishedDate),
         'id': page.id,
         'version':page.version,
         'menu_order' : page.menuOrder,
         'url': "${g.resource(dir: '/')}json/page/${page.id}"
      ]
   }
   
   private def projBlock = { proj ->
      SimpleDateFormat sdf = newSDF()
      return [
         'title' : proj.title,
         'slug' : proj.slug ?: toSlug(proj.title),
         'id' : proj.id,
         'date' : sdf.format(proj.publishedDate),
         'version': proj.version,
         'author': [
            'name': proj.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${proj.author.username}"
         ],
         'content' : toHTML(proj.body)
      ]
   }
   
   private def postBlock = { post ->
      SimpleDateFormat sdf = newSDF()
      def content = [
         'title' : post.title,
         'slug' : post.slug ?: toSlug(post.title),
         'id' : post.id,
         'date' : sdf.format(post.publishedDate),
         'version': post.version,
         'author': [
            'name': post.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${post.author.username}"
         ],
         'content' : toHTML(post.body),
         'categories' : []
      ]
      
      post.categories.each { 
         content['categories'] << [
            'id' : it.id,
            'name' : it.name,
            'url' : "${g.resource(dir:'/')}json/post/category/${it.id}"
         ]
      }
      
      return content
   }
   
   private def pageBlock = { page ->
      SimpleDateFormat sdf = newSDF()
      return [
         'title':page.title,
         'slug' : page.slug ?: toSlug(page.title),
         'id': page.id,
         'date': sdf.format(page.publishedDate),
         'version': page.version,
         'menu_order': page.menuOrder,
         'author': [
            'name': page.author.username,
            'url': "${g.resource(dir: '/')}json/post/author/${page.author.username}"
         ],
         'content' : toHTML(page.body)
      ]
   }
   
   def toHTML(markdown) {
      return synopsisService.makeHTML(markdown)
   }
   
   def toSlug(title) {
      String slug = title.toLowerCase()
      slug = slug.replaceAll("[^a-z0-9]", '-')
      slug = slug.replaceAll(/\-+/, '-')
      slug = slug.replaceAll(/(^\-)|(\-$)/, '')
      return slug
   }
   
   private SimpleDateFormat newSDF() {
      return new SimpleDateFormat("MMMM d, yyyy")
   }
}
