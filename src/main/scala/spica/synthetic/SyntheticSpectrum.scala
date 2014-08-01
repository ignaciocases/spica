package spica

import breeze.linalg._
import breeze.numerics._
import spectra._

/**
 * Contains the wavelenght and relative flux for a synthetic spectrum generated 
 * by MOOG for a given spectroscopic line, characterized by gravity, 
 * Teff, and metalicity.
 *
 * @author ignaciocases
 **/

case class SyntheticSpectrum(line: String, 
                          gravity: Double, 
                             Teff: Double, 
                       metalicity: Double, 
                           lambda: DenseVector[Double], 
                             flux: DenseVector[Double]) 
{
    
  // This is a helper method, as the object does not know where it got the spectrum
  val fileName = fName(line, gravity, Teff, metalicity)
  val stellarParams = s"a${line}  ${Teff} ${gravity} ${metalicity}"
  override def toString = stellarParams
  
  def chiSquared(obs: ObservedSpectrum): Double = {
    0.0
  }
}
