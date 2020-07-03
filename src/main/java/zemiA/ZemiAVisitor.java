package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

public class ZemiAVisitor extends ASTVisitor {

	int NOM = 0;
	int BOvR = 0;

	@Override
	public boolean visit(SimpleName node) {
		String override = "Override";
		System.out.println(node.toString());
		if(override.equals(node.toString())) {
			BOvR++;
			System.out.println("BOvR = " + BOvR);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		System.out.println(node.toString());
		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {//メソッドをvisitした後に呼び出される
		NOM++;
		System.out.println("NOM = " + NOM);
		// TODO 自動生成されたメソッド・スタブ
		super.endVisit(node);
	}


}
