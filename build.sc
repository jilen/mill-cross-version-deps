import mill._
import mill.scalalib._
import mill.scalalib.api.Util

trait BaseModule extends ScalaModule {

  private def makeConstant(scalaV: String, platformSuffix: String): Dep => Dep = { (dep) =>
    val realModule = dep.dep.module.withName(coursier.ModuleName(dep.artifactName(Util.scalaBinaryVersion(scalaV), scalaV, platformSuffix)))
    val realDep =  dep.dep.withModule(realModule)
    dep.copy(
      dep = realDep,
      cross = CrossVersion.Constant("", true)
    )
  }

  def transitiveIvyDeps: T[Agg[Dep]] = T {
    val toConstant = makeConstant(scalaVersion(), platformSuffix())
    ivyDeps().map(toConstant) ++ mandatoryIvyDeps().map(toConstant) ++ T.traverse(moduleDeps)(_.transitiveIvyDeps)().flatten
  }
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


object baz extends BaseModule {

  override def scalaVersion = "3.2.2"

  def moduleDeps = Seq(bar213)


}
