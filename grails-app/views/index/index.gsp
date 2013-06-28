<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>Z Carioca - Software Developer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<g:if test="${isUser}">
<link href="${g.resource(dir: 'css', file: 'screen.css')}" rel="stylesheet" type="text/css" media="screen, projection"/>
<!--[if lt IE 9]>
<script src="${g.resource(dir: 'js', file: 'html5shiv.js')}"></script>
<script src="${g.resource(dir: 'js', file: 'json3.min.js')}"></script>
<script src="${g.resource(dir: 'js', file: 'angular.shiv.js')}"></script>
<script src="${g.resource(dir: 'js', file: 'respond.min.js')}"></script>
<![endif]-->
</g:if>
<script type="text/javascript">
   var baseUrl = '<g:resource dir="json"/>';
   var partials = {
      'blog': '<g:resource dir="partials" file="blog.html"/>',
      'blogRoll': '<g:resource dir="partials" file="blog-roll.html"/>',
      'page': '<g:resource dir="partials" file="page.html"/>',
      'project': '<g:resource dir="partials" file="proj.html"/>',
      'projectRoll': '<g:resource dir="partials" file="proj-roll.html"/>'
   };
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
</script>
<g:if test="${isUser}">
<r:resourceLink dir="css/highlight" file="ir_black.css"/>
<script type="text/javascript" src="${resource(dir: 'js', file: 'highlight.pack.js')}"></script>
<r:require modules="angular, app"/>
</g:if>
<r:layoutResources/>
</head>
<body id="ng-app" ng-app="zcarioca">
   <div class="container">
      <header class="main">
         <g:if test="${isUser }">
         <figure>
            <img src="${resource(dir: 'images', file: 'masthead_2.png') }" title="Z Carioca" alt="Z Carioca"/>
         </figure>
         </g:if>
         <h1 id="logo">Z Carioca</h1>
      </header>
      <!-- end header.main -->
      <a href="#body-content" class="nav-skip">Skip Navigation</a>
      <aside class="main">
         <g:if test="${pagePreviews}">
            <nav>
               <g:each in="${pagePreviews }" var="pagePreview">
                  <a href="#!/page/${pagePreview.slug}">${pagePreview.title}</a>
               </g:each>
               <a href="#!/proj">Projects</a>
               <a href="#!/blog">Blog</a>
            </nav>
         </g:if>
         <g:else>
            <nav ng-controller="MainMenuController">
               <a ng-repeat="page in pages" href="{{page.link}}" ng-click="setActive(page.slug)" ng-class="page.state">
                  {{page.title}}
               </a> 
            </nav>
         </g:else>
      </aside>
      <!-- end aside.main -->
      <section id="body-content" class="content" ng-view>
         <g:if test="${isBot}">
            <article>
               <h1>${title}</h1>
               ${body }
            </article>
         </g:if>
      </section>
      <footer>
         <g:if test="${isUser}">
         <figure>
            <img src="${resource(dir: 'images', file:'footer.png') }"/>
         </figure>
         <div class="login"><g:link controller="admin">login</g:link></div>
         </g:if>
         <div class="copy">&copy; 2013 - ZCarioca.net.  All rights reserved</div>
      </footer>
   </div>
</body>
</html>
