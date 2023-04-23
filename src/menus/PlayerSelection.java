package menus;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;

import edu.ufp.inf.sd.rmi.project.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.server.SubjectState;
import engine.Game;

/**
 * This deals with player and battle options setup (might split it) such as npc, team, commander, starting money, turn money, fog, etc.
 *
 * @author SergeDavid
 * @version 0.2
 */
public class PlayerSelection implements ActionListener {
    //TODO: Scale with map size.
    //Commander Selection
    JButton[] Prev = {new JButton("Prev"), new JButton("Prev"), new JButton("Prev"), new JButton("Prev")};
    JButton[] Next = {new JButton("Next"), new JButton("Next"), new JButton("Next"), new JButton("Next")};
    JLabel[] Name = {new JLabel("Andy"), new JLabel("Andy"), new JLabel("Andy"), new JLabel("Andy")};
    int[] plyer = {0, 0, 0, 0};

    String username;
    SubjectState subjectState;
    ObserverRI observerRI;
    //NPC Stuff
    JLabel[] playerLabels = new JLabel[4];
    boolean[] npc = {false, true, true, true};

    //Other
    JButton Return = new JButton("Return");
    JButton StartMoney = new JButton("$ 100");
    int start = 100;
    JButton CityMoney = new JButton("$ 50");
    int city = 50;
    JButton ThunderbirdsAreGo = new JButton("Start");

    String mapname;

    public PlayerSelection(String map, ObserverRI observer) throws RemoteException, InterruptedException {
        this.observerRI = observer;
        this.username = observer.getUsername();
        for (int i = 0; i < 4; i++) {
            if (i == observer.getId()) {
                playerLabels[i] = new JLabel(observer.getUsername());
            } else {
                playerLabels[i] = new JLabel("NPC");
            }
        }
        mapname = map;
        Point size = MenuHandler.PrepMenu(400, 200);
        for (int i = 0; i < 4; i++) {
            Prev[i].addActionListener(this);
            Prev[i].setBounds(size.x + 10 + 84 * i, size.y + 10, 64, 32);
            Game.gui.add(Prev[i]);
            Next[i].addActionListener(this);
            Next[i].setBounds(size.x + 10 + 84 * i, size.y + 100, 64, 32);
            Game.gui.add(Next[i]);
            playerLabels[i].setBounds(size.x + 12 + 84 * i, size.y + 68, 58, 24);
            Game.gui.add(playerLabels[i]);
            Name[i].setBounds(size.x + 10 + 84 * i, size.y + 40, 64, 32);
            Game.gui.add(Name[i]);
        }
        if (observerRI.getId() == 0) {
            SetBounds(size);
            AddGui();
            AddListeners();
        } else {
            SetBoundsReturn(size);
            AddReturnGui();
            AddReturnListener();
        }
        SubjectState newState = new SubjectState(observer.getId());
        this.observerRI.setLastObserverState(newState);
        this.observerRI.getLobby().setSubjectState(this.observerRI.getLastObserverState());
    }

    public PlayerSelection(String map) {
        mapname = map;
        Point size = MenuHandler.PrepMenu(400, 200);
        for (int i = 0; i < 4; i++) {
            Prev[i].addActionListener(this);
            Prev[i].setBounds(size.x + 10 + 84 * i, size.y + 10, 64, 32);
            Game.gui.add(Prev[i]);
            Next[i].addActionListener(this);
            Next[i].setBounds(size.x + 10 + 84 * i, size.y + 100, 64, 32);
            Game.gui.add(Next[i]);
            playerLabels[i].setBounds(size.x + 12 + 84 * i, size.y + 68, 58, 24);
            Game.gui.add(playerLabels[i]);
            Name[i].setBounds(size.x + 10 + 84 * i, size.y + 40, 64, 32);
            Game.gui.add(Name[i]);
        }
        SetBounds(size);
        AddGui();
        AddListeners();
    }

    private void SetBounds(Point size) {
        ThunderbirdsAreGo.setBounds(size.x + 200, size.y + 170, 100, 24);
        Return.setBounds(size.x + 20, size.y + 170, 100, 24);
    }

    private void SetBoundsReturn(Point size) {
        Return.setBounds(size.x + 20, size.y + 170, 100, 24);

    }

    private void AddReturnGui() {
        Return.addActionListener(this);
        Game.gui.add(Return);

    }

    private void AddGui() {
        Return.addActionListener(this);
        Game.gui.add(ThunderbirdsAreGo);
        Game.gui.add(Return);
    }

    private void AddReturnListener() {
        Return.addActionListener(this);

    }

    private void AddListeners() {
        ThunderbirdsAreGo.addActionListener(this);
        Return.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == Return) {
            MenuHandler.CloseMenu();
            Game.gui.LoginScreen();
        } else if (s == ThunderbirdsAreGo) {
            MenuHandler.CloseMenu();
            Game.btl.NewGame(mapname);
            Game.btl.AddCommanders(plyer, npc, 100, 50);
            Game.gui.InGameScreen();
        }
        for (int i = 0; i < 4; i++) {
            if (s == Prev[i]) {
                plyer[i]--;
                if (plyer[i] < 0) {
                    plyer[i] = Game.displayC.size() - 1;
                }
                Name[i].setText(Game.displayC.get(plyer[i]).name);
                try {
                    SubjectState newState = new SubjectState(this.observerRI.getId(), Game.displayC.get(plyer[i]).name);
                    this.observerRI.setLastObserverState(newState);
                    this.observerRI.getLobby().setSubjectState(this.observerRI.getLastObserverState());
                } catch (RemoteException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (s == Next[i]) {
                plyer[i]++;
                if (plyer[i] > Game.displayC.size() - 1) {
                    plyer[i] = 0;
                }
                Name[i].setText(Game.displayC.get(plyer[i]).name);
                try {
                    SubjectState newState = new SubjectState(this.observerRI.getId(), Game.displayC.get(plyer[i]).name);
                    this.observerRI.setLastObserverState(newState);
                    this.observerRI.getLobby().setSubjectState(this.observerRI.getLastObserverState());
                } catch (RemoteException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }


}
