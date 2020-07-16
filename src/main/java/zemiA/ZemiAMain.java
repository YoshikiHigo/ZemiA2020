package zemiA;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

class Metrics{
	int NProtM;
	//NProtM = getNProtM();
	double BUR;
	double BOvR;
	double AMW;
	int WMC;
	int NOM;
}

public class ZemiAMain {

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

		final NumberOfMethod NOM = new NumberOfMethod();
		unit.accept(NOM);
	}
}
