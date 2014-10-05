package gitter

import scalaz.IList

sealed abstract class Command[A]

object Command {
  object User extends Command[IList[User]]
  object Rooms extends Command[IList[Room]]
  final case class RoomUsers(roomId: String) extends Command[IList[Room.User]]
  final case class Channels(roomId: String) extends Command[IList[Channel]]
  final case class Messages(roomId: String, beforeId: Option[String], limit: Option[Long]) extends Command[IList[Message]]
  final case class SendMessage(roomId: String, text: String) extends Command[Message]
  final case class UpdateMessage(roomId: String, messageId: String, text: String) extends Command[Message]
}
