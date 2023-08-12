package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;
public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_update_patika_name;
    private JTextField fld_update_patika_name;
    private JButton btn_update_patika;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_update_patika_name.setText(patika.getName());

        // Patika Update
        btn_update_patika.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_update_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.isUpdate(patika.getId(), fld_update_patika_name.getText())) {
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
        //# Patika Update
    }
}
