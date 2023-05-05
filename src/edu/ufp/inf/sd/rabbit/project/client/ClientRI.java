package edu.ufp.inf.sd.rabbit.project.client;


import java.rmi.Remote;

public interface ClientRI extends Remote {

    void close() throws Exception;
}


