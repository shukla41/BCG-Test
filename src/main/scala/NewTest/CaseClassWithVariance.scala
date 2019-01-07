package NewTest


trait mytrait1[+T]
case class Value[T](t:T,p:T) extends mytrait1[T]


object CaseClassWithVariance {

  def main(args: Array[String]): Unit = {

    val p: mytrait1[Int]= Value(20,30)
    myValue(p)
  }

  def myValue[T](f:mytrait1[T]): Unit = f match {
    case Value(p,v) => println(s"Your value is $p and $v")
  }
}
