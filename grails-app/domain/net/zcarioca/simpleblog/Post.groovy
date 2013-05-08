package net.zcarioca.simpleblog

import java.util.Date

class Post {

   String title
   String body

   boolean published = false
   Date publishedDate = new Date()

   User author
   
   static hasMany = [ tags : Tag, categories: Category ]

   static constraints = {
      author nullable: false
      title blank: false, unique: true, maxSize: 150
      body nullable: true, maxSize: 100000

      published nullable: false
      publishedDate nullable: false
   }

   static mapping = {
      published index: 'IDX_post_published'
      body type: 'text'
   }

   String toString() {
      return "Post: ${title}"
   }
}