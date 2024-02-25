package com.novitechie;

public class LoadClassRule {
    public static void check(String name) throws Exception {
        if (name.startsWith("com.janetfilter")) {
            System.out.println("----------------------"+name);
            throw new ClassNotFoundException(name);
        }
    }
}
