package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JButton btn_exit;
    private JPanel pnl_user_list;
    private JLabel lbl_welcome;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_add;
    private JLabel lbl_name;
    private JTextField fld_user_name;
    private JLabel lbl_user_username;
    private JTextField fld_user_username;
    private JLabel lbl_user_password;
    private JTextField fld_user_password;
    private JLabel lbl_user_type;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JLabel lbl_user_delete;
    private JTextField fld_user_delete_id;
    private JButton btn_user_delete;
    private JLabel lbl_search_user_name;
    private JTextField fld_search_user_name;
    private JTextField fld_search_user_username;
    private JLabel lbl_search_user_username;
    private JLabel lbl_search_user_type;
    private JComboBox cmb_search_user_type;
    private JButton btn_search_user;
    private JPanel pnl_patika_list;
    private JTable tbl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTextField fld_patika_name;
    private JButton btn_add_patika;
    private JPanel lbl_patika_name;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_ilst;
    private JLabel lbl_add_course_name;
    private JTextField fld_add_course_name;
    private JLabel lbl_add_course_language;
    private JTextField fld_add_course_language;
    private JLabel lbl_add_course_patika;
    private JComboBox cmb_add_course_patika;
    private JLabel lbl_add_course_user;
    private JComboBox cmb_add_course_user;
    private JButton btn_add_course;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        // Application Settings
        this.operator = operator;
        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin :  " + operator.getName() + " " + operator.getUserName());
        //# Application Settings

        // Model User List
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        //# Model User List

        // Get User ID Delete
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_delete_id.setText(selected_user_id);
            } catch (Exception exception) {

            }
        });
        //# Get User ID Delete

        // User Update
        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.isUpdate(user_id, user_name, user_username, user_password, user_type)) {
                    Helper.showMessage("done");
                }
                loadUserModel();
                loadCourseEducatorComboBox();
            }
        });
        //# User Update

        // User Add
        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)) {
                Helper.showMessage("fill");
            } else {
                String name = fld_user_name.getText();
                String username = fld_user_username.getText();
                String password = fld_user_password.getText();
                String type = cmb_user_type.getSelectedItem().toString();

                if (User.isAdd(name, username, password, type)) {
                    Helper.showMessage("done");
                    loadUserModel();
                    loadCourseEducatorComboBox();
                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                }
            }
        });
        //# User Add

        // User Delete
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_delete_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int user_id = Integer.parseInt(fld_user_delete_id.getText());
                    if (User.isDelete(user_id)) {
                        Helper.showMessage("done");
                        loadUserModel();
                        loadCourseEducatorComboBox();
                        loadCourseModel();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        //# User Delete

        // User Search
        btn_search_user.addActionListener(e -> {
            String name = fld_search_user_name.getText();
            String username = fld_search_user_username.getText();
            String type = cmb_search_user_type.getSelectedItem().toString();
            String query = User.searchQuery(name, username, type);
            ArrayList<User> searchUser = User.searchUserList(query);
            loadUserModel(searchUser);
        });
        //# User Search

        // Patika List
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_patika_list = {"ID", "Patika Adı"};

        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        loadPatikaModel();

        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selectedRow = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
        //# Patika List

        // Patika Update
        updateMenu.addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(selectId));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadCoursePatikaComboBox();
                    loadCourseModel();
                }
            });
        });
        //# Patika Update

        // Patika Delete
        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectId = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                if (Patika.isDelete(selectId)) {
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadCoursePatikaComboBox();
                    loadCourseModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        //# Patika Delete

        // Patika Add
        btn_add_patika.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.isAdd(fld_patika_name.getText())) {
                    Helper.showMessage("done");
                    fld_patika_name.setText(null);
                    loadPatikaModel();
                    loadCoursePatikaComboBox();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        //# Patika Add

        // Course List
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID", "Ders Adı", "Programlam Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();

        tbl_course_ilst.setModel(mdl_course_list);
        tbl_course_ilst.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_ilst.getTableHeader().setReorderingAllowed(false);

        loadCoursePatikaComboBox();
        loadCourseEducatorComboBox();
        //# Course List

        // Course Add
        btn_add_course.addActionListener(e -> {
            Item patikaItem = (Item) cmb_add_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_add_course_user.getSelectedItem();

            if (Helper.isFieldEmpty(fld_add_course_name) || Helper.isFieldEmpty(fld_add_course_language)) {
                Helper.showMessage("fill");
            } else {
                if (Course.isAdd(userItem.getKey(), patikaItem.getKey(), fld_add_course_name.getText(), fld_add_course_language.getText())){
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_add_course_name.setText(null);
                    fld_add_course_language.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }

        });
        //# Course Add


        btn_exit.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
    }

    public void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        for (Patika patika : Patika.getList()) {
            int i = 0;
            row_patika_list[i++] = patika.getId();
            row_patika_list[i++] = patika.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User user : list) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User user : User.getList()) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_ilst.getModel();
        clearModel.setRowCount(0);

        for (Course course : Course.getList()){
            int i = 0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getLanguage();
            row_course_list[i++] = course.getPatika().getName();
            row_course_list[i++] = course.getEducator().getName();

            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadCoursePatikaComboBox() {
        cmb_add_course_patika.removeAllItems();

        for (Patika patika : Patika.getList()) {
            cmb_add_course_patika.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    public void loadCourseEducatorComboBox() {
        cmb_add_course_user.removeAllItems();

        for (User user : User.getList()) {
            if (user.getType().equals("educator")) {
                cmb_add_course_user.addItem(new Item(user.getId(), user.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();

        Operator op = new Operator();
        op.setId(1);
        op.setName("Emree");
        op.setUserName("Ünaldıı");
        op.setPassword("123456");
        op.setType("operator");

        OperatorGUI operatorGUI = new OperatorGUI(op);
    }
}
