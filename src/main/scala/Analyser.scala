import java.util.Optional

import com.github.javaparser.ast.body.{FieldDeclaration, MethodDeclaration, VariableDeclarator}
import com.github.javaparser.ast.expr.NameExpr
import com.github.javaparser.ast.stmt.IfStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

import scala.collection.JavaConversions._
import scala.collection.mutable

trait Analyser extends VoidVisitorAdapter[Void]

object Utils {
  def toOptionScala[A](o: Optional[A]): Option[A] = if (o.isPresent) Some(o.get) else None

  def printRange: Optional[com.github.javaparser.Range] => String = rangeOpt => {
    toOptionScala(rangeOpt).map(_.toString).getOrElse("")
  }
}

case class EqIf() extends Analyser {

  override def visit(n: IfStmt, arg: Void): Unit = checkIf(n.asIfStmt)

  private def checkIf(ifStatement: IfStmt): Unit =
    if (ifStatement.getElseStmt.isPresent && ifStatement.getThenStmt == ifStatement.getElseStmt.get)
      println(Console.RED + s"If statement has equivalent branches! at ${Utils.printRange(ifStatement.getRange)}")

}

case class UselessArguments() extends Analyser {

  private val arguments: mutable.Set[String] = mutable.Set()

  override def visit(anyName: NameExpr, arg: Void): Unit =
    arguments.remove(anyName.getNameAsString)

  override def visit(n: MethodDeclaration, arg: Void): Unit = {
    val method = n.asMethodDeclaration.getNameAsString
    method match {
      case mtd if mtd != "main" =>
        arguments.clear
        n.getParameters.toList.map(_.getNameAsString).foreach(arguments.add)
        Utils.toOptionScala(n.getBody).foreach(super.visit(_, null))
        arguments.foreach(p => println(Console.RED + s"Unused argument $p in method $method at ${Utils.printRange(n.getRange)}"))
    }
  }
}

case class BadNaming() extends Analyser {

  override def visit(n: FieldDeclaration, arg: Void): Unit = {
    val list: List[VariableDeclarator] = n.getVariables.toList
    if (n.isStatic && n.isFinal)
      list.map(_.getNameAsString).foreach(p => if (p != p.toUpperCase) println(Console.RED + s"Field $p is not in uppercase at ${Utils.printRange(n.getRange)}"))
  }

}
