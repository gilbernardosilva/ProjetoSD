package edu.ufp.inf.sd.rmi._04_digLib.server;

import java.rmi.Remote;

public interface DigLibSessionRI extends Remote {


    Book[] search(String title, String author);
    void logout();

}
