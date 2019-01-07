package NewTest

object SimpleTrait {
  def main(args: Array[String]): Unit = {

    var t=new A6()
    t.print()
  }
}

trait Printable{
  def print()
}

trait Showable{
  def show()
}

class A6 extends Printable with Showable {
  def print(){
    println("This is printable")
  }
  def show(){
    println("This is showable");
  }
}