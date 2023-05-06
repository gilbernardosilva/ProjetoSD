package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import javax.swing.JButton;

import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import engine.Game;

/**
 * This is the pause menu that is pulled up when you press the Enter button in game.
 *
 * @author SergeDavid
 * @version 0.3
 */
public class Pause implements ActionListener {

    JButton Help = new JButton("Help");
    JButton Save = new JButton("Save");
    JButton Options = new JButton("Options");
    JButton EndTurn = new JButton("EndTurn");
    JButton Resume = new JButton("Resume");
    JButton Quit = new JButton("Quit");
    State gameState;

    public Pause() {
        Point size = MenuHandler.PrepMenu(120, 180);
        SetBounds(size);
        AddGui();
        AddListeners();
    }

    private void SetBounds(Point size) {
        Resume.setBounds(size.x + 10, size.y + 10, 100, 24);
        Save.setBounds(size.x + 10, size.y + 30 * 1 + 10, 100, 24);
        Options.setBounds(size.x + 10, size.y + 30 * 2 + 10, 100, 24);
        EndTurn.setBounds(size.x + 10, size.y + 30 * 3 + 10, 100, 24);
        Quit.setBounds(size.x + 10, size.y + 30 * 4 + 10, 100, 24);
    }

    private void AddGui() {
        Game.gui.add(Resume);
        Game.gui.add(Save);
        Game.gui.add(Options);
        Game.gui.add(EndTurn);
        Game.gui.add(Quit);
    }

    private void AddListeners() {
        Resume.addActionListener(this);
        Save.addActionListener(this);
        Options.addActionListener(this);
        EndTurn.addActionListener(this);
        Quit.addActionListener(this);
    }

    private void removeEndTurnListener() {
        EndTurn.removeActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == Quit) {
            MenuHandler.CloseMenu();
            Game.gui.LoginScreen();
        } else {
            try {
                if (s == EndTurn && Game.btl.currentplayer == Game.lobby.getIndexObserver(Game.username) && !Game.isRabbit) {
                    gameState = new State(0, 0, 0, "EndTurn");
                    MenuHandler.CloseMenu();
                    try {
                        Game.lobby.setGameState(gameState);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }else if(s == EndTurn && Game.btl.currentplayer == Game.playerID && Game.isRabbit){
                    String message = "endTurn";
                    try {
                        Game.channel.basicPublish("gameExchange", "server", null, message.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    MenuHandler.CloseMenu();
                }
                else if (s == Resume) {
                    MenuHandler.CloseMenu();
                } else if (s == Save) {
                    Game.save.SaveGame();
                } else if (s == Options) {
                    new Options();
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
}
