package NewTest.MyPackage

object mytest {
  def main(args: Array[String]): Unit = {

    // Lambda function
    val x: Int => Double = _ *2
    val y: Int => Double = x => x *2
    val z= (x: Int) => x *2

    val list: List[Int] => List[Int] = y=> y.map(p=> p *2)

    val list1: List[Int] => List[Double] = _.map(p=> p.toDouble * 3)
    val l: List[Int] = List(1,2,3)

    l.map{
      p=> y(p)
    }.foreach(println)

    list1(l).foreach(println)
    // Example with function

    def transformedSum(original: List[Long], f: (Long) => Long) = {
      original.map(f).sum
    }

    println(transformedSum(List(1l, 2l, 3l), (l: Long) => l * l))


    def func(x1: Int, f:Int => Int) = f(x1)

    println(func(4, (c:Int) => c *2))

    // takes 2 parameter
    def sumX(a: Int,b: Int ,f:(Int,Int) => Int) = f(a,b)

    println(sumX(4,5, (g:Int,k: Int) => g+k))


    val maxRate = 100
    val someValues = List(2, 4, 6, 8, 10)


    //While a lambda is essentially the same as a function, taking parameters and returning a value,
    // a closure is a slightly different beast: It “closes over” a scope, and may have access to
    // values declared in that scope that are not explicitly passed as parameters.

    def computeFactor( i : Int)= i *i

    val p =someValues.map(value => {
      val factor = computeFactor(value)
       factor
    })




    // Higher order function example, here we must use anonymous function inside the body of the function


    def greetSomeone(s:String) ={
      println("testing")
        (name: String) => println(s"$s $name")
       // if you do not pass, you can pass blank function
        () => println(s"this is $s")
    }

    val j=greetSomeone("hello")
        j("Shuva")

    greetSomeone("Hi")("guy")


    def f1(x: Int) = (y:Int) => math.sqrt(x + y)



    val k= f1(15)(10)
    println(k)




    def sumOfX(f:Int=> Int,a:Int,b:Int): Int ={
      if (a> b) 0 else f(a) + sumOfX(f,a+1,b)
    }



    val si = sumOfX(x => x,0,5)
    println(si)

    val k1=sumOfX(x=> x *x, _:Int , _:Int)
    val j1=k1(3,4)
    println(j1)
  }

}
