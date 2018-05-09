/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author allan
 */
public class ClienteJavaRMI {
    public static Registry referenciaServicoNomes;
    public static InterfaceServ referencia_Servidor;
    
    public static void main(String[] args) {
        try {
            referenciaServicoNomes = LocateRegistry.getRegistry(); // padrao porta 1099
            referencia_Servidor = (InterfaceServ) referenciaServicoNomes.lookup("Servidor2");
            CliImpl cli = new CliImpl(referencia_Servidor);

            referencia_Servidor.chamar("Cliente1", cli);
            
            
        } catch (RemoteException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes  -RemoteException"+ex);
        } catch (NotBoundException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes - NotBoundException"+ex);
        }

    }
    
}
