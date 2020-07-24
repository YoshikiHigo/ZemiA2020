package zemiA;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
		boolean exit_flag = false;

		Metrics metrics = new Metrics();

		FileLoader fileloader = FileLoader.GetInstance();
		for (String arg : args){
			if(!fileloader.Init(arg)){
				System.out.println("please write folder path");
			} else {
				exit_flag = true;
				break;
			}
		}

		if (!exit_flag) System.exit(0);

		List<String> classNames = fileloader.GetAllClassNames();

		NumberOfMethod nom_make = new NumberOfMethod();
		nom_make.makeMethodTable();

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

			final NumberOfMethod nom = new NumberOfMethod();
			nom.getMethodList(className);
			unit.accept(nom);
		}

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

			final NumberOfMethod nom = new NumberOfMethod();
			unit.accept(nom);

			final BaseClassUsageRatio bur = new BaseClassUsageRatio();
			unit.accept(bur);

			final BaseClassOverridingRatio bovr = new BaseClassOverridingRatio();

			unit.accept(bovr);

			metrics.WMC = wmc.getWMC();
			metrics.AMW = amw.getAMW();
			metrics.NProtM = nprotm.getNprotM();
			metrics.BUR = bur.getBUR(metrics.NProtM);
			metrics.BOvR = bovr.getBOvR(className);
			metrics.NOM = NumberOfMethod.getNOM(className);

			System.out.println(className + " of WMC = " + metrics.WMC);
			System.out.println(className + " of AMW = " + metrics.AMW);
			System.out.println(className + " of NprotM = " + metrics.NProtM);
			System.out.println(className + " of BUR = " + metrics.BUR);
			System.out.println(className + " of BOvR = " + metrics.BOvR);
			System.out.println(className + " of NOM = " + metrics.NOM);
		}
	}
}