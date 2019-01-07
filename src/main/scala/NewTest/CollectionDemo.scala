package NewTest

import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object CollectionDemo {
  def main(args: Array[String]): Unit = {

    var seq:Seq[Int] = Seq(52,85,1,8,3,2,7)
    seq.foreach((element:Int) => print(element+" "))
    println("\nis Empty: "+seq.isEmpty)
    println("Ends with (2,7): "+ seq.endsWith(Seq(2,7)))
    println("contains 8: "+ seq.contains(8))
    println("last index of 3 : "+seq.lastIndexOf(3))
    println("Reverse order of sequence: "+seq.reverse)


    //Use an Array if the length of Array is fixed, and an ArrayBuffer if the length can vary.

    val arr= new Array[Int](3)
    for (i<-0 to 2){
     arr(i)=i
    }
    for (i <- arr){
      println(i)
    }


    val favNumsArrayBuffer= new ArrayBuffer[Int]
    for(j<-0 to 5){
      favNumsArrayBuffer.insert(j, (j*2))
      //favNumsArrayBuffer++=Array(j*3)
    }
    favNumsArrayBuffer.foreach(println)

    // List is used to store ordered elements. It extends LinearSeq trait. It is a class for immutable linked lists.
    // This class is good for last-in-first-out (LIFO), stack-like access patterns.

    var list = List(1,8,5,6,9,58,23,15,4)
    var list2 = List(88,100)
    print("Elements: ")
    list.foreach((element:Int) => print(element+" "))        // Iterating using foreach loop
    print("\nElement at 2 index: "+list(2))             // Accessing element of 2 index
    var list3 = list ++ list2                               // Merging two list
    print("\nElement after merging list and list2: ")
    list3.foreach((element:Int)=>print(element+" "))
    var list4 = list3.sorted                            // Sorting list
    print("\nElement after sorting list3: ")
    list4.foreach((element:Int)=>print(element+" "))
    var list5 = list3.reverse                           // Reversing list elements
    print("\nElements in reverse order of list5: ")
    list5.foreach((element:Int)=>print(element+" "))


    // Vector is a general-purpose, immutable data structure. It provides random access of elements.
    // It is good for large collection of elements.
    var vector:Vector[Int] = Vector(5,8,3,6,9,4) //Or
    var vector2 = Vector(5,2,6,3)
    var vector3 = Vector.empty
    println(vector)
    println(vector2)
    println(vector3)

    //Queue implements a data structure that allows inserting and retrieving elements
    // in a first-in-first-out (FIFO) manner.
    var queue = Queue(1,5,6,2,3,9,5,2,5)
    print("Queue Elements: ")
    queue.foreach((element:Int)=>print(element+" "))
    var firstElement = queue.front
    print("\nFirst element in the queue: "+ firstElement)
    var enqueueQueue = queue.enqueue(100)
    print("\nElement added in the queue: ")
    enqueueQueue.foreach((element:Int)=>print(element+" "))
    var dequeueQueue = queue.dequeue
    print("\nElement deleted from this queue: "+ dequeueQueue)


    // Map is used to store elements. It stores elements in pairs of key and values.
    var map  = Map("A"->"Apple","B"->"Ball")             // Creating map
    println(map("A"))                            // Accessing value by using key
    var newMap = map+("C"->"Cat")                  // Adding a new element to map
    println(newMap)
    var removeElement = newMap - ("B")                // Removing an element from map
    println(removeElement.contains("C"))
    println(removeElement.addString(new mutable.StringBuilder()))



    //HashMap is used to store element. It use hash code to store elements and return a map.
    var hashMap = mutable.HashMap("A"->"Apple","B"->"Ball","C"->"Cat")
    hashMap.foreach {
      case (key, value) => println (key + " -> " + value)       // Iterating elements
    }
    println(hashMap("B"))               // Accessing value by using key
    var newHashMap = hashMap+("D"->"Doll")
    newHashMap.foreach {
      case (key, value) => println (key + " -> " + value)
    }


     //It is used to store unique elements in the set.
    // It does not maintain any order for storing elements. You can apply various operations on them.

    val set1 = Set()                            // An empty set
    val games = Set("Cricket","Football","Hocky","Golf")    // Creating a set with elements
    println(set1)
    println(games)

    //HashSet is a sealed class. It extends AbstractSet and immutable Set trait. It uses hash code to store elements.
    var hashset = mutable.HashSet(4,2,8,0,6,3,45)
    hashset.foreach((element:Int) => println(element+" "))
  }



}
