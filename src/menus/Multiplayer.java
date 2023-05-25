package menus;

import edu.ufp.inf.sd.rmi.project.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import edu.ufp.inf.sd.rmi.project.variables.User;
import engine.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;


public class Multiplayer implements ActionListener, KeyListener {
    public JButton New = new JButton("New");
    public JButton Join = new JButton("Join");
    public JButton Return = new JButton("Return");
    public JButton Refresh = new JButton("Refresh");

    //Lobbies
    public JList<String> lobbies_list;
    public JScrollPane lobbies_pane;
    public DefaultListModel<String> lobbies_model = new DefaultListModel<>();

    public Multiplayer() {
        Point size = MenuHandler.PrepMenu(400, 280);
        MenuHandler.HideBackground();
        SetBounds(size);
        AddGui();
        AddListeners();
        MapList(size);
    }

    private void SetBounds(Point size) {
        New.setBounds(size.x, size.y + 10, 100, 32);
        Join.setBounds(size.x, size.y + 10 + 38, 100, 32);
        Refresh.setBounds(size.x, size.y + 10 + 38 * 2, 100, 32);
        Return.setBounds(size.x, size.y + 10 + 38 * 3, 100, 32);
    }

    private void AddGui() {
        Game.gui.add(New);
        Game.gui.add(Join);
        Game.gui.add(Refresh);
        Game.gui.add(Return);
    }

    private void AddListeners() {
        New.addActionListener(this);
        Join.addActionListener(this);
        Refresh.addActionListener(this);
        Return.addActionListener(this);
    }

    private void MapList(Point size) {
        lobbies_model = availableLobbies();
        lobbies_pane = new JScrollPane(lobbies_list = new JList<>(lobbies_model));
        lobbies_pane.setBounds(size.x + 220, size.y + 10, 140, 260);//220,10
        Game.gui.add(lobbies_pane);
        lobbies_list.setBounds(0, 0, 140, 260);
        lobbies_list.setSelectedIndex(0);
    }

    private DefaultListModel<String> availableLobbies() {
        DefaultListModel<String> lobbiesList = new DefaultListModel<>();
        try {
            for (LobbyRI lobby : Game.session.getLobbies()) {
                String lobbyInfo = lobby.getMapName() + " - " + lobby.getLobbyStatus() + " -" + lobby.getCurrentPlayers() + "/" + lobby.getMaxPlayers();
                lobbiesList.addElement(lobbyInfo);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return lobbiesList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == Refresh) {
            new Multiplayer();
        } else if (source == New) {
            new NewLobby();
        } else if (source == Join) {
            try {
                int index = this.lobbies_list.getSelectedIndex();
                Game.lobby = Game.session.getLobby(index);
                Game.observer = new ObserverImpl(Game.lobby, Game.username, Game.game);
                Game.lobby.attach(Game.observer);
                //Game.session.getUser().getToken().verify();
                new PlayerSelectionMP(index);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == Return) {
            new StartMenu();
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