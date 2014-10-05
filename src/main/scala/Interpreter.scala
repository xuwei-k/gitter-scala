package gitter

import httpz.{Core, Request}
import scalaz.IList

private[gitter] object Interpreter extends CommandToAction {
  override def apply[A](fa: Command[A]) = fa match {
    case Command.User =>
      Core.json[IList[User]](Request(baseURL + "user"))
    case Command.Rooms =>
      Core.json[IList[Room]](Request(baseURL + "rooms"))
    case Command.RoomUsers(roomId) =>
      Core.json[IList[Room.User]](Request(s"${baseURL}rooms/$roomId/users"))
    case Command.Channels(roomId) =>
      Core.json[IList[Channel]](Request(s"${baseURL}rooms/$roomId/channels"))
    case Command.Messages(roomId, beforeId, limit) =>
      val p = params("beforeId" -> beforeId, "limit" -> limit.map(_.toString))
      Core.json[IList[Message]](Request(s"${baseURL}rooms/$roomId/chatMessages", params = p))
    case Command.SendMessage(roomId, text) =>
      Core.json[Message](Request(s"${baseURL}rooms/$roomId/chatMessages", params = Map("text" -> text), method = "POST"))
    case Command.UpdateMessage(roomId, messageId, text) =>
      Core.json[Message](Request(s"${baseURL}rooms/$roomId/chatMessages/$messageId", params = Map("text" -> text), method = "PUT"))
  }

  private def params(p: (String, Option[String]) *): Map[String, String] = {
    p.foldLeft(Map.empty[String, String]){
      case (map, (_, None)) => map
      case (map, (k, Some(v))) => map.updated(k, v)
    }
  }
}
