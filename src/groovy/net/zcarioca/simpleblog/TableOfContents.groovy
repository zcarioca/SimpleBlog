/*
 * Project: SimpleBlog
 * 
 * Copyright (C) 2013 zcarioca.net
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.zcarioca.simpleblog

/**
 * A table of contents
 * 
 * 
 * @author zcarioca
 */
class TableOfContents {

   private LinkedList<MenuItem> menuItems = new LinkedList<MenuItem>()
   
   void addItem(int level, String id, String text) {
      MenuItem newItem = new MenuItem(level, id, text)
      if (menuItems.empty || menuItems.peekLast().level == level) {
         menuItems.add(newItem)
      } else if (menuItems.peekLast().level < level) {
         menuItems.peekLast().addItem(newItem)
      } else {
         newItem.getSubItems().addAll(menuItems)
         menuItems.clear()
         menuItems.add(newItem)
      }
   }
   
   String toHTML() {
      StringBuilder out = new StringBuilder()
      int level = this.menuItems.peek().level
      
      out << "<ul class=\"table-of-contents toc-level-${level}\">\n"
      for (MenuItem item : menuItems) {
         out << item.toHTML()
      }
      out << "\n</ul>"
      
      return out.toString()
   }

   class MenuItem {
      private final int level
      private final String id
      private final String text
      private final LinkedList<MenuItem> subItems
      
      public MenuItem(int level, String id, String text) {
         this.level    = level
         this.id       = id
         this.text     = text
         this.subItems = new LinkedList<MenuItem>()
      }
      
      public int getLevel() {
         return this.level
      }
      
      public String getId() {
         return this.id
      }
      
      public String getText() {
         return this.text
      }
      
      public LinkedList<MenuItem> getSubItems() {
         return this.subItems
      }
      
      public void addItem(MenuItem item) {
         if (subItems.empty || subItems.peekLast().level == item.level) {
            subItems.add(item)
         } else if (subItems.peekLast().level < item.level) {
            subItems.peekLast().addItem(item)
         } else {
            item.getSubItems().addAll(subItems)
            subItems.clear()
            subItems.add(item)
         }
      }
      
      public String toHTML() {
         StringBuilder out = new StringBuilder()
         
         out << "\n<li><a href=\"javascript:scrollToElement('${id}');\">${text}</a>"
         if (!subItems.empty) {
            out << "\n<ul class=\"toc-level-${level + 1}\">"
            for (MenuItem item : subItems) {
               out << item.toHTML()
            }
            out << "\n</ul>"
         }
         out << "</li>"
         return out.toString()
      }
   }
}
