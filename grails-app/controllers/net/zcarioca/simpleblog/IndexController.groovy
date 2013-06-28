package net.zcarioca.simpleblog

class IndexController {

   def restService

   def index() {
      def map = [ isBot : false ]
      def userAgent = request.getHeader('User-Agent')
      if (userAgent?.toLowerCase()?.contains("google")) {
         def pagePreviews = restService.getPagePreviews()
         def queryFragment = params['_escaped_fragment_']
         
         map.isBot = true
         map.pagePreviews = pagePreviews
      }
      return map
   }
}
