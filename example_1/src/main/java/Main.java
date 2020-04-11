import animals.Cat;

public class Main {

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.setAge(-100);
        getAmim(cat);
    }
    public static void getAmim(Cat anim){
        anim.golos();
        System.out.println(anim.getAge());
    }

}
