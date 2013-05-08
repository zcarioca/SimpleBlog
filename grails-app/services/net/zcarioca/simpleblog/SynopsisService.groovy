package net.zcarioca.simpleblog

class SynopsisService {
   static transactional = false

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
