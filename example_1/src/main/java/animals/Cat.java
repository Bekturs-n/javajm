package animals;

public class Cat implements Anim {
    int age;

    public Cat() {
    }

    public void setAge(int age) {
        if (age < 0 || age > 20) {
            this.age = 1;
        } else {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }

    public void golos() {
        System.out.println("Мяу");
    }
}
