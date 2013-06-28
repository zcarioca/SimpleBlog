package net.zcarioca.simpleblog

class Page {
   String title
   String slug
   String body

   Integer menuOrder = 0
   boolean published = false
   Date publishedDate = new Date()

   User author

   static constraints = {
      author nullable: false
      title blank: false, unique: true, maxSize: 150
      slug blank: false, unique: true, maxSize: 150
      body nullable: true, maxSize: 3000

      menuOrder nullable: true
      published nullable: false
      publishedDate nullable: false
   }

   static mapping = {
      published index: 'IDX_page_published'
      body type: 'text'
   }

   String toString() {
      return "Page: ${title}"
   }
}
