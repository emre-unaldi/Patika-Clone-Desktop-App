package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String type;

    public User() {
    }

    ;

    public User(int id, String name, String userName, String password, String type) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        User user;

        Statement statement = null;
        try {
            statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User user;

        Statement statement = null;
        try {
            statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String name, String username, String type) {
        String searchQuery = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND username LIKE '%{{username}}%'";
        searchQuery = searchQuery.replace("{{name}}", name);
        searchQuery = searchQuery.replace("{{username}}", username);
        if (!type.isEmpty()) {
            searchQuery += " AND type = '{{type}}'";
            searchQuery = searchQuery.replace("{{type}}", type);
        }

        return searchQuery;
    }

    public static boolean isAdd(String name, String username, String password, String type) {
        String addQuery = "INSERT INTO user (name, username, password, type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(username);

        if (findUser != null) {
            Helper.showMessage("Bu kullanıcı adı kullanılıyor. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(addQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);

            int response = preparedStatement.executeUpdate();
            if (response == -1) {
                Helper.showMessage("error");
            }

            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static User getFetch(String username) {
        User user = null;
        String findQuery = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(findQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static User getFetch(String username, String password) {
        User user = null;
        String findQuery = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(findQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                switch (resultSet.getString("type")){
                    case "operator":
                        user = new Operator();
                        break;
                }
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static User getFetch(int id) {
        User user = null;
        String findQuery = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(findQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static boolean isDelete(int id) {
        String deleteQuery = "DELETE FROM user WHERE id = ?";
        ArrayList<Course> courseList = Course.getListByUser(id);

        for (Course course : courseList) {
            Course.isDelete(course.getId());
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static boolean isUpdate(int id, String name, String username, String password, String type) {
        String updateQuery = "UPDATE user SET name = ?, username = ?, password = ?, type = ? WHERE id = ?";
        User findUser = User.getFetch(username);

        if (findUser != null && findUser.getId() != id) {
            Helper.showMessage("Bu kullanıcı adı kullanılıyor. Lütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(updateQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String surname) {
        this.userName = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
