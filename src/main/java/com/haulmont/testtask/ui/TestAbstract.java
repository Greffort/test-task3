package com.haulmont.testtask.ui;

public abstract class TestAbstract {
    public abstract void getAll();

    public void yyy() {
        System.out.println("aaaaa");
    }
}

class Test extends TestAbstract {

    @Override
    public void getAll() {

    }

    public void my() {
    }

    @Override
    public void yyy() {
        System.out.println("yyyy");
    }
}

class Main {
    public static void main(String[] args) {
        TestAbstract testAbstract = new Test();
        testAbstract.getAll();
        testAbstract.yyy();
    }
}