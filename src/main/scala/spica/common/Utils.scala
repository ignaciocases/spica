package common

object Utils {

  import scala.language.reflectiveCalls
  import java.io.{FileWriter, PrintWriter}

  /**
   * Simple Try for IO
   * from Foundation (ignaciocases)
   */
  def Try[A, B](a: => A)(b: => B): Option[A] = {
    try Some(a)
    catch { case e: Exception => throw(e); None }
    finally { b }
  }
  
  /**
   * Read/write utilities
   * After Pollak (2009)
   */
  def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B =
  try { f(param) } finally { param.close() }
  
  def writeToFile(fileName:String, data:String) = 
    using (new FileWriter(fileName)) {
      fileWriter => fileWriter.write(data)
    }

  def appendToFile(fileName:String, textData:String) =
    using (new FileWriter(fileName, true)){ 
      fileWriter => using (new PrintWriter(fileWriter)) {
        printWriter => printWriter.println(textData)
      }
    }

}