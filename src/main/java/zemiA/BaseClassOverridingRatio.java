package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;

public class BaseClassOverridingRatio extends ASTVisitor {

	String o = "Override";

	@Override
	public boolean visit(SimpleName node) {
		if (o.equals(node.getIdentifier())) {
			System.out.println("a");
		}
		return super.visit(node);
	}

}
