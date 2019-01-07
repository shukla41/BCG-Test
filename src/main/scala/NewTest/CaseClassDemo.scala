package NewTest


trait mytrait

case class CaseClass1(a:Int, b:Int) extends mytrait
case class CaseClass2(a:Int) extends mytrait


object CaseClassDemo {
  def main(args: Array[String]): Unit = {

    caseTest(CaseClass1(10,20))
    caseTest(CaseClass2(10))
    caseTest(CaseClass1(10,20))

    val p= CaseClass1(10,30)
    val b=p.copy()
    println(b)
    println(b.b)
  }

  def caseTest(f: mytrait) = f match {
    case CaseClass1(f,g) => println("a = "+f+" b ="+g)
    case CaseClass2(f) => println("a = "+f)
    case _ => "None"
}

}