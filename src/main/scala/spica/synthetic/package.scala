package object spectra {

  import java.io.File.{separator => /}
  import breeze.linalg._
  import breeze.numerics._

  import spica.functions._
  import spica.SyntheticSpectrum

  /**
   * Environement variables
   */
  val syntheticSpectrumPath = List("moog", "spectra.txt")
  val syntheticSpectrumCPath = List("moog", "SYNTC")
  val observedSpectraPath = List("moog", "SPEC")
  val spicaDataPath = System.getenv("SPICA_DATA")

  // 21 different values of Teff (from 5500K to 6500K with a step of 50K)
  // 21 different values of log g (from 4.0 dex to 5.0 dex with a step of 0.05 dex)
  // 11 different values of [Fe/H] (from 0. dex to +0.5 dex with a step 0.05dex)
  // 34 spectral lines corresponding to Fe I and Fe II.

  val initialTeff = 5500
  val endTeff = 6500
  val stepTeff = 50

  /**
   * Helper method that creates the file name based on stellar parameters
   */
  def fName(line: String, gravity: Double, Teff: Double, metalicity: Double) = {
    val g = if (gravity.isValidInt) gravity.toInt.toString
            else gravity.toString.replace(".","p")
    
    s"asci_a${line}g${g}c1"
  }

  /**
   * Helper method for reading spectrum files
   *
   */
  def loadSyntheticSpectrum(line: String, gravity: Double, Teff: Double, metalicity: Double) = {
    val spectraFile = Option {      
      val fileName = fName(line, gravity, Teff, metalicity)
      val spectrumPath = syntheticSpectrumCPath ::: List(fileName)
      spicaDataPath + / + spectrumPath.mkString("/")
    } getOrElse {
      sys.error("Could not load synthetic spectra, files not found")
    }
    println(spectraFile)
    import common.Utils._
    Try {
      val s = io.Source.fromFile(spectraFile)
      s.getLines.toList.map(_.toDouble)
    }()
  }

  /**
   * Reads a synthetic spectrum from file into a SyntheticSpectrum object
   *
   */
  // def syntheticSpectrum(line: String, gravity: Double, Teff: Double, metalicity: Double) = {
  //   val position = (Teff.toInt - initialTeff)/stepTeff
  //   val fileLines: Option[List[Double]] = loadSyntheticSpectrum(line, gravity, Teff, metalicity)
  //   val nSpectralPoints: Int = fileLines.map(_.head.toInt).getOrElse(0)
  //   println(nSpectralPoints)
  //
  //   val lambda: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
  //   val flux: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
  //
  //   for(k <- 0 to (nSpectralPoints - 1)) {
  //     lambda(k) = fileLines.map(_.tail.apply(k)).getOrElse(0)
  //     flux(k) = fileLines.map(_.tail.apply(k + (position + 1) * nSpectralPoints)).getOrElse(0)
  //   }
  //
  //   // creates the synthetic spectrum object
  //   SyntheticSpectrum(line, gravity, Teff, metalicity, lambda, flux)
  // }

  /**
   * Loads the grid of synthetic spectra
   *
   */
  def loadSyntheticSpectra() = {
    // 21 different values of Teff (from 5500K to 6500K with a step of 50K)
    // 21 different values of log g (from 4.0 dex to 5.0 dex with a step of 0.05 dex)
    // 11 different values of [Fe/H] (from 0. dex to +0.5 dex with a step 0.05dex)
    // 34 spectral lines corresponding to Fe I and Fe II.
    
    for {
      l <- (1 to 2).filterNot(_ == 31)
      val line = l.toString
      g <- 400 to 410 by 5
      val gravity = g/100.0
      val metalicity = 0.0
    } yield {

      val fileLines: Option[List[Double]] = loadSyntheticSpectrum(line, gravity, initialTeff, metalicity)
      val nSpectralPoints: Int = fileLines.map(_.head.toInt).getOrElse(0)
        
      for {
        teff <- initialTeff to endTeff by stepTeff
      } yield {
        val position = (teff - initialTeff)/stepTeff

        val lambda: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
        val flux: DenseVector[Double] = DenseVector.zeros[Double](nSpectralPoints)
      
        for(k <- 0 to (nSpectralPoints - 1)) {
          lambda(k) = fileLines.map(_.tail.apply(k)).getOrElse(0)
          flux(k) = fileLines.map(_.tail.apply(k + (position + 1) * nSpectralPoints)).getOrElse(0)
        }
        // creates the synthetic spectrum object
        SyntheticSpectrum(line, gravity, teff, metalicity, lambda, flux)
      }
    }
  }.flatten
}