package zemiA;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;

public class MetricVisitor extends ASTVisitor {
    public ArrayList<Integer> CYCLO = new ArrayList<Integer>();
    private int Method_count;
    private int If_count;
    private int Case_count;
    private int Switch_count;

    @Override
    public boolean visit(MethodDeclaration node) {
        Method_count++;
        System.out.println("Method:" + Method_count);
        return super.visit(node);
    }

    @Override
    public void endVisit(MethodDeclaration node) {
        CYCLO.add(If_count + (Case_count-Switch_count) + 1);
        If_count=0;
        Case_count=0;
        Switch_count=0;
        super.endVisit(node);
    }

    @Override
    public boolean visit(IfStatement node) {
        If_count++;
        System.out.println("If:" + If_count);
        return super.visit(node);
    }

    @Override
    public boolean visit(SwitchStatement node) {
        Switch_count++;
        System.out.println("Switch:" + Switch_count);
        return super.visit(node);
    }

    @Override
    public boolean visit(SwitchCase node) {
        Case_count++;
        System.out.println("Case:" + Case_count);
        return super.visit(node);
    }

    public int getMethod_Count(){
        return Method_count;
    }
}
