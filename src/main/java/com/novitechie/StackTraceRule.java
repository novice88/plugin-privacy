package com.novitechie;

public class StackTraceRule {
    public static boolean check() {
        RuntimeException e = new RuntimeException();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            if (stackTraceElement.getFileName() == null) {
                return true;
            }
        }
        return false;
    }
}
