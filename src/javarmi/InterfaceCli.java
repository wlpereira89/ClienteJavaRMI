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
 * @author wagner
 * 
 */
public interface InterfaceCli extends Remote {
    
    public boolean notificarInteresse(String nomeArquivo) throws RemoteException; //permite que seja notificado o cliente de um novo arquivo no servidor que ele tenha interesse
    
}
