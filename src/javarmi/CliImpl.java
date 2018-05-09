/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author allan
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
    
    private InterfaceServ refServidor;

    public CliImpl(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;
    }

    
    
    @Override
    public String echo(String umaStringQualquer) throws RemoteException{
        System.out.println(umaStringQualquer);
        return umaStringQualquer;
    }
    
    
    
}
