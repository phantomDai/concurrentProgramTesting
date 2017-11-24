package set.mutants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 输入要测试的算法名称则自动在相应的目录里面加载变异体，存入mutants中
 */
public class MutantSimpleLinearSet {
    private List<Mutant> mutants ;
    private static String MUTANT_PACKAGE = "" ;
    public MutantSimpleLinearSet(String className) {
        String mutantsPath = System.getProperty("user.dir") + "\\src\\mutants\\" + className;
        MUTANT_PACKAGE = "mutants." + className;
        File file = new File(mutantsPath) ;
        Mutant mt ;
        mutants = new ArrayList<Mutant>();
        String[] mutantfiles = file.list();
        for (int i = 0; i < mutantfiles.length; i++) {
            String fullClassName = MUTANT_PACKAGE + "."
                    + mutantfiles[i] + "." + className;
            mt = new Mutant(mutantfiles[i],fullClassName);
            mutants.add(mt);
        }
    }
    public void add(Mutant mutant) {
        mutants.add(mutant);
    }

    public Mutant get(int index){
        Mutant mutant = null ;
        mutant = mutants.get(index);
        return mutant;
    }

    public boolean isEmpty(){
        return mutants.isEmpty();
    }

    public Mutant remove(int index){
        return mutants.remove(index);
    }

    public int numOfMutants(){
        return mutants.size();
    }

    public void printMutant(){
        for (int i = 0; i < mutants.size(); i++) {
            System.out.println(mutants.get(0).getCLASSNAME());
        }
    }

    public String getMutantFullName(int index){
        Mutant mutant = null ;
        mutant = mutants.get(index);
        return mutant.getCLASSNAME();
    }

    public static void main(String[] args) {
        MutantSimpleLinearSet ms = new MutantSimpleLinearSet("SimpleLinear");
//        mutantSimpleLinearSet.printMutant();
        System.out.println(ms.getMutantFullName(0));
    }


}
