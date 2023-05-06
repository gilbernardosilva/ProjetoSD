package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyStatusEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import engine.Game;


public class PlayerSelectionMP implements ActionListener {
    //Commander Selection

    JButton[] Next = {new JButton("Next"), new JButton("Next"), new JButton("Next"), new JButton("Next")};
    JButton Lock = new JButton("Lock");
    JLabel[] Name = {new JLabel("Andy"), new JLabel("Andy"), new JLabel("Andy"), new JLabel("Andy")};
    int[] plyer = {0, 0, 0, 0};

    //NPC Stuff
    JLabel[] ManOrMachine = {new JLabel("PLY"), new JLabel("NPC"), new JLabel("NPC"), new JLabel("NPC")};
    boolean[] npc = {false, false, false, false};

    //Other
    JButton Return = new JButton("Return");
    JButton StartMoney = new JButton("$ 100");
    int start = 100;
    JButton CityMoney = new JButton("$ 50");
    int city = 50;
    JButton ThunderbirdsAreGo = new JButton("Start");

    String mapname;

    int indexLobby;
    Point size;


    public PlayerSelectionMP(int indexLobby) throws RemoteException {
        Game.observer.setPlayerSelectionMP(this);
        int i = Game.lobby.getIndexObserver(Game.username);
        ManOrMachine[i].setText(Game.username);
        npc[i] = false;
        mapname = Game.lobby.getMapName().name();
        size = MenuHandler.PrepMenu(400, 200);
        System.out.println("Index é " + i);
        System.out.println(Game.lobby.getCurrentPlayers());
        System.out.println(Game.lobby.players().get(0).getUsername());
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(null, null, null, null));
        ids.set(i, i);
        ArrayList<String> usernames = new ArrayList<>(Arrays.asList(null, null, null, null));
        usernames.set(i, Game.username);
        State state = new State(ids, plyer, usernames);
        System.out.println(Game.displayC.get(plyer[i]).name);
        Game.lobby.setState(state, i);
        this.indexLobby = indexLobby;
        SetBounds(size, i);
        AddGui(i);
        AddListeners(i);

    }


    public void updatePlayerSelection(State state) {
        try {
            for (int i = 0; i < Game.lobby.getCurrentPlayers(); i++) {
                int id = state.getId().get(i);
                ManOrMachine[id].setText(state.getUsername().get(i));
                ManOrMachine[id].setBounds(size.x + 12 + 84 * id, size.y + 68, 58, 24);
                Game.gui.add(ManOrMachine[id]);
                Name[id].setBounds(size.x + 10 + 84 * id, size.y + 40, 64, 32);
                Game.gui.add(Name[id]);
                plyer[i] = state.getCharacter()[i];
                Name[id].setText(Game.displayC.get(plyer[i]).name);
            }
            int i = Game.lobby.getIndexObserver(Game.username);

            SetBounds(size, i);
            AddGui(i);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame(State state) {
        MenuHandler.CloseMenu();
        Game.btl.NewGame(mapname);
        Game.btl.AddCommanders(plyer, npc, 100, 50);
        Game.gui.InGameScreen();
    }

    private void SetBounds(Point size, int i) {
        Name[i].setBounds(size.x + 10 + 84 * i, size.y + 40, 64, 32);
        ManOrMachine[i].setBounds(size.x + 12 + 84 * i, size.y + 68, 58, 24);
        Next[i].setBounds(size.x + 10 + 84 * i, size.y + 100, 64, 32);
        Lock.setBounds(size.x + 10 + 84 * i, size.y + 10, 64, 32);
        ThunderbirdsAreGo.setBounds(size.x + 200, size.y + 170, 100, 24);
        Return.setBounds(size.x + 20, size.y + 170, 100, 24);
    }

    private void AddGui(int i) {
        Game.gui.add(ManOrMachine[i]);
        Game.gui.add(Name[i]);
        Game.gui.add(Next[i]);
        Game.gui.add(Lock);
        Return.addActionListener(this);
        try {
            if (Game.lobby.getIndexObserver(Game.username) == 0) {
                Game.gui.add(ThunderbirdsAreGo);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Game.gui.add(Return);
    }

    private void AddListeners(int i) {
        Next[i].addActionListener(this);
        Lock.addActionListener(this);
        ThunderbirdsAreGo.addActionListener(this);
        Return.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == Return) {
            try {
                if (Game.lobby != null) {
                    if (Game.lobby.getCurrentPlayers() == 1) {
                        Game.session.deleteLobby(Game.lobby.getID());
                    }
                    Game.lobby.detach(Game.observer);
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            Game.lobby = null;
            Game.observer = null;
            MenuHandler.CloseMenu();
            new Multiplayer();
        } else if (s == ThunderbirdsAreGo) {
            try {
                if (Game.lobby.getCurrentPlayers() == 4 && Game.lobby.getMapName() == LobbyMapEnum.FourCorners) {
                    MenuHandler.CloseMenu();
                    Game.btl.NewGame(mapname);
                    Game.btl.AddCommanders(plyer, npc, 100, 50);
                    Game.lobby.setLobbyStatus(LobbyStatusEnum.ONGOING);
                    Game.gui.InGameScreen();
                } else if (Game.lobby.getCurrentPlayers() == 2 && Game.lobby.getMapName() == LobbyMapEnum.SmallVs) {
                    MenuHandler.CloseMenu();
                    Game.btl.NewGame(mapname);
                    Game.btl.AddCommanders(plyer, npc, 100, 50);
                    Game.lobby.setLobbyStatus(LobbyStatusEnum.ONGOING);
                    System.out.println(Arrays.toString(plyer));
                    Game.gui.InGameScreen();
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else if (s == Lock) {
            try {
                int i = Game.lobby.getIndexObserver(Game.username);
                ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(null, null, null, null));
                ids.set(i, i);
                ArrayList<String> username = new ArrayList<>(Arrays.asList(null, null, null, null));
                username.set(i, Game.username);
                State state = new State(ids, plyer, username);
                Game.lobby.setState(state, i);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (s == Next[i]) {
                    Next[i].setEnabled(false);
                    System.out.println(Next[i].isEnabled());
                    plyer[i]++;
                    if (plyer[i] > Game.displayC.size() - 1) {
                        plyer[i] = 0;
                    }
                    Name[i].setText(Game.displayC.get(plyer[i]).name);
                }

            }

        }
        for (int i = 0; i < 4; i++) {
            Next[i].setEnabled(true);
        }

    }


}
