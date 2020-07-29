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
		boolean detection_flag = false;

		Metrics metrics = new Metrics();

		FileLoader fileloader = FileLoader.GetInstance();
		for (String arg : args){
			if(fileloader.Init(arg)){
				exit_flag = true;
				break;
			}
		}

		if (!exit_flag){
			System.out.println("please write folder path");
			System.exit(0);
		}

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

			if (DetectRefusedParentBequest(metrics)){
				detection_flag = true;
				DetectionPrint(metrics,className);
			}
		}

		if (!detection_flag) System.out.println("There is no class which Refused Parent Bequest");
	}

	private static boolean DetectRefusedParentBequest(Metrics metrics){
		return ((metrics.NProtM > 4 && metrics.BUR < 0.33) || metrics.BOvR < 0.33) && ((metrics.AMW > 2 || metrics.WMC > 14) && metrics.NOM > 7);
	}

	private static void DetectionPrint(Metrics metrics, String className){
		System.out.println("---------------");
		System.out.println(className + " Refused Parent Bequest");
		if (metrics.NProtM > 4){
			System.out.println("!!! " + className + " of NProtM = " + metrics.NProtM + " ( > 4)");
		} else {
			System.out.println(className + " of NProtM = " + metrics.NProtM);
		}
		if (metrics.BUR < 0.33){
			System.out.println("!!! " + className + " of BUR = " + metrics.BUR + " ( < 0.33)");
		} else {
			System.out.println(className + " of BUR = " + metrics.BUR);
		}
		if (metrics.BOvR < 0.33){
			System.out.println("!!! " + className + " of BOvR = " + metrics.BOvR + " ( < 0.33)");
		} else {
			System.out.println(className + " of BOvR = " + metrics.BOvR);
		}
		if (metrics.AMW > 2){
			System.out.println("!!! " + className + " of AMW = " + metrics.AMW + " ( > 2)");
		} else {
			System.out.println(className + " of AMW = " + metrics.AMW);
		}
		if (metrics.WMC > 14){
			System.out.println("!!! " + className + " of WMC = " + metrics.WMC + " ( > 14)");
		} else {
			System.out.println(className + " of WMC = " + metrics.WMC);
		}
		if (metrics.NOM > 7){
			System.out.println("!!! " + className + " of NOM = " + metrics.NOM + " ( > 7)");
		} else {
			System.out.println(className + " of NOM = " + metrics.NOM);
		}
	}
}