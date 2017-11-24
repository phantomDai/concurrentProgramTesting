package temp;

public class Student {
    private int age ;
    private String name = "xiaoming" ;
    public Student(int myage,String myname) {
        this.age = myage ;
        this.name = myname ;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set(int age,String name){
        this.age = age ;
        this.name = name ;
    }

}
