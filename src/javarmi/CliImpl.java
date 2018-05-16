/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author allan
 * @author wagner
 * Classe responsável pelos métodos disponíveis no cliente e pelos métodos invocados remotamente pelo servidor
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
    
    private InterfaceServ refServidor; // referencia do servidor
    private List<String[]> arquivos; // lista de arquivos disponiveis no cliente
    private String notificado;
    
    
    
    public CliImpl(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;
        this.arquivos = new ArrayList<>();
        this.notificado="";
    }
    public String pushNotify(){
        String not = this.notificado;
        this.notificado = "";
        return not;
    }
    /**
     * Método que retorna a referencia do servidor no serviço de nomes
     * @return IntefaceServ - referência do servidor
     */
    public InterfaceServ getRefServidor() {
        return refServidor;
    }
    
    /**
     * Método invocado pelo servidor para notificar clientes interessados em arquivos que não existiam anteriormente
     * @param nomeArquivo nome do arquivo que estava sendo aguardado e agora está disponível no servidor
     * @return boolean - true caso a notificação ocorra com sucesso
     * @throws RemoteException
     */
    @Override
    public boolean notificarInteresse(String nomeArquivo) throws RemoteException{
        System.out.println("\nO arquivo: "+nomeArquivo+" que você tinha interesse está disponivel para download agora! Se quiser baixar o arquivo precione 8");
        notificado = nomeArquivo;
        return true;
    }
    private void perguntarDown() throws RemoteException{        
        this.salvarArquivo(refServidor.downloadArquivo(notificado));
        notificado = "";
    }
    /**
     * Método responsável por criar um arquivo novo no cliente
     * @param nome nome do arquivo que será criado
     * @param conteudo conteúdo do arquivo que será criado
     * @return boolean - true caso criação do arquivo ocorra com sucesso
     */
    public boolean escreverArquivo(String nome, String conteudo){
        String[] nova  = {nome,conteudo};
        
        arquivos.add(nova);
        return true;
    }
    
    /**
     * Método responsável por salvar o arquivo na lista de arquivos do cliente
     * @param arquivo nome do arquivo a ser salvo
     * @return boolean - true caso operação ocorra com sucesso
     */
    public boolean salvarArquivo(String[] arquivo){
        arquivos.add(arquivo);
        return true;
    }
    
    /**
     * Método responsável por listar os arquivos do cliente
     */
    public void listarArquivos(){
        if (arquivos.size() > 0) {
            System.out.println("\nEste peer possui os aqrquivos:");
            String[] arq;
            for (int i = 0; i < arquivos.size(); i++) {
                arq = arquivos.get(i);
                System.out.println(arq[0] + " - " + arq[1]);
            }
        } else {
            System.out.println("\nEste peer não possui arquivos");
        }
    }
    
    /**
     * Método responsável por obter um arquivo da lista de arquivos do cliente
     * @param nomeArquivo  nome do arquivo a ser obtido
     * @return String - o arquivo a ser obtido (caso ele exista na lista de arquivos do cliente), ou null caso contrário
     */
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
