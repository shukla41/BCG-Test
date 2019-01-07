package NewTest


import java.util

object DatatypeEx {

  def main(args: Array[String]): Unit = {

    implicit def stringToname(s: String) = new implitest(s)

    var name = "Shuv"
    println(name.ismyname)

    println(functionExample(2,mul))
    println(execT(20,helloWorld))

    val str= currfunc("shuva")_
    println(str("moy"))

    //Here Notice how I have used placeholder syntax to inform Scala that
    // I am going to provide the value of productPrice later on.
    val dicountApplied = calculateProductPrice(30, _: Double)
    // Now with actual value
    println(dicountApplied(3))



  }


    def functionExample(a:Int, f:Int=>AnyVal):Unit = {
      val p=f(a)
    }
      def mul(a:Int): Int ={
       a *2
    }


  def execT(a:Int,f: () => Unit) {
    for (i <-1 to a) f()
  }
  def helloWorld(): Unit = { println("Hello, world") }

  //////////////////////////////////////////////////////////
  //Currying function

  def currfunc(s1: String)(s2: String) = s1+s2
 ////////////////////////////////////////////////////////
  //Partially Applied Functions

  def calculateProductPrice(discount: Double, productPrice: Double): Double =
    (100 - discount) * productPrice


}


///////////////////////////////////////
class implitest(s: String) {
  def ismyname: Boolean = s == "Shuva"
}