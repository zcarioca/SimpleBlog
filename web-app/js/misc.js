/*
 * A set of miscellaneous methods and classes
 */
window.onpopstate = function(event) {
   var state = event.state;
   if (state && state.position) {
      window.scrollTo(0, state.position);
   } else {
      setTimeout(function() {window.scrollTo(0,0)},100);
   }
}
function scrollToElement(id) {
   var element = document.getElementById(id),
       doc = document.documentElement,
       body = document.body,
       top = (doc && doc.scrollTop  || body && body.scrollTop  || 0);
       boundingBox = null,
       position = 0;
   
   if (element) {
      boundingBox = element.getBoundingClientRect();
      if (boundingBox) {
         position = top + boundingBox.top;
         window.scrollTo(0, position);
         
         if (history && history.pushState) {
            history.pushState({position: position}, '', location.hash);
         }
      }
   }
};