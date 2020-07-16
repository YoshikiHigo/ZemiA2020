package zemiA;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import FileLoader.FileLoader;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ZemiAMain {

	public static void main(final String[] args) {

		System.out.println("Started");

		final FileLoader fileLoader = FileLoader.GetInstance();
		fileLoader.Init("C:\\Users\\user\\ZemiATest2020\\src\\main\\java");
		List<String> classNames = fileLoader.GetAllClassNames();
		System.out.println("classes: " + classNames);

		final ASTParser parser = ASTParser.newParser(AST.JLS14);

		if (true) {
			System.out.println("ABC");
		}

		final ZemiAVisitor visitor = new ZemiAVisitor();
		final NumberOfMethod NOM = new NumberOfMethod();
		NOM.makeMethodTable();
		final BaseClassOverridingRatio BOvR = new BaseClassOverridingRatio();

		for (String className: classNames) {

			List<String> lines = fileLoader.GetJavaFile(className);
			parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());

			CompilationUnit unit = null;
			try {
				unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
			} catch (final Exception e) {
				System.err.println(e.getMessage());
				return;
			}

			unit.accept(visitor);
			unit.accept(NOM);
		}

		for (String className: classNames) {
			System.out.println(className + ":"+ NumberOfMethod.getMethodList(className));
		}

		for (String className: classNames) {

			List<String> lines = fileLoader.GetJavaFile(className);
			parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());

			CompilationUnit unit = null;
			try {
				unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
			} catch (final Exception e) {
				System.err.println(e.getMessage());
				return;
			}

			unit.accept(BOvR);
		}

		for (String className: classNames) {
			System.out.println(className + ":BOvR = " + BOvR.getBOvR(className));
		}
	}
}
