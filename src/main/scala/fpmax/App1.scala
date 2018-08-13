package fpmax

import scala.io.StdIn.readLine

object App1 {
  def main(): Unit = {
    println("What's your name?")

    val name = readLine()

    println("Hello, " + name + ", welcome to the game!")

    var exec = true

    while (exec) {
      val num = scala.util.Random.nextInt(5) + 1

      println("Dear " + name + ", please guess a number from 1 to 5:")

      // Partial function:
      val guess = readLine().toInt

      if (guess == num)
        println("You guessed right, " + name + "!")
      else
        println("You guessed wrong, " + name + "! The number was: " + num)

      println("Do you want to continue?")

      readLine() match {
        case "y" => exec = true
        case "n" => exec = false
      }
    }
  }
}
