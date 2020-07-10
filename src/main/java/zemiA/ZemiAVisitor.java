package zemiA;

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

<<<<<<< HEAD
}
=======
  @Override
  public boolean visit(final MethodInvocation node) {
    System.out.println(node.toString());
    return super.visit(node);
  }
}
>>>>>>> 341e5abc0056e031a4d0e2fca7f8281e3b910253
