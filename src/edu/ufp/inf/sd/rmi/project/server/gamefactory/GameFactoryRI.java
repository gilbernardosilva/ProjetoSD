package edu.ufp.inf.sd.rmi.project.server.gamefactory;

import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    GameSessionRI login(String username, String password) throws RemoteException;
    GameSessionRI register(String username, String password) throws RemoteException;

    }