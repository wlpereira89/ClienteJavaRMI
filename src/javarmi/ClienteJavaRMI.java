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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author allan
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

            referencia_Servidor.chamar("Cliente1", cli);
            while (permanecer) {
                System.out.println("Digite o que deseja fazer:");

                System.out.println("1 - Escrever novo arquivo");
                System.out.println("2 - Listar próprios arquivos");
                System.out.println("3 - Baixar arquivo");
                System.out.println("4 - Fazer upload próprio para o servidor");
                System.out.println("5 - Escrever arquivo no servidor");
                System.out.println("0 - sair");

                String opp = in.readLine();
                String msg;
                String nome;
                String conteudo;
                switch (opp) {

                    // Sair
                    case "0":
                        System.exit(0);
                        break;
                    case "1":
                        System.out.println("Escreva o nome do arquivo");
                        nome = in.readLine();
                        System.out.println("Escreva o conteudo do arquivo");
                        conteudo = in.readLine();
                        if (cli.escreverArquivo(nome, conteudo)) {
                            System.out.println("Arquivo inserido com sucesso");
                        } else {
                            System.out.println("Erro na inserção do arquivo");
                        }
                        break;
                    case "2":
                        cli.listarArquivos();
                        break;
                    case "3":
                        System.out.println("Escreva o nome do arquivo");
                        nome = in.readLine();
                        arquivo = referencia_Servidor.downloadArquivo(nome);
                        if (arquivo != null) {
                            if (cli.salvarArquivo(arquivo)) {
                                System.out.println("Arquivo baixo com sucesso");
                            } else {
                                System.out.println("Erro no salvamento do arquivo");
                            }

                        } else {
                            System.out.println("O arquivo não foi localizado no servidor, deseja registrar interesse (Y,n)");
                            switch(in.readLine())
                            {
                                case "n":
                                    break;
                                default:
                                    System.out.println("Digite a data limite que você espera o arquivo em formato dd/mm/aaaa, em caso de deixar em branco ou preencher incorretamente será dado 1 mês como limite");
                                    String data = in.readLine();
                                    DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
                                    df.setLenient (false); 
                                    Date dataLimite = df.parse (data);
                                    referencia_Servidor.registrarInteresse(nome, cli, dataLimite);
                            }
                        }
                        break;
                    case "4":
                        System.out.println("Escreva o nome do arquivo");
                        nome = in.readLine();
                        arquivo = cli.getArquivo(nome);
                        if (arquivo!=null)
                        {
                            referencia_Servidor.uploadArquivo(arquivo);
                            System.out.println("Arquivo enviado com sucesso");
                        }
                        else{
                            System.out.println("Arquivo não encontrado");
                        }
                        break;
                    case "5":
                         System.out.println("Escreva o nome do arquivo");
                        nome = in.readLine();
                        System.out.println("Escreva o conteudo do arquivo");
                        conteudo = in.readLine();
                        String[] arq = {nome,conteudo};
                        referencia_Servidor.uploadArquivo(arq); 
                        System.out.println("Arquivo enviado com sucesso");
                        break;
                    default:
                        System.out.println("Opção inválida digite nova opção");
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
