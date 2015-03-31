package im.mange.little

import java.io.File

import org.eclipse.jetty.server.{Server, ServerConnector}
import org.eclipse.jetty.webapp.WebAppContext

class LittleServer(serverPort: Int, autoStart: Boolean = true, webAppPath: String = "src/main/webapp") {
  private val server = createServer(serverPort)
  private val context = createContext
  server.setHandler(context)

  private def createServer(port: Int) = {
    val server = new Server
    val httpConnector = new ServerConnector(server)
    httpConnector.setPort(port)
    server.setConnectors(Array(httpConnector))
    server
  }

  def start() = try {
    server.start()
  } catch {
    case e: Throwable => e.printStackTrace(); throw e
  }

  private def createContext = {
    val classLoader = getClass.getClassLoader
    def packagedPath(root: String) = classLoader.getResource(root).toExternalForm

    def discover(path: String, packaged: String, context: WebAppContext) =
      if (new File(path).exists()) path else packagedPath("webapp")

    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setClassLoader(classLoader)
    context.setWar(discover(webAppPath, "webapp", context))
    context
  }

  if (autoStart) start()
}
