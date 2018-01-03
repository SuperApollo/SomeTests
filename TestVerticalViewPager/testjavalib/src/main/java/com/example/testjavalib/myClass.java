package com.example.testjavalib;

/**
 * 测试匿名内部类
 * 两种方式：1.抽象类
 * 2.接口
 * 具体应用为，直接new一个抽象类、接口同名的对象，实现具体的抽象方法，最本质关键的地方在于实现抽象方法，而没有
 * 去用一个子类去实现抽象方法，凡是这种new出来的时候实现抽象方法的都是匿名内部类，如handler，thread，runnable
 */
public class myClass {
    private interface TestInnerClass {
        void show();
    }

    private abstract static class TestInnerClass1 {
        abstract void show();
    }

    public static void main(String[] args) {
        TestInnerClass testInnerClass = new TestInnerClass() {
            @Override
            public void show() {
                System.out.println("interface implemented inner class");
            }
        };
        testInnerClass.show();
        TestInnerClass1 testInnerClass1 = new TestInnerClass1() {
            @Override
            void show() {
                System.out.println("abstract class implemented inner class");
            }
        };
        testInnerClass1.show();
    }
}
