/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author allan
 * @author wagner
 * 
 */
public interface InterfaceServ extends Remote {
    
    public boolean procuraArquivo(String nomeArquivo) throws RemoteException; // verifica se arquivo existe no servidor
    public String[] downloadArquivo(String nomeArquivo) throws RemoteException; // baixa arquivo do servidor e retorna null para arquivo inexistente
    public void listarArquivos() throws RemoteException; // lista arquivos no servidor
    public void uploadArquivo(String[] arquivo) throws RemoteException; // insere um arquivo no servidor e notifica quem tenha interesse nesse arquivo
    public void registrarInteresse(String arquivo, InterfaceCli refCliente, Date dataLimite) throws RemoteException; // registra interesse por arquivo no servidor
    public boolean cancelarInteresse(String nome, InterfaceCli refCliente) throws RemoteException; // cancela interesse por arquivo no servidor
    public List<String> listarInfoArquivos() throws RemoteException; // lista informacoes de aruivos do servidor
    
}
