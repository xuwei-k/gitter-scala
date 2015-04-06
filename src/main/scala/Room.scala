package gitter

import argonaut.CodecJson
import httpz.JsonToString

final case class Room (
  name: String,
  security: Option[String],
  url: String,
  uri: Option[String],
  mentions: Long,
  v: Option[Long],
  id: String,
  lurk: Boolean,
  noindex: Boolean,
  premium: Option[Boolean],
  lastAccessTime: Option[String], // TODO DateTime,
  oneToOne: Boolean,
  unreadItems: Long,
  topic: String,
  userCount: Long,
  githubType: String
) extends JsonToString[Room]

object Room {
  implicit val instance: CodecJson[Room] =
    CodecJson.casecodec16(apply, unapply)(
      "name",
      "security",
      "url",
      "uri",
      "mentions",
      "v",
      "id",
      "lurk",
      "noindex",
      "premium",
      "lastAccessTime",
      "oneToOne",
      "unreadItems",
      "topic",
      "userCount",
      "githubType"
    )


  final case class User(
    displayName: String,
    url: String,
    avatarUrlMedium: String,
    username: String,
    invited: Option[Boolean],
    id: String,
    avatarUrlSmall: String,
    v: Option[Int]
  ) extends JsonToString[User]

  object User {
    implicit val instance: CodecJson[User] =
      CodecJson.casecodec8(apply, unapply)(
        "displayName",
        "url",
        "avatarUrlMedium",
        "username",
        "invited",
        "id",
        "avatarUrlSmall",
        "v"
      )
  }

}
