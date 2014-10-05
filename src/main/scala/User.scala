package gitter

import argonaut.CodecJson
import httpz.JsonToString

final case class User(
  displayName: String,
  url : String,
  avatarUrlMedium: String,
  username: String,
  invited: Boolean,
  v: Int,
  id: String,
  avatarUrlSmall: String
) extends JsonToString[User]

object User {

  implicit val instance: CodecJson[User] =
    CodecJson.casecodec8(apply, unapply)(
      "displayName",
      "url",
      "avatarUrlMedium",
      "username",
      "invited",
      "v",
      "id",
      "avatarUrlSmall"
    )

}
