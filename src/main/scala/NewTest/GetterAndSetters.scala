package NewTest

object GetterAndSetters {
  def main(args: Array[String]): Unit = {

   val p= new Person()
    p.age=10
    println(p.getAge())
    new GetterAndSetters

    //////////////////////////
    //val p1= new Person1()  // Actual getter and setter
    //p1.age=50
    //println(p1.age)  //as its private, no accessable from other object and class

    //calling diff object
    Person1.i
  }


}

// Companion class
class GetterAndSetters {

  val p= new Person()
  p.age=20
  println(p.getAge())

}


  class Person {
  var name = "Noname"
  var age = -1

  def setName(name: String) {
    this.name = name
  }

  def setAge(age: Int) {
    this.age = age
  }

  def getName(): String = {
    name
  }

    def getAge(): Int = {
    age
  }
    getAge()
}

class Person1() {

  private var _ag: Int = -1

  private def age: Int = _ag

  def age_=(value: Int) = {
    _ag = value
  }


}


// private member can be accessible from companion object.
object Person1{
  val i= new Person1()
   i.age=90
  println(i.age)
}