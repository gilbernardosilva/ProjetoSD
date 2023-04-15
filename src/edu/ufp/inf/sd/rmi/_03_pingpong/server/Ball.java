package edu.ufp.inf.sd.rmi._03_pingpong.server;

import java.io.Serializable;


/**
 * <p>Title: Projecto SD</p>
 *
 * <p>Description: Projecto apoio aulas SD</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */
public class Ball implements Serializable {
    private int playerID;

    public Ball(int playerid) {
        this.playerID = playerid;
    }
    public int getPlayerID(){
        return this.playerID;
    }
}
