import java.nio.file.Paths

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

object Runner {

  def main(args: Array[String]): Unit = {

    if(args.isEmpty){
      println("Arguments: File name")
    } else {
      val file = args(0)
      val visitors: List[Analyser] = List(EqIf(), UselessArguments(), BadNaming())
      val compilationUnit: CompilationUnit = JavaParser.parse(Paths.get(file))
      visitors.foreach(p => compilationUnit.accept(p, null))
    }
  }

}
