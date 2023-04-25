package menus;

import javax.swing.*;

import engine.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.awt.Point;

public class Login implements ActionListener, KeyListener {
    public JTextField username = new JTextField("Username");
    public JTextField password = new JTextField("Password");
    public JButton login = new JButton("Login");
    public JButton register = new JButton("Register");
    public JButton exit = new JButton("Exit");


    public Login() {
        Point size = MenuHandler.PrepMenu(400, 280);
        MenuHandler.HideBackground();
        setBounds(size);
        AddListeners();
        AddGui();
    }


    private void setBounds(Point size) {
        int x = size.x;
        int y = size.y;

        username.setBounds(x + 75, y + 50, 250, 32);
        password.setBounds(x + 75, y + 100, 250, 32);
        login.setBounds(x + 75, y + 160, 100, 32);
        register.setBounds(x + 225, y + 160, 100, 32);
        exit.setBounds(x + 160, y + 230, 80, 32);
    }

    private void AddGui() {
        Game.gui.add(username);
        Game.gui.add(password);
        Game.gui.add(login);
        Game.gui.add(register);
        Game.gui.add(exit);
    }

    private void AddListeners() {
        login.addActionListener(this);
        register.addActionListener(this);
        exit.addActionListener(this);
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Username")) {
                    username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setText("Username");
                }
            }
        });
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (password.getText().equals("Password")) {
                    password.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (password.getText().isEmpty()) {
                    password.setText("Password");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == login) {
            String name = username.getText();
            String pass = password.getText();
            try {
                Game.session = Game.stub.login(name, pass);
                Game.username = name;
                new StartMenu();
                JOptionPane.showMessageDialog(Game.gui, " Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == register) {
            String name = username.getText();
            String pass = password.getText();
            try {
                Game.session = Game.stub.register(name, pass);
                Game.username = name;
                new StartMenu();
                JOptionPane.showMessageDialog(Game.gui, "Register successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == exit) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Handle Enter key press
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}