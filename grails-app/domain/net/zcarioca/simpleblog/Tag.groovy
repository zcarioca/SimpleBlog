package net.zcarioca.simpleblog

class Tag {

   String name
   
   void setName(String name) {
      this.name = "${name ?: ''}".toLowerCase()
   }

   static belongsTo = Post
   static hasMany = [ posts : Post ]
   
   static constraints = {
      name blank: false, unique: true, maxSize: 100
   }

   String toString() {
      return "Tag: ${name}"
   }
}
