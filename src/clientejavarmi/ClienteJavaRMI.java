/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientejavarmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author allan
 */
public class ClienteJavaRMI {
    
    public static void main(String[] args) {
        try {
            Registry referenciaServicoNomes = LocateRegistry.getRegistry(); // padrao porta 1099
            InterfaceServ referencia_Servidor = (InterfaceServ) referenciaServicoNomes.lookup("Servidor2");
            CliImpl cli = new CliImpl(referencia_Servidor);

            referencia_Servidor.chamar("Cliente1", cli);
            
            
        } catch (RemoteException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes  -RemoteException"+ex);
        } catch (NotBoundException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes - NotBoundException"+ex);
        }

    }
    
}
