package edu.ufp.inf.sd.rmi.project.server.lobby;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.server.SubjectState;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface LobbyRI extends Remote {

    public List<ObserverRI> players() throws RemoteException;
    public UUID getID() throws RemoteException;
    public LobbyMapEnum getMapname() throws RemoteException;
    public void attach(ObserverRI obs) throws RemoteException;
    public void detach(ObserverRI obs) throws RemoteException;
    public void setSubjectState(SubjectState subjectState) throws RemoteException, InterruptedException;
}

