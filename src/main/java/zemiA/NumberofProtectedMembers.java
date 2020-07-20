package zemiA;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import FileLoader.FileLoader;

public class NumberofProtectedMembers extends ASTVisitor {
	String superc;
	int NprotM = 0;
	org.eclipse.jdt.core.dom.Type superClass;

	@Override
	public boolean visit(TypeDeclaration node) {
		superClass = node.getSuperclassType();
		//System.out.println(node.getSuperclassType());
		if (superClass != null) {
			FileLoader fileloader = FileLoader.GetInstance();
			List<String> lines;
			superc = superClass.toString();
			lines = fileloader.GetJavaFile(superc);
			final ASTParser parser2 = ASTParser.newParser(AST.JLS14);
			if (lines != null) {
				parser2.setSource(String.join(System.lineSeparator(), lines).toCharArray());
				CompilationUnit unit = null;
				try {
					unit = (CompilationUnit) parser2.createAST(new NullProgressMonitor());
				} catch (final Exception e) {
					System.err.println(e.getMessage());
					System.exit(0);
				}
				final SearchProtected Protected = new SearchProtected();
				unit.accept(Protected);
				NprotM = Protected.getNprotM();
			}
		}
		return super.visit(node);
	}

	public int getNprotM() {
		return NprotM;
	}
}
