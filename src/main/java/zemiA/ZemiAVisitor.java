package zemiA;

import org.eclipse.jdt.core.dom.*;

public class ZemiAVisitor extends ASTVisitor {
  private int Method_count;
  private int If_count;
  private int Case_count;
  private int Switch_count;

  @Override
  public boolean visit(MethodDeclaration node) {
    Method_count++;
    System.out.println("Method:" + Method_count);
    return super.visit(node);
  }

  @Override
  public boolean visit(IfStatement node) {
    If_count++;
    System.out.println("If:" + If_count);
    System.out.println(node.toString());
    return super.visit(node);
  }

  @Override
  public boolean visit(SwitchCase node) {
    Case_count++;
    System.out.println("Case:" + Case_count);
    return super.visit(node);
  }

  @Override
  public void endVisit(SwitchStatement node) {
    Switch_count++;
    System.out.println("Case:" + Switch_count);
    super.endVisit(node);
  }

  public int getMethod_Count(){
    return Method_count;
  }

  public int getIf_Count(){
    return If_count;
  }

  public int getSwitch_Count(){
    return Switch_count;
  }

  public int getCase_Count(){
    return Case_count;
  }
}
