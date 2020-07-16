package zemiA;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class NumberofProtectedMembers extends ASTVisitor{
	String superclass;
	org.eclipse.jdt.core.dom.Type superClass;
	@Override
	public boolean visit(TypeDeclaration node) {
		superClass = node.getSuperclassType();
		superclass = superClass.toString();
		System.out.println(superclass);
		return super.visit(node);
	}

	public static void main(final String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/java/zemiA/ZemiAMain.java"),
					StandardCharsets.ISO_8859_1);
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		final ASTParser parser = ASTParser.newParser(AST.JLS14);
		parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());

		CompilationUnit unit = null;
		try {
			unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		if (true) {
			System.out.println("ABC");
		}

		final ZemiAVisitor visitor = new ZemiAVisitor();
		unit.accept(visitor);
	}

	class Parent0801 {
	    public String ps1="親クラスのメンバ変数が参照されました。";
	    public Parent0801(){
	        System.out.println("親クラスのコンストラクタ（引数なし）が呼ばれました。");
	    }
	    public void pm() {
	        System.out.println("親クラスのメソッドが呼ばれました。");
	    }
	}

	class Child0801 extends Parent0801 { // Parentクラス（親クラス）を継承。
	    public String cs1="子クラスのメンバ変数が参照されました。";
	    public Child0801(){
	        System.out.println("自クラスのコンストラクタ（引数なし）が呼ばれました。");
	    }
	    public void cm() {
	        System.out.println("子クラスのメソッドが呼ばれました。");
	    }
	}

}
