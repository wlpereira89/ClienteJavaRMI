/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author allan
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
    
    private InterfaceServ refServidor;
    private List<String[]> arquivos;
    
    public CliImpl(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;
        this.arquivos = new ArrayList<>();
    }

    
    
    @Override
    public String echo(String umaStringQualquer) throws RemoteException{
        System.out.println(umaStringQualquer);
        return umaStringQualquer;
    }

    @Override
    public boolean notificarInteresse(String nomeArquivo) throws RemoteException{
        System.out.println("O arquivo: "+nomeArquivo+" que você tinha interesse está disponivel você pode baixar ele agora");
        
        return true;
    }
    public boolean escreverArquivo(String nome, String conteudo){
        String[] nova  = {nome,conteudo};
        
        arquivos.add(nova);
        return true;
    }
    public boolean salvarArquivo(String[] arquivo){
        arquivos.add(arquivo);
        return true;
    }
    public void listarArquivos(){
        if (arquivos.size() > 0) {
            System.out.println("\nEste peer possui os aqrquivos");
            String[] arq;
            for (int i = 0; i < arquivos.size(); i++) {
                arq = arquivos.get(i);
                System.out.println(arq[0] + " - " + arq[1]);
            }
        } else {
            System.out.println("Este peer não possui arquivos");
        }
    }
    public String[] getArquivo(String nomeArquivo){
        String[] arq = null;
        if (arquivos.size() >= 1) {
            for (int i = 0; i < arquivos.size(); i++) {
                arq = arquivos.get(i);
                if (arq[0].equals(nomeArquivo)) {
                    return arq;
                }
            }
        }
        return null;
    }
    
}
