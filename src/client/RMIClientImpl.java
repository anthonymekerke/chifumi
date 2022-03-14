package client;

import common.RMIClientInterface;
import common.RMIServerInterface;
import common.engine.Engine;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientImpl implements RMIClientInterface {
    private RMIServerInterface server;
    private RMIClientInterface stub;

    private ShifumiClientApplication app;

    private String username;
    private BooleanProperty canPlay;
    private ObjectProperty<Engine.ACTIONS> toEvaluate;

    public RMIClientImpl(ShifumiClientApplication app, String username){
        this.app = app;

        this.username = this.toString();

        this.canPlay = new SimpleBooleanProperty(false);
        this.toEvaluate = new SimpleObjectProperty<>(Engine.ACTIONS.DEFAULT);

        try {

            this.stub = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

            String name = "rmi_server";
            Registry registry = LocateRegistry.getRegistry();
            this.server = (RMIServerInterface) registry.lookup(name);

            if(server.register(this)) {System.out.println("connected to server");}
            else {
                System.out.println("Server refused connection");
                System.exit(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BooleanProperty canPlayProperty(){
        return this.canPlay;
    }

    public ObjectProperty<Engine.ACTIONS> toEvaluateProperty(){
        return this.toEvaluate;
    }

    public void submitActionToServer(Engine.ACTIONS action) {
        try {
            server.submitAction(this, action);
        }catch(RemoteException e){
            System.out.println("Can't submit your action to server");
        }
    }

    public void resetActionOnServer(){
        toEvaluate.setValue(Engine.ACTIONS.DEFAULT);
        try{
            server.resetAction(this);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getUserName() throws RemoteException {
        return this.username;
    }

    @Override
    public void notifyCanPlay(boolean bool) throws RemoteException {
        this.canPlay.setValue(bool);
    }

    @Override
    public void notifyCanEvaluateWinner(Engine.ACTIONS opponentAction) throws RemoteException {
        this.toEvaluateProperty().setValue(opponentAction);
    }
}
