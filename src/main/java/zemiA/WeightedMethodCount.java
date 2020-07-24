package zemiA;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.*;

public class WeightedMethodCount extends ASTVisitor {

	public ArrayList<Integer> CYCLO = new ArrayList<Integer>();
	private int If_count;
	private int Case_count;
	private int For_count;
	private int While_count;

	@Override
	public void endVisit(MethodDeclaration node) {
		CYCLO.add(If_count + Case_count + For_count + While_count + 1);
		If_count = 0;
		Case_count = 0;
		For_count = 0;
		While_count = 0;
		super.endVisit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		If_count++;
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		Case_count++;
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		For_count++;
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		While_count++;
		return super.visit(node);
	}

	public int getWMC() {
		int WMC = 0;

		for (int num : CYCLO) {
			WMC = WMC + num;
		}
		return WMC;
	}
}
