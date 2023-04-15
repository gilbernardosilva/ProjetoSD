package edu.ufp.inf.sd.rmi._03_pingpong.server;

import java.util.Random;

public class PingRunnable implements Runnable{

    @Override
    public void run() {
        int randVal;
        randVal = Math.abs(new Random().nextInt(99)+1);
        System.out.println(randVal);
    }
}
