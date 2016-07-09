package gitter

import httpz.Action
import scalaz._

object Gitter extends Gitter[Command] {
  implicit def instance[F[_]](implicit I: Inject[Command, F]): Gitter[F] =
    new Gitter[F]

  def commands2action[A](a: Commands[A]): Action[A] =
    a.foldMap(interpreter)(httpz.ActionMonad)
}


class Gitter[F[_]](implicit I: Inject[Command, F]) {

  private[this] type FreeF[A] = Free[F, A]

  final def lift[A](f: Command[A]): FreeF[A] =
    Free.liftF(I.inj(f))

  final val user: FreeF[IList[User]] =
    lift(Command.User)

  final val rooms: FreeF[IList[Room]] =
    lift(Command.Rooms)

  final def roomUsers(roomId: String): FreeF[IList[Room.User]] =
    lift(Command.RoomUsers(roomId))

  final def channels(roomId: String): FreeF[IList[Channel]] =
    lift(Command.Channels(roomId))

  final def messages(roomId: String): FreeF[IList[Message]] =
    lift(Command.Messages(roomId, None, None))

  final def sendMessages(roomId: String, text: String): FreeF[Message] =
    lift(Command.SendMessage(roomId, text))

  final def updateMessages(roomId: String, messageId: String, text: String): FreeF[Message] =
    lift(Command.UpdateMessage(roomId, messageId, text))
}
