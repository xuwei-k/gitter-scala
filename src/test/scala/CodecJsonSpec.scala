package gitter

import argonaut.{CodecJson, DecodeJson, EncodeJson}
import org.scalacheck.{Arbitrary, Prop, Properties}
import scalaz.scalacheck.ScalazArbitrary._
import scalaz.std.anyVal._
import scalaz.{Equal, IList}

object CodecJsonSpec extends Properties("CodecJson"){

  def encodedecode[A: EncodeJson: DecodeJson: Arbitrary: Equal] =
    Prop.secure(Prop.forAll(CodecJson.derived[A].codecLaw.encodedecode(_)))

  property("CodecJson[IList[Int]]") = encodedecode[IList[Int]]

}
