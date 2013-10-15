package net.zcarioca.simpleblog

import java.util.regex.Matcher;

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
      return wrapTOC(output.toString())
   }

   public String makeSynopsis(String content) {
      StringBuilder sb = new StringBuilder(content.replaceAll(/\{:toc\}/, ""))
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

   private String wrapTOC(String content) {
      TableOfContents toc = new TableOfContents()
      if (content.contains("{:toc}")) {
         log.debug "Contains a table of contents"

         StringBuffer newContent = new StringBuffer()

         Matcher matcher = content =~ /<(h|H)\d>(?:(?!<\/h).)*<\/(h|H)\d>/

         while (matcher.find()) {
            def replacement = new StringBuffer(matcher.group())
            int level = Integer.parseInt(replacement.substring(2,3))
            def title = replacement.substring(4, replacement.length() - 5)
            def slug = toSlug(title)
            
            replacement.insert(3, " id=\"${slug}\"")
            
            matcher.appendReplacement(newContent, replacement.toString())
            toc.addItem(level, slug, title)
         }
         matcher.appendTail(newContent)

         content = newContent.replaceAll(/(<p>)?\{:toc\}(<\/p>)?/, toc.toHTML())
      }
      return content
   }

   private String toSlug(title) {
      String slug = title.toLowerCase()
      slug = slug.replaceAll("[^a-z0-9]", '-')
      slug = slug.replaceAll(/\-+/, '-')
      slug = slug.replaceAll(/(^\-)|(\-$)/, '')
      return slug
   }
}
