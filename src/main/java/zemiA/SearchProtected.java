package zemiA;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Modifier;

public class SearchProtected extends ASTVisitor {
	int NprotM = 0;

	@Override
	public boolean visit(Modifier node) {
		String modifier = node.toString();
		String Protected = "protected";
		if (modifier.equals(Protected)) {
			NprotM++;
		}
		return super.visit(node);
	}

	public int getNprotM() {
		return NprotM;
	}
}
