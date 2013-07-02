package net.zcarioca.simpleblog

class SimpleBlogTagLib {
   static namespace = "simpleBlog"
   
   /**
    * Renders the menu items as javascript.
    * 
    * @attr menuItems REQUIRED The list of menu items.
    */
   def menuJS = { attrs ->
      out << '['
      
      def index = 0
      def lastIndex = attrs.menuItems.size() - 1
      attrs.menuItems.each { menuItem ->
         out << "{title:'${menuItem.title}',type:'page',id:'${menuItem.id}',slug:'${menuItem.slug}',link:'/page/${menuItem.slug}'}"
         if (index != lastIndex) {
            out << ','
         }
         index++
      }
      
      out << ']'
   }
   
   /**
    * Renders the list of static files
    */
   def staticFiles = { attrs ->
      out << """{
      'blog':'${g.resource(dir:'partials', file:'blog.html')}',
      'blogRoll':'${g.resource(dir:'partials', file:'blog-roll.html')}',
      'page':'${g.resource(dir:'partials', file:'page.html')}',
      'project':'${g.resource(dir:'partials', file:'proj.html')}',
      'projectRoll':'${g.resource(dir:'partials', file:'proj-roll.html')}'
   }"""
   }
   
   /**
    * Renders the google analytics code
    */
   def googleAnalytics = { attrs ->
      out << '''<script type="text/javascript">
    var _gaq = _gaq || [];
    _gaq.push([ '_setAccount', 'UA-39324990-1' ]);
    _gaq.push([ '_setDomainName', 'zcarioca.net' ]);
    _gaq.push([ '_setAllowLinker', true ]);
    _gaq.push([ '_trackPageview' ]);
    (function() {
       var ga = document.createElement('script');
       ga.type = 'text/javascript';
       ga.async = true;
       ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
       var s = document.getElementsByTagName('script')[0];
       s.parentNode.insertBefore(ga, s);
    })();
</script>'''
   }
}
