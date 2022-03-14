package common;

import common.engine.Engine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    boolean register(RMIClientInterface client) throws RemoteException;

    void submitAction(RMIClientInterface client, Engine.ACTIONS actions) throws RemoteException;
    void resetAction(RMIClientInterface client) throws RemoteException;
}
