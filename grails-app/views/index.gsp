<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Z Carioca - Software Developer</title>
<!--[if lt IE 9]>
<script src="${g.resource(dir: 'js', file: 'html5shiv.js')}"></script>
<script src="${g.resource(dir: 'js', file: 'json3.min.js')}"></script>
<script src="${g.resource(dir: 'js', file: 'angular.shiv.js')}"></script>
<![endif]-->
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
<style type="text/css">
@font-face {
  font-family: 'Black Ops One';
  font-style: normal;
  font-weight: 400;
  src: url('${resource(dir:"fonts", file:"black_ops_one.woff")}') format('woff');
}
</style>
<r:resourceLink dir="css" file="reset.css"/>
<r:resourceLink dir="css" file="app-main.css"/>
<!--[if lt IE 9]>
<r:resourceLink dir="css" file="app-main-ie.css"/>
<![endif]-->
<r:resourceLink dir="css/highlight" file="ir_black.css"/>
<script type="text/javascript" src="${resource(dir: 'js', file: 'highlight.pack.js')}"></script>
<r:require modules="angular, app"/>
<r:layoutResources/>
</head>
<body id="ng-app" ng-app="zcarioca">
   <div id="page">
      <div id="header">
         <h1>Z Carioca</h1>
      </div>
      <a href="#body-content" class="nav-skip">Skip Navigation</a>
      <div id="body">
         <div id="main-menu" ng-controller="MainMenuController">
            <ul>
               <li ng-repeat="page in pages" ng-class="page.state">
                  <a href="{{page.link}}" ng-class="page.state" ng-click="setActive(page.slug)">{{page.title}}</a>
               <li>
            </ul>
         </div>
         <div id="body-content" ng-view></div>
      </div>
      <div id="footer">
         <div class="login"><g:link controller="admin">login</g:link></div>
         <div class="copy">&copy; 2013 - ZCarioca.net.  All rights reserved</div>
      </div>
   </div>
</body>
</html>
