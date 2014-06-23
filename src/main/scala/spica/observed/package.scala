package object observedSpectra {

  import java.io.File.{separator => /}
  import breeze.linalg._
  import breeze.numerics._

  import spica.functions._
  import spica.ObservedSpectrum

  val observedSpectraPath = List("moog", "SPEC")
  val spicaDataPath = System.getenv("SPICA_DATA")

  def fName(line: String, sequenceNumber: String, starIdentifier: String): String = {
    s"na${line}${sequenceNumber}${starIdentifier}.asc"
  }

  def loadObservedSpectrum(line: String, sequenceNumber: String, starIdentifier: String) = {
    val spectraFile = Option {      
      val fileName = fName(line, sequenceNumber, starIdentifier)
      val spectrumPath = observedSpectraPath ::: List(fileName)
      spicaDataPath + / + spectrumPath.mkString("/")
    } getOrElse {
      sys.error("Could not load observed spectra, files not found")
    }
    println(spectraFile)
    import common.Utils._
    Try {
      val s = io.Source.fromFile(spectraFile)
      s.getLines.toList
    }()
  }

  /**
   * Loads the grid of observed spectra
   *
   */
  def loadObservedSpectra(starIdentifier: String) = {
    for {
      l <- (1 to 1).filterNot(_ == 31)
      val line = l.toString
      n <- 0 to 1 //999
      val sequenceNumber = f"${n}%03d"
    } yield {

      val fileLines: List[String] = loadObservedSpectrum(line, sequenceNumber, starIdentifier).getOrElse(Nil)
      // println(fileLines)
      
      // TODO: combine both methods to do a single pass
      val (lambdaList, fluxList) = fileLines.map { _.split("\\s+") match {
        case Array(l, f) => (l, f)
      }}.unzip
      
      val nSpectralPoints = lambdaList.length
      
      val lambda: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
      val flux: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
      
      for(k <- 0 to (nSpectralPoints - 1)) {
        lambda(k) = lambdaList(k).toDouble
        flux(k) = fluxList(k).toDouble
      }
      
      ObservedSpectrum(starIdentifier, line, sequenceNumber, lambda, flux)
    }
  }
}