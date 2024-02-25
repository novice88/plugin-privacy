package com.novitechie;

public class StackTraceRule {
    public static boolean check() {
        System.out.println("------------------StackTraceRule");
        RuntimeException e = new RuntimeException();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            if (stackTraceElement.getFileName() == null) {
                return true;
            }
        }
        return false;
    }
}
