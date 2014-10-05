import argonaut._
import httpz.Action
import scalaz._

package object gitter {

  /** @todo will be remove [[https://github.com/argonaut-io/argonaut/pull/141]] */
  implicit def IListDecodeJson[A](implicit e: DecodeJson[A]): DecodeJson[IList[A]] =
    DecodeJson(a =>
      a.downArray.hcursor match {
        case None =>
          if (a.focus.isArray)
            DecodeResult.ok(IList.empty)
          else
            DecodeResult.fail("[A]IList[A]", a.history)
        case Some(hcursor) =>
          hcursor.traverseDecode(IList.empty[A])(_.right, (acc, c) =>
            c.jdecode[A] map (_ :: acc)) map (_.reverse)
      })

  /** @todo will be remove [[https://github.com/argonaut-io/argonaut/pull/141]] */
  implicit def IListEncodeJson[A](implicit A: EncodeJson[A]): EncodeJson[IList[A]] =
    EncodeJson(fa => Json.jArray(fa.foldRight(List.empty[Json])((a, b) => A.encode(a) :: b)))

  private[gitter] final val baseURL = "https://api.gitter.im/v1/"

  type CommandToAction = Command ~> Action

  type Commands[A] = Free.FreeC[Command, A]

  val interpreter: CommandToAction = Interpreter
}
