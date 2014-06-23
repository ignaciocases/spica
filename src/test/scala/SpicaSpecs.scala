package test.spica

import org.scalatest.FunSpec

class SpicaSpec extends FunSpec {
  describe("Adding 1 to 1") {
    it("should equals 2"){
      assert(1+1 == 2)
    }
  }
}