package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;

public class ZemiAVisitor extends ASTVisitor {

  @Override
  public boolean visit(SimpleName node) {
    //System.out.println(node.getIdentifier());
    return super.visit(node);
  }

  @Override
  public boolean visit(MethodDeclaration node) {
    //System.out.println(node.toString());
    return super.visit(node);
  }

  @Override
  public boolean visit(final MethodInvocation node) {
    System.out.println(node.toString());
    return super.visit(node);
  }

   @Override
  public boolean visit(final IfStatement node){
    System.out.println(node.toString());
    System.out.println(node.getElseStatement().toString());
    return super.visit(node);
   }
}
