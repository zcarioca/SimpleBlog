package net.zcarioca.simpleblog

import com.petebevin.markdown.MarkdownProcessor

class SynopsisService {
   static transactional = false
   
   public String makeHTML(String content) {
      def matcher = content =~ /```\w+\s+/
      
//      println "============================================="
//      println "OLD CONTENT: ${content}"
      while (matcher.find()) {
         def item = matcher.group()
         def lang = item.trim()[3..-1]
         
//         println "Search: ${item}"
//         println "Lang: ${lang}"

         def idx = content.indexOf(item)
         content = content.replaceFirst(item, "<pre><code class=\"${lang}\">")
         content = content.replaceFirst("\\s+```", "</code></pre>")
      }
      content = new MarkdownProcessor().markdown(content)
//      println "============================================="
//      println "============================================="
//      println "NEW CONTENT: ${content}"
//      println "============================================="
      return content
   }

   public String makeSynopsis(String content) {
      StringBuilder sb = new StringBuilder(content)
      if (content.length() > 250) {
         sb.setLength(250)
      }
      
      sb = new StringBuilder(sb.replaceAll("\\*|\\<|\\>|_|\\s", " "))
      
      char space = ' '
      while (sb.charAt(sb.size() - 1) != space) {
         sb.setLength(sb.size() - 1)
      }
      return "<p>${sb.toString()}[...]</p>"
   }
}
