package NewTest

object classExample {

  def main(args: Array[String]): Unit = {

    // How to check the datatype using clousures .
    val pf1 : PartialFunction[Any,String] = { case s:String => "yes"}
    val pf2 : PartialFunction[Any,String] = {case s:Double => "yes"}


   // val pf= pf1 orElse pf2


    def tryPF(x: Any, f: PartialFunction[Any,String]) : String ={
      try{
        f(x).toString
      }
      catch {
        case _: MatchError => "Error"
      }
    }

    List("str",3.14,10).foreach{
      p=> println(tryPF(p,pf1))
    }



  }



}



