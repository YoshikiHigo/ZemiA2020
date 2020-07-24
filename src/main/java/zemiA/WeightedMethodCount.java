package zemiA;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WeightedMethodCount extends ASTVisitor {
    public ArrayList<Integer> CYCLO = new ArrayList<Integer>();
    private int If_count;
    private int Case_count;
    private int For_count;
    private int While_count;

    @Override
    public void endVisit(MethodDeclaration node) {
        CYCLO.add(If_count + (Case_count) + 1);
        If_count=0;
        Case_count=0;
        super.endVisit(node);
    }

    @Override
    public boolean visit(IfStatement node) {
        If_count++;
        System.out.println("If:" + If_count);
        return super.visit(node);
    }

    @Override
    public boolean visit(SwitchCase node) {
        Case_count++;
        System.out.println("Case:" + Case_count);
        return super.visit(node);
    }

    public int getWMC(){
        int WMC=0;

        for(int num : CYCLO){
            WMC = WMC + num;
        }
        return WMC;
    }
}
