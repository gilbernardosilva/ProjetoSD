package edu.ufp.inf.sd.rmi.Project.server.gamefactory;

import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
     public GameSessionRI login(String username,String password) throws RemoteException;

     public GameSessionRI register(String username,String password) throws RemoteException;


}
