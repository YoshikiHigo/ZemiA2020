package zemiA;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class SearchProtected extends ASTVisitor {
	int NprotM = 0;
	ArrayList<ArrayList<String>> ProtectedMethods = new ArrayList<ArrayList<String>>();

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
		ArrayList<String> name_parameter = new ArrayList<String>();
		int modifiers = node.getModifiers();
		if (Modifier.isProtected(modifiers)) {
			name_parameter.add(node.getName().toString());
			for (Object s : node.parameters()) {
				String[] type = s.toString().split(" ");
				name_parameter.add(type[0]);
			}
			ProtectedMethods.add(name_parameter);
		}
		return super.visit(node);
	}

	public int getNprotM() {
		return NprotM;
	}

	public ArrayList<ArrayList<String>> getProtectedMethods() {
		return ProtectedMethods;
	}
}
