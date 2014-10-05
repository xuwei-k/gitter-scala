package gitter

import argonaut.CodecJson
import httpz.JsonToString

final case class Channel(
  id: String,
  name: String,
  topic: String,
  uri: String,
  oneToOne: Boolean,
  unreadItems: Long,
  mentions: Long,
  lastAccessTime: String,
  lurk: Boolean,
  url: String,
  githubType: String,
  security: String,
  v: Option[Int]
) extends JsonToString[Channel]

object Channel {

  implicit val instance: CodecJson[Channel] =
    CodecJson.casecodec13(apply, unapply)(
      "id",
      "name",
      "topic",
      "uri",
      "oneToOne",
      "unreadItems",
      "mentions",
      "lastAccessTime",
      "lurk",
      "url",
      "githubType",
      "security",
      "v"
    )
}
