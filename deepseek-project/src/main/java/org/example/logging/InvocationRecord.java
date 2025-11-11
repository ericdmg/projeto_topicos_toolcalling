package org.example.logging;

public class InvocationRecord {
    public final String method;
    public final String account;
    public final double value;

    public InvocationRecord(String method, String account, double value) {
        this.method = method;
        this.account = account;
        this.value = value;
    }

    @Override
    public String toString() {
        return method + "(" + account + ", " + value + ")";
    }
}
