package net.zcarioca.simpleblog

import com.petebevin.markdown.MarkdownProcessor

class SynopsisService {
   static transactional = false
   
   public String makeHTML(String content) {
      def matcher = content =~ /```\w+\s+/
      
      while (matcher.find()) {
         def item = matcher.group()
         def lang = item.trim()[3..-1]

         def idx = content.indexOf(item)
         content = content.replaceFirst(item, "<pre><code class=\"${lang}\">")
         content = content.replaceFirst("\\s+```", "</code></pre>")
      }
      return new MarkdownProcessor().markdown(content)
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
