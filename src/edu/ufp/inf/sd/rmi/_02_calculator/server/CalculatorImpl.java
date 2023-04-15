package edu.ufp.inf.sd.rmi._02_calculator.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double add(List<Double> list) throws RemoteException {
        return list.stream().count();
    }

    @Override
    public double div(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteArithmeticException("Number not divisible by 0");
        }
        return a / b;
    }

}