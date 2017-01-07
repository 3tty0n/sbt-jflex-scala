object Tokens {
  trait Token
  case class ID(s: String) extends Token
  case class NUM(i: Int) extends Token
  case class REAL(d: Double) extends Token
  case object IF extends Token
  case object EOF extends Token
}