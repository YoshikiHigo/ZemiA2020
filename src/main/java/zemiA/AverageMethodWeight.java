package zemiA;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;

public class AverageMethodWeight extends ASTVisitor {
	public ArrayList<Integer> CYCLO = new ArrayList<Integer>();
	private int Method_count;
	private int If_count;
	private int Case_count;
	private int Switch_count;

	@Override
	public boolean visit(MethodDeclaration node) {
		Method_count++;
		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		CYCLO.add(If_count + (Case_count - Switch_count) + 1);
		If_count = 0;
		Case_count = 0;
		Switch_count = 0;
		super.endVisit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		If_count++;
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		Switch_count++;
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		Case_count++;
		return super.visit(node);
	}

	public double getAMW() {
		int WMC = 0;
		double AMW = 0;

		for (int num : CYCLO) {
			WMC = WMC + num;
		}

		AMW = (double) WMC / Method_count;

		return AMW;
	}
}