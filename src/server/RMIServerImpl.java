package server;

import common.RMIClientInterface;
import common.RMIServerInterface;
import common.engine.Engine;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImpl implements RMIServerInterface {
    private RMIClientInterface client1;
    private RMIClientInterface client2;

    private Engine.ACTIONS client1Action = Engine.ACTIONS.DEFAULT;
    private Engine.ACTIONS client2Action = Engine.ACTIONS.DEFAULT;

    public RMIServerImpl(){
        try{
            String name = "rmi_server";
            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);

            System.out.println("Server bound");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean register(RMIClientInterface client) throws RemoteException {
        if(client1 == null) {
            this.client1 = client;
        }
        else if(client2 == null){
            this.client2 = client;
        }
        else{
            System.out.println("third client try to connect -> connection refused \n third client has to wait");
            return false;
        }

        if(client1 != null && client2 != null){
            client1.notifyCanPlay(true);
            client2.notifyCanPlay(true);
        }

        System.out.println(client.getUserName() + " is registered");
        return true;
    }

    @Override
    public void submitAction(RMIClientInterface client, Engine.ACTIONS action) throws RemoteException{
        if(client.getUserName().equals(client1.getUserName())){
            client1Action = action;
        }
        if(client.getUserName().equals(client2.getUserName())){
            client2Action = action;
        }

        if(client1Action != Engine.ACTIONS.DEFAULT && client2Action != Engine.ACTIONS.DEFAULT){
            client1.notifyCanEvaluateWinner(client2Action);
            client2.notifyCanEvaluateWinner(client1Action);
        }
    }

    public void resetAction(RMIClientInterface client) throws RemoteException{
        if(client.getUserName().equals(client1.getUserName())){
            client1Action = Engine.ACTIONS.DEFAULT;
        }
        if(client.getUserName().equals(client2.getUserName())){
            client2Action = Engine.ACTIONS.DEFAULT;
        }
    }

    public static void main(String[] args){
        new RMIServerImpl();
    }
}
