import im.mange.little.LittleServletServer

object TestServletServer extends App {
  val server = new LittleServletServer(1234, autoStart = false)
  server.add("/test", staticContent("<b>Hello</b>"))
  server.start()

  private def staticContent(html: String) = {
    import javax.servlet.http.{ HttpServletResponse, HttpServletRequest, HttpServlet }

    new HttpServlet {
      override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.getOutputStream.print(html)
      }
    }
  }

}
