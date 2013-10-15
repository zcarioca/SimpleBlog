package net.zcarioca.simpleblog



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SynopsisService)
class SynopsisServiceTests {

   SynopsisService service = new SynopsisService()

   void testMakeHTML() {
      String markdown = """
This is a test of the table of contents

{:toc}

##The first header

Some content

##The second header

More content

###internal header

more content

## Last header

#Main Header 03

end of content
"""
      def output = service.makeHTML(markdown)
      println output
   }
}
