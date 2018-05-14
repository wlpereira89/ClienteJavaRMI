/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author allan
 * @author Wagner
 * 
 */
public class ClienteJavaRMI {

    public static Registry referenciaServicoNomes;
    public static InterfaceServ referencia_Servidor;

    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Boolean permanecer = true;
        String[] arquivo;

        try {
            referenciaServicoNomes = LocateRegistry.getRegistry(); // padrao porta 1099
            referencia_Servidor = (InterfaceServ) referenciaServicoNomes.lookup("Servidor2");
            CliImpl cli = new CliImpl(referencia_Servidor);
            boolean mostrar = true;
            while (permanecer) {

                if (mostrar) {
                    System.out.println("\nDigite o que deseja fazer:");
                    System.out.println("1 - Escrever novo arquivo");
                    System.out.println("2 - Listar próprios arquivos");
                    System.out.println("3 - Baixar arquivo");
                    System.out.println("4 - Fazer upload próprio para o servidor");
                    System.out.println("5 - Escrever arquivo no servidor");
                    System.out.println("6 - Cancelar interesse em arquivo");
                    System.out.println("7 - Lista de arquivos do servidor");
                    System.out.println("0 - sair");
                    //mostrar = false;
                } else {
                    System.out.println("\nPrecione nova opção, para ver novamente o menu, digite menu");
                }

                String opp = in.readLine();
                String msg;
                String nomeArquivo;
                String conteudo;
                switch (opp) {
                    case "0":  // Sair
                        System.exit(0);
                        break;
                    case "1":   // Escrever novo arquivo
                        System.out.println("\nEscreva o nome do arquivo");
                        nomeArquivo = in.readLine();
                        System.out.println("\nEscreva o conteudo do arquivo");
                        conteudo = in.readLine();
                        if (cli.escreverArquivo(nomeArquivo, conteudo)) {
                            System.out.println("\nArquivo inserido com sucesso");
                        } else {
                            System.out.println("\nErro na inserção do arquivo");
                        }
                        break;
                    case "2": // Listar arquivos proprios
                        cli.listarArquivos();
                        break;
                    case "3": // Baixar arquivo
                        System.out.println("\nEscreva o nome do arquivo que deseja baixar");
                        nomeArquivo = in.readLine();
                        arquivo = cli.getRefServidor().downloadArquivo(nomeArquivo);
                        if (arquivo != null) {
                            if (cli.salvarArquivo(arquivo)) {
                                System.out.println("\nArquivo copiado com sucesso");
                            } else {
                                System.out.println("\nErro no salvamento do arquivo");
                            }
                        } else {
                            System.out.println("\nO arquivo não foi localizado no servidor, deseja registrar interesse (Y,n)");
                            switch (in.readLine()) {
                                case "n":
                                    break;
                                default:
                                    boolean condicao = true;
                                    String dias = null;
                                    while (condicao) {
                                        System.out.println("\nDigite o numero de dias a esperar:");
                                        dias = in.readLine();
                                        condicao = false;
                                        try {
                                            Long.parseLong(dias);
                                        } catch (NumberFormatException ex) {
                                            System.out.println("\nDigite apenas numeros inteiros positivos!");
                                            condicao = true;
                                        }
                                        if (!condicao) {
                                            if (Integer.parseInt(dias) < 0) {
                                                System.out.println("Digite apenas numeros inteiros positivos");
                                                condicao = true;
                                            }
                                        }
                                    }
                                    Calendar hoje = Calendar.getInstance();
                                    int ndias = Integer.parseInt(dias);
                                    hoje.add(Calendar.DAY_OF_MONTH, ndias);
                                    Date dataLimite = hoje.getTime();
                                    cli.getRefServidor().registrarInteresse(nomeArquivo, cli, dataLimite);
                            }
                        }
                        break;
                    case "4":  // Fazer upload para servidor
                        System.out.println("\nEscreva o nome do arquivo que deseja fazer upload");
                        nomeArquivo = in.readLine();
                        arquivo = cli.getArquivo(nomeArquivo);
                        if (arquivo != null) {
                            cli.getRefServidor().uploadArquivo(arquivo);
                            System.out.println("\nArquivo enviado com sucesso");
                        } else {
                            System.out.println("\nArquivo não encontrado");
                        }
                        break;
                    case "5":  // Escrever arquivo no servidor
                        System.out.println("\nEscreva o nome do arquivo para inserir no servidor");
                        nomeArquivo = in.readLine();
                        System.out.println("\nEscreva o conteudo do arquivo");
                        conteudo = in.readLine();
                        String[] arq = {nomeArquivo, conteudo};
                        cli.getRefServidor().uploadArquivo(arq);
                        System.out.println("\nArquivo enviado com sucesso");
                        break;
                    case "6":  // Cancelar interesse em arquivo
                        System.out.println("\nEscreva o nome do arquivo que não tem mais interesse");
                        nomeArquivo = in.readLine();
                        if (cli.getRefServidor().cancelarInteresse(nomeArquivo, cli)) {
                            System.out.println("\nInteresse cancelado com sucesso");
                        } else {
                            System.out.println("\nInteresse não localizado");
                        }
                        break;
                    case "7": // Lista de arquivos do servidor
                        List<String> infos = cli.getRefServidor().listarInfoArquivos();
                        System.out.println("\nDetalhes de arquivos no servidor:");
                        for (int i = 0; i < infos.size(); i++) {
                            System.out.println(infos.get(i));
                        }
                        break;
                    case "menu":
                        mostrar = true;
                        break;
                    default:
                        System.out.println("\nOpção inválida digite nova opção");
                        break;
                }
            }

        } catch (RemoteException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes  -RemoteException" + ex);
        } catch (NotBoundException ex) {
            System.out.println("Classe Cliente: Erro ao utilizar servico de nomes - NotBoundException" + ex);
        }
    }
}
