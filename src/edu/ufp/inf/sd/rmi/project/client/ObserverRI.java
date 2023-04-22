package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;

import java.rmi.Remote;

public interface ObserverRI extends Remote {
    String getUsername();
    LobbyRI getLobby();

}

