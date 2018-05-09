/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author allan
 */
public interface InterfaceServ extends Remote {
    
    public void chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException;
    
}
