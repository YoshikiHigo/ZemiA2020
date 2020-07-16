package zemiA;

import org.eclipse.jdt.core.dom.*;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

public class ZemiAVisitor extends ASTVisitor {

	@Override
	public boolean visit(SimpleName node) {
		System.out.println(node.getIdentifier());
		return super.visit(node);
	}

<<<<<<< HEAD
	@Override
	public boolean visit(MethodDeclaration node) {
		//System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodInvocation node) {
		//System.out.println(node.toString());
		return super.visit(node);
	}
=======
  public ZemiAVisitor() {
    super();
  }

  @Override
  public boolean visit(MethodDeclaration node) {
    System.out.println(node.toString());
    return super.visit(node);
  }

  public ZemiAVisitor(boolean visitDocTags) {
    super(visitDocTags);
  }

  @Override
  public boolean visit(final MethodInvocation node) {
    System.out.println(node.toString());
    return super.visit(node);
  }
>>>>>>> 80589c55598fab838caee59d613f3558a247e875
}
