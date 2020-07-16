package zemiA;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import FileLoader.FileLoader;

public class NumberofProtectedMembers extends ASTVisitor{
	String superc;
	int NprotM = 0;
	org.eclipse.jdt.core.dom.Type superClass;
	@Override
	public boolean visit(TypeDeclaration node) {
		superClass = node.getSuperclassType();
		//System.out.println(node.getSuperclassType());
		if(superClass!=null) {
			FileLoader fileloader = FileLoader.GetInstance();
			List<String> lines;
			superc = superClass.toString();
			System.out.println(superc);
			lines = fileloader.GetJavaFile(superc);
			System.out.println(lines);
			final ASTParser parser2 = ASTParser.newParser(AST.JLS14);
			System.out.println(parser2);
			if(lines!=null) {
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
				NprotM=Protected.getNprotM();
			}
		}
		System.out.println(NprotM);
		return super.visit(node);
	}

	public int getNprotM() {
		return NprotM;
	}

	public static void main(final String[] args) {
//		if (args[1] == null){
//			System.out.println("please write folder path");
//			System.exit(0);
//		}

		Metrics metrics = new Metrics();
		FileLoader fileloader = FileLoader.GetInstance();

		if(!fileloader.Init("src/main/java/zemiA")){
			System.out.println("please write folder path");
			System.exit(0);
		}

		List<String> classNames = fileloader.GetAllClassNames();

		for (String className : classNames){
			List<String> lines = null;
			lines = fileloader.GetJavaFile(className);

			final ASTParser parser = ASTParser.newParser(AST.JLS14);
			parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());

			CompilationUnit unit = null;
			try {
				unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
			} catch (final Exception e) {
				System.err.println(e.getMessage());
				return;
			}

			final WeightedMethodCount wmc = new WeightedMethodCount();
			unit.accept(wmc);

			final AverageMethodWeight amw = new AverageMethodWeight();
			unit.accept(amw);

			final NumberofProtectedMembers nprotm = new NumberofProtectedMembers();
			unit.accept(nprotm);

			metrics.WMC = wmc.getWMC();
			metrics.AMW = amw.getAMW();

			System.out.println(className + " of WMC = " + metrics.WMC);
			System.out.println(className + " of AMW = " + metrics.AMW);
		}
	}

}
