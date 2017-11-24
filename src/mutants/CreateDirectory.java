package mutants;

import priority.SimpleTree;

import java.io.File;
import java.io.IOException;

public class CreateDirectory {

    public static void CreateDirectory(String parentDirectory,int number) throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\mutants\\" + parentDirectory;
        for (int i = 0; i < number; i++) {
            String target = path + "\\mutant" + String.valueOf(i);
            System.out.println(target);
            File file = new File(target);
            if (!file.exists())
                file.mkdirs();
        }

    }

    public static void main(String[] args) throws IOException {
        CreateDirectory("FineGrainedHeap",2);
    }

}
