package org.example;

import java.util.concurrent.TimeUnit;

public class Cat extends Animal {

    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public double goToWalk(long programStartTime) {
        double walkTime = getAge() * 0.25;  // Секунды
        try {
            TimeUnit.SECONDS.sleep((long) walkTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return (System.currentTimeMillis() - programStartTime) / 1000.0;
    }

    @Override
    public String toString() {
        return "Cat name = " + getName() + ", age = " + getAge();
    }
}
