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

   println(p)



  }

}
