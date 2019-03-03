package phobosive.main.layer
import phobosive.context.io.IoService
import phobosive.context.processing.ProcessingService

trait ServiceLayer { self: BaseLayer =>
  lazy val processingService: ProcessingService = new ProcessingService()
  lazy val ioService: IoService                 = new IoService()
}
