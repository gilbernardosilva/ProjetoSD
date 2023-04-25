package menus;

import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Offline implements ActionListener {


    public JButton New = new JButton("New");
    public JButton Return = new JButton("Return");
    public JButton Exit = new JButton("Exit");

    //Map list
    public JList maps_list = new JList();
    DefaultListModel maps_model = new DefaultListModel();

    public Offline() {
        Point size = MenuHandler.PrepMenu(400, 280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
        MapList(size);
    }

    private void SetBounds(Point size) {
        New.setBounds(size.x, size.y + 10, 100, 32);
        Return.setBounds(size.x, size.y + 10 + 38, 100, 32);
        Exit.setBounds(size.x, size.y + 10 + 38 * 2, 100, 32);
    }

    private void AddGui() {
        Game.gui.add(New);
        Game.gui.add(Return);
        Game.gui.add(Exit);
    }

    private void MapList(Point size) {
        maps_model = Game.finder.GrabMaps();
        JScrollPane maps_pane = new JScrollPane(maps_list = new JList(maps_model));
        maps_pane.setBounds(size.x+220, size.y+10, 140, 260);//220,10
        Game.gui.add(maps_pane);
        maps_list.setBounds(0, 0, 140, 260);
        maps_list.setSelectedIndex(0);
    }

    private void AddListeners() {
        New.addActionListener(this);
        Return.addActionListener(this);
        Exit.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == New) {
            new PlayerSelection(maps_list.getSelectedValue() + "");
        } else if (s == Return) {
            new StartMenu();
        } else if (s == Exit) {
            System.exit(0);
        }
    }

}

