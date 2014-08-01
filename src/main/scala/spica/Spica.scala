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
    /*
      HD 16141
      Teff = 5806 K
      logg = 4.19 dex
      [Fe/H] = +0.16 dex
    */
    val starIdentifier = "hd16"
    val hd160 = loadObservedSpectra(starIdentifier)
    println(hd160)
    
    
    val deltas = for {
      s <- ss
    } yield (s.line, s.lambda(0), s.lambda(s.lambda.length - 1))
    
    println(deltas)
    
    // val distances = for {
    //   obs <- hd160
    //   synth <- ss
    //   if (synth.line == obs.line)
    // } yield {
    //   // synthetic interpolated flux
    //   // using cubic splines
    //   // val interpolatedFlux = LinearInterpolator(synth.lambda, synth.flux)
    //   val interpolatedFlux = CubicInterpolator(synth.lambda, synth.flux)
    //
    //   // Checked
    //   val synthFlux = interpolatedFlux(obs.lambda)
    //   // observed - expected
    //   // FIXME
    //   (synth, breeze.numerics.sqrt(squaredDistance(obs.flux, synthFlux)))
    // }
    //
    // val lines = distances.groupBy(e => e._1.line)
    // println(lines.map { case (k, v) => {
    //   val min = v.map(_._2).min
    //   val indexOfMin = v.indexWhere(_._2 == min)
    //   v(indexOfMin)
    // }})
    // // println(lines)
  }  
}