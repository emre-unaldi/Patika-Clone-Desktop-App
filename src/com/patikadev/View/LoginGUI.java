package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wTop;
    private JPanel wBottom;
    private JLabel lbl_login_title;
    private JTextField fld_login_username;
    private JFormattedTextField fld_login_password;
    private JLabel lbl_username;
    private JLabel lbl_login_password;
    private JButton btn_login;

    public LoginGUI() {
        add(wrapper);
        setSize(600, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_login_username) || Helper.isFieldEmpty(fld_login_password)) {
                Helper.showMessage("fill");
            } else {
                User user = User.getFetch(fld_login_username.getText(), fld_login_password.getText());
                if (user == null) {
                    Helper.showMessage("Kullanıcı Bulunamadı");
                } else {
                    switch (user.getType()) {
                        case "operator":
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI();
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI();
                            break;
                        default:
                            break;
                    }
                    dispose();
                }

            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
