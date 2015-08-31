import httpz.Action
import scalaz._

package object gitter {

  private[gitter] final val baseURL = "https://api.gitter.im/v1/"

  type CommandToAction = Command ~> Action

  type Commands[A] = Free.FreeC[Command, A]

  val interpreter: CommandToAction = Interpreter
}
