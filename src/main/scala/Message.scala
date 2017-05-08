package gitter

import argonaut.{CodecJson, Json}
import argonaut.ArgonautScalaz._
import gitter.Message.{Issue, Mention}
import httpz.JsonToString
import scalaz.IList

final case class Message (
  id: String,
  text: String,
  html: String,
  sent: String,
  editedAt: Option[String],
  fromUser: Room.User,
  unread: Boolean,
  readBy: Long,
  urls: IList[Message.URL],
  mentions: IList[Mention],
  issues: IList[Issue],
  meta: Json, // TODO
  v: Long
) extends JsonToString[Message]

object Message {

  implicit val instance: CodecJson[Message] =
    CodecJson.casecodec13(apply, unapply)(
      "id",
      "text",
      "html",
      "sent",
      "editedAt",
      "fromUser",
      "unread",
      "readBy",
      "urls",
      "mentions",
      "issues",
      "meta",
      "v"
    )

  final case class Mention(
    screenName: String,
    userId: Option[String],
    userIds: Json // TODO
  ) extends JsonToString[Mention]

  object Mention {
    implicit val instance: CodecJson[Mention] =
      CodecJson.casecodec3(apply, unapply)(
        "screenName",
        "userId",
        "userIds"
      )
  }

  final case class Issue(number: Long) extends JsonToString[Issue]

  object Issue {
    implicit val instance: CodecJson[Issue] =
      CodecJson.casecodec1(apply, unapply)(
        "number"
      )
  }

  final case class URL(url: String) extends JsonToString[URL]

  object URL {
    implicit val instance: CodecJson[URL] =
      CodecJson.casecodec1(apply, unapply)(
        "url"
      )
  }
}
