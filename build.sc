import mill._
import mill.scalalib._

trait BaseModule extends ScalaModule {

}

object foo3 extends BaseModule {
  override def scalaVersion = "3.2.2"
  def ivyDeps = Agg(
    ivy"io.circe::circe-core:0.14.5",
    ivy"io.circe::circe-parser:0.14.5"
  )
}

object bar213 extends BaseModule {
  override def scalaVersion = "2.13.10"
  override def ivyDeps = Agg(
    ivy"com.chuusai::shapeless:2.3.10"
  )

  def moduleDeps = Seq(foo3)

  def scalacOptions = Seq(
    "-Ytasty-reader"
  )
}


object baz3 extends BaseModule {

  override def scalaVersion = "3.2.2"

  def moduleDeps = Seq(bar213)


}
