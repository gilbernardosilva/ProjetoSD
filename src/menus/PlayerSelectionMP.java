package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;

import engine.Game;


public class PlayerSelectionMP implements ActionListener {
    //Commander Selection

    JButton[] Prev = {new JButton("Prev"),new JButton("Prev"),new JButton("Prev"),new JButton("Prev")};
    JButton[] Next = {new JButton("Next"),new JButton("Next"),new JButton("Next"),new JButton("Next")};
    JLabel[] Name = {new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy"),new JLabel("Andy")};
    int[] plyer = {0,0,0,0};

    //NPC Stuff
    JButton[] ManOrMachine = {new JButton("PLY"),new JButton("NPC"),new JButton("NPC"),new JButton("NPC")};
    boolean[] npc = {false,true,true,true};

    //Other
    JButton Return = new JButton("Return");
    JButton StartMoney = new JButton ("$ 100");int start = 100;
    JButton CityMoney = new JButton ("$ 50");int city = 50;
    JButton ThunderbirdsAreGo = new JButton ("Start");

    String mapname;

    int indexLobby;


    public PlayerSelectionMP(int indexLobby) throws RemoteException {
        mapname = Game.lobby.getMapName().name();
        Point size = MenuHandler.PrepMenu(400,200);
        int index = Game.lobby.getIndexObserver(Game.username);
        System.out.println("Index é " + index);
        System.out.println(Game.lobby.getCurrentPlayers());
        this.indexLobby= indexLobby;
        SetBounds(size);
        AddGui();
        AddListeners();
    }


    private void SetBounds(Point size) {
        ThunderbirdsAreGo.setBounds(size.x+200, size.y+170, 100, 24);
        Return.setBounds(size.x+20, size.y+170, 100, 24);
    }
    private void AddGui() {
        Return.addActionListener(this);
        Game.gui.add(ThunderbirdsAreGo);
        Game.gui.add(Return);
    }
    private void AddListeners() {
        ThunderbirdsAreGo.addActionListener(this);
        Return.addActionListener(this);
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == Return) {
            try {
                if(Game.lobby!= null) {
                    if (Game.lobby.getCurrentPlayers() == 1) {
                        Game.session.deleteLobby(Game.lobby.getID());
                    }
                    Game.lobby.detach(Game.observer);
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            Game.lobby= null;
            Game.observer = null;
            MenuHandler.CloseMenu();
            new Multiplayer();
        }
        else if(s == ThunderbirdsAreGo) {
            MenuHandler.CloseMenu();
            Game.btl.NewGame(mapname);
            Game.btl.AddCommanders(plyer, npc, 100, 50);
            Game.gui.InGameScreen();
        }
        for (int i = 0; i < 4; i++) {
            if (s == Prev[i]) {
                plyer[i]--;
                if (plyer[i]<0) {plyer[i]=Game.displayC.size()-1;}
                Name[i].setText(Game.displayC.get(plyer[i]).name);
            }
            else if (s == Next[i]) {
                plyer[i]++;
                if (plyer[i]>Game.displayC.size()-1) {plyer[i]=0;}
                Name[i].setText(Game.displayC.get(plyer[i]).name);
            }
            else if (s == ManOrMachine[i]) {
                npc[i]=!npc[i];
                if (npc[i]) {ManOrMachine[i].setText("NPC");}
                else {ManOrMachine[i].setText("PLY");}
            }
        }
    }
}
