package zemiA;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import FileLoader.FileLoader;

public class BaseClassUsageRatio extends ASTVisitor{

	long UsedProtected = 0;


	@Override
	public boolean visit(MethodDeclaration node) {
		int modifiers = node.getModifiers();
		//System.out.println(node.toString());
		System.out.println("Modifiers = " + modifiers);
		if (modifiers == 4) {   // "4" means "protected"
			System.out.println("You got a protected");
			UsedProtected++;
		}
		return super.visit(node);
	}

	protected void test() {
		System.out.println("This is just a test");
	}

	public long getBUR(int NprotM) {
		return UsedProtected/NprotM;
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

			final BaseClassUsageRatio bur = new BaseClassUsageRatio();
			unit.accept(bur);

			metrics.WMC = wmc.getWMC();
			metrics.AMW = amw.getAMW();

			System.out.println(className + " of WMC = " + metrics.WMC);
			System.out.println(className + " of AMW = " + metrics.AMW);
		}
	}
}
