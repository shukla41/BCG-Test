package NewTest

object MethodOverride {

  def main(args: Array[String]): Unit = {

    var b = new Bike()
    b.run()

  }
}

// parent class
class Vehicle{

  // If you put final, that means the method cant be override
   def run(){
    println("vehicle is running")
  }
}

// child class
class Bike extends Vehicle{
  override def run(){
    println("Bike is running")
  }
}
