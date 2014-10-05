package gitter

import httpz.Action
import scalaz.Free.FreeC
import scalaz._

object Gitter extends Gitter[Command] {
  implicit def instance[F[_]](implicit I: Inject[Command, F]): Gitter[F] =
    new Gitter[F]

  def commands2action[A](a: Commands[A]): Action[A] =
    Free.runFC[Command, Action, A](a)(interpreter)(httpz.ActionMonad)
}


class Gitter[F[_]](implicit I: Inject[Command, F]) {

  private[this] type FreeCF[A] = FreeC[F, A]

  final def lift[A](f: Command[A]): FreeCF[A] =
    Free.liftFC(I.inj(f))

  final val user: FreeCF[IList[User]] =
    lift(Command.User)

  final val rooms: FreeCF[IList[Room]] =
    lift(Command.Rooms)

  final def roomUsers(roomId: String): FreeCF[IList[Room.User]] =
    lift(Command.RoomUsers(roomId))

  final def channels(roomId: String): FreeCF[IList[Channel]] =
    lift(Command.Channels(roomId))

  final def messages(roomId: String): FreeCF[IList[Message]] =
    lift(Command.Messages(roomId, None, None))

  final def sendMessages(roomId: String, text: String): FreeCF[Message] =
    lift(Command.SendMessage(roomId, text))

  final def updateMessages(roomId: String, messageId: String, text: String): FreeCF[Message] =
    lift(Command.UpdateMessage(roomId, messageId, text))
}
