package me.whiteship.inflearnthejavatest;

/**
 * Created by jojoldu@gmail.com on 2020-11-24
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
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
