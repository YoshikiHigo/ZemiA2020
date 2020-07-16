package zemiA;

import FileLoader.FileLoader;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NumberOfMethod extends ASTVisitor {

	private static HashMap <String, List<String>> methodTable;
	private String className;
	private List<String> methodList;

	public void makeMethodTable(){
		methodTable = new HashMap<String, List<String>>();
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO auto-generated method stub
		className = node.getName().toString();
		methodList = new ArrayList<String>();
		methodTable.put(className, methodList);

		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO auto-generated method stub
		SimpleName methodName = node.getName();
		methodList = methodTable.get(className);
		methodList.add(methodName.getIdentifier());

		return super.visit(node);
	}

	public static List<String> getMethodList(String className){
		if (className == null || !methodTable.containsKey(className)){
			return Collections.emptyList();
		}else{
			return methodTable.get(className);
		}
	}

	public static int getNOM(String className){
		if (className == null || !methodTable.containsKey(className)){
			return 0;
		}else{
			return methodTable.get(className).size();
		}
	}
}
