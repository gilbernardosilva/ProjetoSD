package edu.ufp.inf.sd.rmi.project.server.gamefactory;

import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.project.variables.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    GameSessionRI login(User user) throws RemoteException;
    GameSessionRI register(User user) throws RemoteException;
    User getUser(String username, String password) throws RemoteException;



}