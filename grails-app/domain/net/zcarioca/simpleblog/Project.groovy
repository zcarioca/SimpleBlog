package net.zcarioca.simpleblog

class Project {
   String title
   String slug
   String body

   boolean published = false
   Date publishedDate = new Date()
   
   User author

   static constraints = {
      author nullable: false
      title blank: false, unique: true, maxSize: 150
      slug blank: false, unique: true, maxSize: 150
      body nullable: true, maxSize: 30000

      published nullable: false
      publishedDate nullable: false
   }

   static mapping = {
      published index: 'IDX_project_published'
      body type: 'text'
   }
   
   String toString() {
      return "Project: ${title}"
   }
}
