package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int screenCenterPoint(String axis, Dimension size) {
        int point;

        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
                break;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String data) {
        optionPageTR();
        String message;
        String title;

        switch (data) {
            case "fill":
                message = "Lütfen tüm alanları doldurunuz !";
                title = "Hata !";
                break;
            case "done":
                message = "İşlem Başarılı !";
                title = "Sonuç";
                break;
            case "error":
                message = "Bir hata oluştu !";
                title = "Hata !";
            default:
                message = data;
                title = "Mesaj !";
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String data) {
        optionPageTR();
        String message;
        String title = "Emin misin ?";

        switch (data) {
            case "sure":
                message = "Bu işlemi gerçekleştirmek istediğine emin misin ?";
                break;
            default:
                message = data;
                break;
        }

        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }
}
