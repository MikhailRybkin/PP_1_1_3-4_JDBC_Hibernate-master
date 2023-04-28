package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Person", "1", (byte) 12);
        userService.saveUser("Person", "2", (byte) 21);
        userService.saveUser("Person", "3", (byte) 32);
        userService.saveUser("Person", "4", (byte) 23);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
