package NewTest

object ScalaCollectionFunction {

  def main(args: Array[String]): Unit = {

    println("Step 1: How to initialize a Set of type String to represent Donut elements")
    val donutBasket1: Set[String] = Set("Plain Donut", "Strawberry Donut")
    val b= List(1,2,3,4,5,6,7,8,9,10)
    println(s"Elements of donutBasket1 = $donutBasket1")

    println("\nStep 2: How to define an accumulator function to calculate the total length of the String elements")
    val donutLengthAccumulator: (Int, String) => Int = (accumulator, donutName) => accumulator + donutName.length

    println("\nStep 3: How to call aggregate function with the accumulator function from Step 2")
    val totalLength = donutBasket1.aggregate(0)(donutLengthAccumulator, _ + _)
    println(s"Total length of elements in donutBasket1 = $totalLength")


    val donutLengthAccumulator1: (Int, Int) => Int = (accumulator, donutName) => accumulator + donutName

    val totalLength1 = b.aggregate(0)(donutLengthAccumulator1, _ + _)
    println(s"Total length of elements in donutBasket1 = $totalLength1")
  }
}
