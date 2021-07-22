package com.k4rnaj1k;

public class AppProperties {
    @PropertyKey("token")
    public long token;
    @PropertyKey("user")
    public String username;

    @Override
    public String toString() {
        return "com.k4rnaj1k.AppProperties{" +
                "token=" + token +
                ", username='" + username + '\'' +
                '}';
    }
}
