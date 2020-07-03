package FileLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;

public class FileLoader {

    // シングルトン化
    private static FileLoader instance;

    private FileLoader(){
        classPathTable = new HashMap<String, String>();
    }

    public static FileLoader getInstance(){
        if(instance == null){
            instance = new FileLoader();
        }

        return instance;
    }

    String projectPath;
    HashMap<String, String> classPathTable;

    // 調査対象のプロジェクトのパスの設定
    // もしも与えられたものがフォルダのパスでなかったらFalseを返す
    // 正しくパスが与えられていればTrueを返す
    public Boolean Init(String _projectPath){
        projectPath = _projectPath;

        File projectDir = new File(projectPath);
        return projectDir.isDirectory();
    }

    // 設定したプロジェクト以下の全ての*.javaファイルを検索し、
    // クラス名のリストを返す
    public List<String> GetAllClassNames(){
        String pattern = "*.java";
        List<String> retVal = new ArrayList<String>();
        Queue<File> dirs = new ArrayDeque<File>();
        File root = new File(projectPath);
        dirs.add(root);

        //再帰的に検索
        while(!dirs.isEmpty()){
            File dir = dirs.poll();
            File[] children = dir.listFiles();
            for (File f: children) {
                if(f.isDirectory()){
                    dirs.add(f);
                }else if(f.getName().matches((pattern))){
                    // .でファイル名を区切った手前の文字列をクラス名とする
                    String className = f.getName().split(".")[0];
                    retVal.add(className);
                    classPathTable.put(className, f.getAbsolutePath());
                }
            }
        }
        return retVal;
    }


}
