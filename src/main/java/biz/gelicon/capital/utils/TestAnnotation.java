package biz.gelicon.capital.utils;

public class TestAnnotation {
    @Proba(id=5, type = "asd")
    public int get() {
        System.out.println("get");
        return 1;
    }

}
