package NewTest

class InheritanceDemo {

val par= new C()
  par.show()
}

/// MultiLevel inheritance

class A {
  // parent class
  var salary1 = 10000
}

class B extends A{
  var salary2 = 20000
}

class C extends B {
  def show(){
    println("salary1 = "+salary1)
    println("salary2 = "+salary2)
  }
}