package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.线程安全原子操作.ABA;


public class Test {

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(new Node("A"));
        stack.push(new Node("B"));

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(stack.pop(3).item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            Node A = null;
            try {
                A = stack.pop(0);
                System.out.println(stack.pop(0).item);
                stack.push(new Node("D"));
                stack.push(new Node("C"));
                stack.push(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
    }

}
