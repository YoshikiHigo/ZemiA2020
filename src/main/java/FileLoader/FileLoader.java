package FileLoader;
import org.eclipse.osgi.container.SystemModule;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;

public class FileLoader {
    // Make Singleton
    private static FileLoader instance;

    private FileLoader(){
        classPathTable = new HashMap<String, String>();
        contentsCache = new HashMap<String, List<String>>();
    }

    public static FileLoader GetInstance(){
        if(instance == null){
            instance = new FileLoader();
        }

        return instance;
    }

    String projectPath;
    HashMap<String, String> classPathTable;
    HashMap<String, List<String>> contentsCache;

    // Set Path of Target Project
    // If arg is illegal, return False.
    // If not, return True.
    public Boolean Init(String _projectPath){
        projectPath = _projectPath;

        File projectDir = new File(projectPath);
        return projectDir.isDirectory();
    }

    // Search all .java Files in the Project
    // and Return all Class Names
    public List<String> GetAllClassNames(){
        String pattern = ".*\\.java";
        List<String> retVal = new ArrayList<String>();
        Queue<File> dirs = new ArrayDeque<File>();
        File root = new File(projectPath);
        dirs.add(root);

        //Search Recursive
        while(!dirs.isEmpty()){
            File dir = dirs.poll();
            File[] children = dir.listFiles();
            for (File f: children) {
                if(f.isDirectory()){
                    dirs.add(f);
                }else if(f.getName().matches((pattern))){
                    System.out.println(f.getName());
                    String className = f.getName().split("\\.")[0];
                    retVal.add(className);
                    classPathTable.put(className, f.getAbsolutePath());
                }
            }
        }
        return retVal;
    }

    // Return File Content
    // If classname is illegal, return null
    public List<String> GetJavaFile(String className){
        String path = classPathTable.get(className);
        List<String> content = null;

        if(!contentsCache.containsKey(className)) {
            try {
                content = Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1);
            } catch (final Exception e) {
                return null;
            }
            contentsCache.put(className, content);
        }else{
            content = contentsCache.get(className);
        }

        return content;
    }

    //For test
    /*
    public static void main(final String[] args) {
        FileLoader f = FileLoader.GetInstance();
        f.Init("C:\\Users\\daich\\Documents\\SemiAJava\\ZemiA2020\\src\\main\\java");
        List<String> classes = f.GetAllClassNames();
        System.out.println(classes);

        List<String> zemiAContent = f.GetJavaFile("ZemiAMain");
        System.out.println(zemiAContent);
        // Cache Test
        System.out.println(f.GetJavaFile("ZemiAMain"));
    }
    */

}
