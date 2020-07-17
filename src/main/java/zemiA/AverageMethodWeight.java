package zemiA;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AverageMethodWeight extends ASTVisitor {
    public ArrayList<Integer> CYCLO = new ArrayList<Integer>();
    private int Method_count;
    private int If_count;
    private int Case_count;
    private int Switch_count;

    @Override
    public boolean visit(MethodDeclaration node) {
        Method_count++;
//        System.out.println("Method:" + Method_count);
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
//        System.out.println("If:" + If_count);
        return super.visit(node);
    }

    @Override
    public boolean visit(SwitchStatement node) {
        Switch_count++;
//        System.out.println("Switch:" + Switch_count);
        return super.visit(node);
    }

    @Override
    public boolean visit(SwitchCase node) {
        Case_count++;
//        System.out.println("Case:" + Case_count);
        return super.visit(node);
    }

    public double getAMW(){
        int WMC=0;
        double AMW=0;

        for(int num : CYCLO){
            WMC = WMC + num;
        }

        AMW = (double)WMC/Method_count;

        return AMW;
    }
}