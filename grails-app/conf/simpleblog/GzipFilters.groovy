package simpleblog

import org.apache.commons.io.IOUtils
import grails.converters.JSON
import java.util.zip.GZIPOutputStream

class GzipFilters {

    def filters = {
        jsonFilter(uri:'/json/**') {
            after = { Map model ->
               response.setContentType("application/json")
               response.setCharacterEncoding("UTF-8")
               response.setHeader('Content-Language', 'en')
               
               def accepts = request.getHeaders('Accept-Encoding')*.toLowerCase()
               def outputStream = null
               
               if (accepts.any{it.contains('gzip')}) {
                  response.setHeader('Content-Encoding', 'gzip')
                  response.setHeader('Vary', 'Accept-Encoding')
                  outputStream = new GZIPOutputStream(response.outputStream, 1049)
               } else {
                  outputStream = response.outputStream
               }
               println "Model: ${model}"
               outputStream << (model as JSON)
               IOUtils.closeQuietly(outputStream)
            }
        }
    }
}
