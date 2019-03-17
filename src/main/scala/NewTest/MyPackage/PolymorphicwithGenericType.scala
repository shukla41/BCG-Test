package NewTest.MyPackage

object PolymorphicwithGenericType {

  def main(args: Array[String]): Unit = {

    def applyDiscount[T](discount: T) {
      discount match {
        case d: String =>
          println(s"Lookup percentage discount in database for $d")

        case d: Double =>
          println(s"$d discount will be applied")

        case _ =>
          println("Unsupported discount type")
      }
    }

    applyDiscount[Double](8.8)
  }
}
