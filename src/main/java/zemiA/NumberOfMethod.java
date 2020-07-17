package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class NumberOfMethod extends ASTVisitor {

	int NOM = 0;

	@Override
	public void endVisit(MethodDeclaration node) {
		// TODO 自動生成されたメソッド・スタブ
		NOM++;
		super.endVisit(node);
	}

	public int getNOM() {
		return NOM;
	}
}
