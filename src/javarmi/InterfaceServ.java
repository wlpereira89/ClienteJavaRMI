/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author allan
 */
public interface InterfaceServ extends Remote {
    
    public void chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException;
    public boolean procuraArquivo(String nomeArquivo); //verifica se existe arquivo no servidor
    public String[] downloadArquivo(String nomeArquivo); //retorna null para arquivo inexistente
    public void listarArquivos(); //lista arquivos no servidor
    public void uploadArquivo(String[] arquivo) throws RemoteException; //insere um arquivo no servidor
    public void registrarInteresse(String arquivo, InterfaceCli refCliente, Date dataLimite); //permite ao cliente registrar interesse em arquivo especifico
    
}
