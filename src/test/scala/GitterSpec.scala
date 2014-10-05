package gitter

import httpz._, native._
import org.scalacheck.{Prop, Properties}

object GitterSpec {
  lazy val bearer: String = sys.env.getOrElse("BEARER", sys.error("BEARER does not defined"))
  lazy val bearerEndo = Request.bearer(bearer)

  final val scalajpRoomId = "542d73c3163965c9bc208264"
}

abstract class GitterSpec[A](command: Command[A], name: String) extends Properties(name) {
  import GitterSpec._

  val action = Gitter.commands2action(Gitter.lift(command))

  def run() = action.interpretWith(bearerEndo)

  property(name) = Prop.secure{
    val result = run()
    println(result)
    result.leftMap(_.fold(
      println,
      println,
      (_, error, history, _) => println((error, history)))
    )
    result.isRight
  }
}

object UserSpec extends GitterSpec(Command.User, "user")
object RoomsSpec extends GitterSpec(Command.Rooms, "rooms")
object RoomUsersSpec extends GitterSpec(Command.RoomUsers(GitterSpec.scalajpRoomId), "roomUsers")
object ChannelsSpec extends GitterSpec(Command.Channels(GitterSpec.scalajpRoomId), "channels")
object MessagesSpec extends GitterSpec(Command.Messages(GitterSpec.scalajpRoomId, None, None), "messages")

// TODO sendMessages, updateMessages
