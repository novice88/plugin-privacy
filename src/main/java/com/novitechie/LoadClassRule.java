package com.novitechie;

public class LoadClassRule {
    public static void check(String name) throws Exception {
        if (name.startsWith("com.janetfilter")) {
            throw new ClassNotFoundException(name);
        }
    }
}
