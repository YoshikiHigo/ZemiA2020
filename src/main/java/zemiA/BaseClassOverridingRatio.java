package zemiA;

import FileLoader.FileLoader;
import org.eclipse.jdt.core.dom.*;

import java.util.HashMap;
import java.util.List;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;

public class BaseClassOverridingRatio extends ASTVisitor {

	private String o= "Override";
	private List<String> superclassMethodList;
	private int BOvN;
	private int parentNOM;
	private HashMap<String, Double> BOvRTable = new HashMap<String, Double>();

	private Type superclass;
	private String superclassName;
	private String className;

	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO auto-generated method stub
		Type superclass = node.getSuperclassType();
		if(superclass != null){
			superclassName = superclass.toString();
			BOvN = 0;
		}else{
			superclassName = "null";
		}
		className = node.getName().toString();

		superclassMethodList = NumberOfMethod.getMethodList(superclassName);
		parentNOM = NumberOfMethod.getNOM(superclassName);
		
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO auto-generated method stub
		String methodName = node.getName().toString();
		if(superclassMethodList.contains(methodName)){
			BOvN++;
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node){
		BOvRTable.put(className, (double)BOvN/parentNOM);
	}

	public double getBOvR(String className){
		return (double)BOvRTable.get(className);
	}
}