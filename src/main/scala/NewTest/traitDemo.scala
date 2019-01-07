package NewTest

object traitDemo {

  def main(args: Array[String]): Unit = {

    var t = new A7()
    t.show()
    t.print()
    t.showdef()
    t.demof()
    t.f1()
    println(t.p)

  }
}


trait Printale{
  def print()
}

trait Showale{
  def show()
}

trait run{
  def showdef()
}

trait demo {
  def demof() // Abstract method
  def f1() ={  // Non Abstract Method
    println("my name is f1")
  }
}

// If you mixins with other class and traits, then extend the class first followed by traits
// If we have more than 2 traits and you extend more than 2 traits using with , then class has to be abstract
// abstract class cant be instantiated. Need to extend the abstract class by creating another class.

class y {
  var p: Int=8
}

  class A8 extends y with Printale with Showale with run with demo  {
  def print(){
    println("This is printable")
  }
  def show(){
    println("This is showable");
  }

  def showdef(){
    println("This is showdef");
  }
  def demof(){
    println("This is demof");
  }

}

class A7 extends A8 {

}