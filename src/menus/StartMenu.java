package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import engine.Game;

/**
 * This is the opening menu of the game.
 *
 * @author SergeDavid
 * @version 0.2
 */
public class StartMenu implements ActionListener {

    public JButton New = new JButton("New");
    public JButton Load = new JButton("Continue");
    public JButton Online = new JButton("Multiplayer");

    //Other
    public JButton Editor = new JButton("Editor");
    public JButton Credits = new JButton("Credits");
    public JButton Options = new JButton("Options");
    public JButton Exit = new JButton("Exit");


    public StartMenu() {
        Point size = MenuHandler.PrepMenu(400, 280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
    }

    private void SetBounds(Point size) {
        New.setBounds(size.x + 100, size.y, 200, 32);
        Load.setBounds(size.x + 100, size.y + 38, 200, 32);
        Online.setBounds(size.x + 100, size.y + 38 * 2, 200, 32);
        Editor.setBounds(size.x + 100, size.y + 38 * 3, 200, 32);
        Credits.setBounds(size.x + 100, size.y + 38 * 4, 200, 32);
        Options.setBounds(size.x + 100, size.y + 38 * 5, 200, 32);
        Exit.setBounds(size.x + 100, size.y + 38 * 6, 200, 32);
    }

    private void AddGui() {
        Game.gui.add(New);
        Game.gui.add(Load);
        Game.gui.add(Online);
        Game.gui.add(Editor);
        Game.gui.add(Credits);
        Game.gui.add(Options);
        Game.gui.add(Exit);
    }


    private void AddListeners() {
        New.addActionListener(this);
        Load.addActionListener(this);
        Online.addActionListener(this);
        Editor.addActionListener(this);
        Credits.addActionListener(this);
        Options.addActionListener(this);
        Exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == New) {
            new Offline();
        } else if (s == Load) {
            Game.save.LoadGame();
            MenuHandler.CloseMenu();
        } else if (s == Online) {
            new Multiplayer();
        } else if (s == Editor) {
            Game.edit.StartEditor(
                    "MapName",
                    16,
                    20);
            MenuHandler.CloseMenu();
        } else if (s == Credits) {
            new Credits();
        } else if (s == Options) {
            new Options();
        } else if (s == Exit) {
            System.exit(0);
        }
    }
}
