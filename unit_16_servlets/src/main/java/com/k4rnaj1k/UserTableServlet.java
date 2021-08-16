package com.k4rnaj1k;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserTableServlet extends HttpServlet {
    private final Map<String,String> users = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        writer.write("<table border=\"1\"><tr><th>IP</th><th>User-Agent</th></tr>");
        String ip = req.getRemoteAddr();
        users.put(req.getRemoteAddr(), req.getHeader("User-Agent"));
        for (String userIp :
                users.keySet()) {
            String usersRow;
            if(Objects.equals(userIp, ip)) {
                usersRow = String.format("<tr><td><b>%s</b></td><td><b>%s</b></td></tr>", userIp, users.get(userIp));
            }else{
                usersRow = String.format("<tr><td>%s</td><td>%s</td></tr>", userIp, users.get(userIp));
            }
            writer.write(usersRow);
        }
        System.out.println(req.getRemoteAddr());
        writer.write("</table>");
    }
}
