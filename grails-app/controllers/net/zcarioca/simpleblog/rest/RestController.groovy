package net.zcarioca.simpleblog.rest

import grails.converters.JSON
import net.zcarioca.simpleblog.Page
import net.zcarioca.simpleblog.RestService

class RestController {
   RestService restService

   def main() {
      def content = [:]
      content['pages'] = restService.getPagePreviews()
      content['projects'] = restService.getProjectPreviews()
      content['posts'] = restService.getPostPreviews()
      return restService.getResponse(content)
   }
   
   def loadProject() {
      def content = ['project' : restService.getProjectById(params.id)]
      return restService.getResponse(content)
   }
   
   def loadPost() {
      def content = ['post' : restService.getPostById(params.id)]
      return restService.getResponse(content)
   }
   
   def loadPostsByCategory() {
      def content = ['post' : restService.getPostsByCategory(params.id)]
      return restService.getResponse(content)
   }
   
   def loadPostsByAuthor() {
      def content = ['posts' : restService.getPostsByAuthor(params.name)]
      return restService.getResponse(content)
   }
   
   def loadPostsByDate() {
      def content = ['posts' : restService.getPostsByDate(params.month, params.year)]
      return restService.getResponse(content)
   }
   
   def loadPage() {
      def content = ['page' : restService.getPageById(params.id)]
      return restService.getResponse(content)
   }
}
