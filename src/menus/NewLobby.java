package menus;

import edu.ufp.inf.sd.rmi.project.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import engine.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import engine.Game;


public class NewLobby implements ActionListener, KeyListener {
    public JButton SmallVs = new JButton("2 Players");
    public JButton FourCorners = new JButton("4 Players");

    public JButton Return = new JButton("Return");


    public NewLobby() {
        Point size = MenuHandler.PrepMenu(400, 280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
    }

    private void SetBounds(Point size) {
        SmallVs.setBounds(size.x, size.y + 10, 100, 32);
        FourCorners.setBounds(size.x, size.y + 10 + 38, 100, 32);
        Return.setBounds(size.x, size.y + 10 + 38 * 3, 100, 32);
    }

    private void AddGui() {
        Game.gui.add(SmallVs);
        Game.gui.add(FourCorners);
        Game.gui.add(Return);
    }

    private void AddListeners() {
        SmallVs.addActionListener(this);
        FourCorners.addActionListener(this);
        Return.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == SmallVs) {
            try {
                Game.lobby = Game.session.createLobby(LobbyMapEnum.SmallVs, Game.session);
                int index = Game.session.getLobbies().indexOf(Game.lobby);
                Game.observer = new ObserverImpl(Game.lobby, Game.username, Game.game);
                Game.lobby.attach(Game.observer);
                new PlayerSelectionMP(index);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == FourCorners) {
            try {
                Game.lobby = Game.session.createLobby(LobbyMapEnum.FourCorners, Game.session);
                int index = Game.session.getLobbies().indexOf(Game.lobby);
                Game.observer = new ObserverImpl(Game.lobby, Game.username, Game.game);
                Game.lobby.attach(Game.observer);
                new PlayerSelectionMP(index);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == Return) {
            new Multiplayer();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
