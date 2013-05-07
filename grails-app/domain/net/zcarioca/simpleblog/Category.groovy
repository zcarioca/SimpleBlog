package net.zcarioca.simpleblog

class Category {

   String name

   void setName(String name) {
      this.name = "${name ?: ''}".toUpperCase()
   }

   static belongsTo = Post
   static hasMany = [ posts : Post ]

   static constraints = {
      name blank: false, unique: true, maxSize: 100
   }

   String toString() {
      return "Category: ${name}"
   }
}
