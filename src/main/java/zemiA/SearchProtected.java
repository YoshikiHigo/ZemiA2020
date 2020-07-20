package zemiA;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class SearchProtected extends ASTVisitor {
	int NprotM = 0;
	List<String> ProtectedMethods = new ArrayList<String>();

	@Override
	public boolean visit(Modifier node) {
		String modifier = node.toString();
		String Protected = "protected";
		if (modifier.equals(Protected)) {
			NprotM++;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		int modifiers = node.getModifiers();
		//System.out.println(node.toString());
		System.out.println("Modifiers = " + modifiers);
		if (Modifier.isProtected(modifiers)) {
			ProtectedMethods.add(node.getName().toString());
		}
		return super.visit(node);
	}

	public int getNprotM() {
		return NprotM;
	}

	public List<String> getProtectedMethods(){
		return ProtectedMethods;
	}
}
