package org.ada.web.runnables.sctchallenge

import org.apache.commons.lang3.StringEscapeUtils
import org.incal.core.runnables.{InputRunnable, InputRunnableExt}
import org.incal.core.util.{listFiles, writeStringAsStream}

import scala.io.Source

class MergeResultsFromFolder extends InputRunnableExt[MergeResultsFromFolderSpec] {

  private val defaultDelimiter = ","
  private val eol = "\n"

  override def run(
    input: MergeResultsFromFolderSpec
  ): Unit = {
    val delimiter = StringEscapeUtils.unescapeJava(input.delimiter.getOrElse(defaultDelimiter))

    val inputFileNames = listFiles(input.inputFolderName).map(_.getName).filter(_.endsWith(input.extension))

    val results = inputFileNames.flatMap { inputFileName =>
      val lines = Source.fromFile(input.inputFolderName + "/" + inputFileName).getLines()
      lines.map { line =>
        val els = line.split(delimiter, -1).map(_.trim)
        val genesNum = els(0)
        val distance = els(1)
        val positionFileName = els(2)

        (genesNum, distance, positionFileName)
      }
    }

    val resultsSorted = results.sortWith { case ((genesNum1, distance1, _), (genesNum2, distance2, _)) =>
      if (genesNum1 == genesNum2) distance1 < distance2 else genesNum1 < genesNum2
    }

    val content = resultsSorted.map { case (genesNum, distance, fileName) => Seq(genesNum, distance, fileName).mkString(delimiter)}.mkString(eol)
    writeStringAsStream(content, new java.io.File(input.exportFileName))
  }
}

case class MergeResultsFromFolderSpec(
  inputFolderName: String,
  extension: String,
  delimiter: Option[String],
  exportFileName: String
)
