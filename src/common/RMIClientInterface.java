package common;

import common.engine.Engine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    String getUserName() throws RemoteException;

    void notifyCanPlay(boolean bool) throws RemoteException;

    void notifyCanEvaluateWinner(Engine.ACTIONS opponentAction) throws RemoteException;
}
