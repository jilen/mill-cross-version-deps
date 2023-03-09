package app

import dal.FooRepo

@main
def testApp(): Unit = {
  println(FooRepo.getById(0L).map(_.status))
}
