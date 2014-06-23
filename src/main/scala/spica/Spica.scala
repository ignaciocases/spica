package spica

import scala.math._

import breeze.linalg._
import breeze.numerics._
import breeze.interpolation._

// import spire.algebra._
// import spire.math.poly._
// import spire.math._

import spica.functions._

import spectra._
import observedSpectra._

object Spica extends App {

  override def main(args: Array[String]): Unit = {
    // val synthSpectra = loadSyntheticSpectra.getOrElse(Nil)

    // asci_a1g4p95c1
    // linea a1 (1 a 35 -> 34)
    // gravedad 4.95 (4.00 a 5.00 -> 21)
    // Teff -> (de 5500K a 6500K con un paso de 50K)

    val ss = loadSyntheticSpectra()
    // println(ss.length)
    
    // Real spectra
    // 1000 simulaciones de los espectros observados por rango espectral    
    val starIdentifier = "hd16"
    val hd160 = loadObservedSpectra(starIdentifier)
    // println(hd160)
    
    val distances = for {
      obs <- hd160
      synth <- ss
    } yield {
      // synthetic interpolated flux
      // using cubic splines
      val interpolatedFlux = LinearInterpolator(synth.lambda, synth.flux)
      val synthFlux = interpolatedFlux(obs.lambda)
      // observed - expected
      // FIXME
      (synth, breeze.numerics.sqrt(squaredDistance(obs.flux, synthFlux)))
    }

    val lines = distances.groupBy(e => e._1.line)
    println(lines.map { case (k, v) => v.map(_._2).min } )
  }  
}