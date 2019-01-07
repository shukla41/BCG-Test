package NewTest



  object SingleTonObj{
    def main(args:Array[String]){
      SingletonObject.hello()         // No need to create object.
      //val k=new SingleTonObj()
      //k.dip()
    }
  }


  object SingletonObject{
    def hello(){
      println("Hello, This is Singleton Object")
    }


}
