package fpmax

import cats.effect.IO
import cats.syntax.apply._
import cats.syntax.monad._

import scala.util.Try

object MyApp {
  def main(args: Array[String]): Unit = {
    pureMain.unsafeRunSync()
  }

  def pureMain: IO[Unit] = {
    for {
      _ <- Console.println("What's your name?")
      name <- Console.readLine()
      _ <- Console.println("Hello, " + name + "! Welcome to the game!")
      _ <- playRound(name)
    } yield {}
  }

  def playRound(name: String): IO[Unit] =
    for {
      num   <- Random.nextInt(5).map(_ + 1)
      guess <- nextGuess(name)
      _     <-
        if (guess == num)
          Console.println("You guessed right, " + name + "!")
        else
          Console.println("You guessed wrong, " + name + "! The number was: " + num)
      shouldContinue <- askToContinue
      _ <- if (shouldContinue) playRound(name) else IO.unit
    } yield {}

  def nextGuess(name: String): IO[Int] =
    for {
      _  <- Console.println("Dear " + name + ", please guess a number from 1 to 5:")
      in <- Console.readLine()
      num = toInt(in)
      guess <- num match {
        case Some(n) if n < 1 || n > 5 =>
          Console.println("Your number must be from 1 to 5, try again.") *> nextGuess(name)
        case None =>
          Console.println("You must enter number, try again.") *> nextGuess(name)
        case Some(n) =>
          IO.pure(n)
      }
    } yield guess

  def askToContinue: IO[Boolean] =
    for {
      _  <- Console.println("Do you want to continue?")
      in <- Console.readLine()
      answer <- in match {
        case "y" => IO.pure(true)
        case "n" => IO.pure(false)
        case _   =>
          Console.println("You must answer with 'n' or 'y', try again.") *> askToContinue
      }
    } yield answer

  def toInt(s: String): Option[Int] = Try(s.toInt).toOption
}

object Console {
  def println(s: String): IO[Unit] = IO { Predef.println(s) }
  def readLine(): IO[String] = IO { scala.io.StdIn.readLine() }
}

object Random {
  def nextInt(n: Int): IO[Int] = IO { scala.util.Random.nextInt(n) }
}
