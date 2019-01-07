package NewTest



object OOPS {

  def main(args: Array[String]): Unit = {

    val s = new Student(100,"shuva",20) // // Creating an object
    println(s.show())
    println(s.rollshow())

    val p = new NewStudent()
    println(p.show())

    new Arithmetic().add(4,4)
    new Arithmetic().add(5,4)
    new StudentCons()

  }
}

 class Student(id:Int,name: String) {  // Primary constructor which is defined at the time of class defination

  var roll: Int=0
  def this(id:Int,name: String, roll:Int){
    this(id,name)  // Calling primary constructor, and it is first line
    this.roll=roll
  }
  def show() : String= {
    val str= (id+" "+name)
    str
  }

  def rollshow() : Int= {
    val str= roll
    str
  }
}

protected class NewStudent {
  var id: Int= 0  //// All fields must be initialized for reular class
  var name: String=null


  def show() : String= {
    val str= (id+" "+name)
    str
  }

  def display() : Unit={
    println("HI Scala")
  }
}

class Arithmetic{
  def add(a:Int, b:Int){
    var add = a+b;
    println("sum = "+add);
  }
}

//defalut constructor
class StudentCons{
  println("Hello from default constructor");

}