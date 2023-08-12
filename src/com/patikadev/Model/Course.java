package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int userId;
    private int patikaId;
    private String name;
    private String language;
    private Patika patika;
    private User educator;

    public Course(int id, int userId, int patikaId, String name, String language) {
        this.id = id;
        this.userId = userId;
        this.patikaId = patikaId;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patikaId);
        this.educator = User.getFetch(userId);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList = new ArrayList<>();
        Course course;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id, user_id, patika_id, name, language);
                courseList.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courseList;
    }

    public static ArrayList<Course> getListByUser(int userId) {
        ArrayList<Course> courseList = new ArrayList<>();
        Course course;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE user_id = " + userId);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id, user_id, patika_id, name, language);
                courseList.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courseList;
    }

    public static boolean isAdd(int user_id, int patika_id, String name, String language) {
        String addQuery = "INSERT INTO course (user_id, patika_id, name, language) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(addQuery);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, patika_id);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, language);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static boolean isDelete(int id) {
        String deleteQuery = "DELETE FROM course WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPatikaId() {
        return patikaId;
    }

    public void setPatikaId(int patikaId) {
        this.patikaId = patikaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
