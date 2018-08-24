import cats.data.OptionT
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{FiniteDuration, SECONDS}

class MainSpec extends FlatSpec with Matchers {

  def await[A](future: Future[A]) = Await.result(future, FiniteDuration(2, SECONDS))

  "Option" should "behave as expected" in {
    val s: Option[Int] = Some(1)
    val n: Option[Int] = None

    s map (_ + 1) shouldBe Some(2)
    n map (_ + 1) shouldBe None
  }

  "Future[Option]" should "behave as expected" in {
    val s: Future[Option[Int]] = Future(Some(1))
    val n: Future[Option[Int]] = Future(None)

    await(s map (_ map (_ + 1))) shouldBe Some(2)
    await(n map (_ map (_ + 1))) shouldBe None
  }

  "OptionT" should "behave as expected" in {
    val s: OptionT[Future, Int] = OptionT(Future(Option(1)))
    val n: OptionT[Future, Int] = OptionT(Future(Option.empty))

    await(s.map(_ + 1).value) shouldBe Some(2)
    await(n.map(_ + 1).value) shouldBe None
  }

}