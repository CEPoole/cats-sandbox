package sandbox

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  val futureOptionInt: Future[Option[Int]] = Future(Option(10))
  val result1 = {
    futureOptionInt map { optionInt =>
      optionInt map { int =>
        int * 10
      }
    }
  }

  val optionT: OptionT[Future, Int] = OptionT(futureOptionInt)
  val result2 = {
    optionT map (int => int * 10)
  }

}
