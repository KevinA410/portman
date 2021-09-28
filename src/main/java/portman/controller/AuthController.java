package portman.controller;

import javax.swing.JTextField;

public class AuthController {
    public static void validateId(JTextField txt) {
        String numbers = "0123456789";
        String t = txt.getText();
        String out = "";

        if (t.length() == 0)
            return;

        for (int i = 0; i < t.length(); i++) {
            String last = String.valueOf(t.charAt(i));
            if (numbers.contains(last)) {
                out += last;
            }
        }

        if (out.length() > 6) {
            out = out.substring(0, 6);
        }

        txt.setText(out);
    }

    public static void validateName(JTextField txt) {
        String symbols = " abcdefghijklmnñopqrstuvwxyzáéíóú";
        String t = txt.getText();
        String out = "";

        if (t.length() == 0)
            return;

        for (int i = 0; i < t.length(); i++) {
            String last = String.valueOf(t.charAt(i));
            if (symbols.contains(last.toLowerCase())) {
                out += last;
            }
        }

        txt.setText(out);
    }
}
