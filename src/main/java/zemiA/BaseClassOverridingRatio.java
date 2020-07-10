package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;

public class BaseClassOverridingRatio extends ASTVisitor {

	String o = "Override";

	@Override
	public boolean visit(SimpleName node) {
		// TODO 自動生成されたメソッド・スタブ
		if (o.equals(node.getIdentifier())) {
			System.out.println("aとbは同じです。");
		}
		return super.visit(node);
	}

}
