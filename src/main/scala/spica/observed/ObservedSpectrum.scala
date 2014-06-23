package spica

import breeze.linalg._
import breeze.numerics._
import spectra._

/**
 * Contains the wavelenght and relative flux for an observed spectrum
 *
 * @author ignaciocases
 **/

case class ObservedSpectrum(starIdentifier: String,
                                      line: String,
                            sequenceNumber: String,
                                    lambda: DenseVector[Double], 
                                      flux: DenseVector[Double]) 
{
    
}