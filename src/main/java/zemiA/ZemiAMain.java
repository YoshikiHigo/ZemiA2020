package zemiA;

import FileLoader.FileLoader;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
		if (args[1] == null){
			System.out.println("please write folder path");
			System.exit(0);
		}

		FileLoader fileloader = FileLoader.GetInstance();

		if(!fileloader.Init(args[1])){
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

			final ZemiAVisitor visitor = new ZemiAVisitor();
			unit.accept(visitor);

			//ここから各機能のクラスを実行
		}
	}
}
