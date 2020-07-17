package zemiA;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import FileLoader.FileLoader;

class Metrics{
	int NProtM;
	double BUR;
	double BOvR;
	double AMW;
	int WMC;
	int NOM;
}

public class ZemiAMain {

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

			metrics.WMC = wmc.getWMC();
			metrics.AMW = amw.getAMW();

			System.out.println(className + " of WMC = " + metrics.WMC);
			System.out.println(className + " of AMW = " + metrics.AMW);
		}
	}
}