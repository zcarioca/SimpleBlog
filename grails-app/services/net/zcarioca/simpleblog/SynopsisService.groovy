package net.zcarioca.simpleblog

import org.apache.commons.lang.StringEscapeUtils;

import com.petebevin.markdown.MarkdownProcessor

class SynopsisService {
   static transactional = false
   
   public String makeHTML(String content) {
      MarkdownProcessor mkp = new MarkdownProcessor()
      def matcher = content =~ /```\w+\s+/
      int start = 0
      StringBuilder mkout = new StringBuilder()
      def preList = []
      
      while (matcher.find()) {
         def item = matcher.group()
         def lang = item.trim()[3..-1]
         
         int groupPoint = content.indexOf(item, start)
         mkout << content.substring(start, groupPoint)
         mkout << "{{INSERT POINT}}"
         
         int nextPoint = groupPoint + item.length()
         start = content.indexOf("```", nextPoint)
         
         def codeblock = content.substring(nextPoint, start)
         codeblock = StringEscapeUtils.escapeHtml(codeblock).replaceAll('\\$', '&#36;')
         preList << "<pre><code class=\"${lang}\">${codeblock}</code></pre>"
         start += 3
      }
      
      mkout << content.substring(start)
      
      def output = mkp.markdown(mkout.toString())
      
      preList.each { string ->
         def regex = /(<p>\s*)?\{\{INSERT POINT\}\}(\s*<\/p>)?/
         output = output.replaceFirst(regex, string)
      }
      return output.toString()
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
