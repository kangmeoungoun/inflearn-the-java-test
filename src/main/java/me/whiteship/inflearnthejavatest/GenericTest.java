package me.whiteship.inflearnthejavatest;


public class GenericTest<T extends Number> {
    private T t;

    public <T> void test(T t){
        System.out.println(t.getClass());;
    }
    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
