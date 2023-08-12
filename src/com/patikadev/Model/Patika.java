package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Patika() {
    }

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika patika;
        Statement statement = null;

        try {
            statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM patika");
            while (resultSet.next()) {
                patika = new Patika();
                patika.setId(resultSet.getInt("id"));
                patika.setName(resultSet.getString("name"));

                patikaList.add(patika);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patikaList;
    }

    public static boolean isAdd(String name){
        String addQuery = "INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(addQuery);
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean isUpdate(int id, String name){
        String updateQuery = "UPDATE patika SET name = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(updateQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean isDelete(int id) {
        String deleteQuery = "DELETE FROM patika WHERE id = ?";
        ArrayList<Course> courseList = Course.getList();

        for(Course course : courseList) {
            if (course.getPatika().getId() == id) {
                Course.isDelete(course.getId());
            }
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

    public static Patika getFetch(int id) {
        Patika patika = null;
        String query = "SELECT * FROM patika WHERE id  = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patika = new Patika(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return patika;
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
}

