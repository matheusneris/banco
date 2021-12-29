import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Variáveis para navegar no menu
        int acao = -1;
        int subAcao;

        //Criando scanner para receber infos do usuário
        Scanner s = new Scanner(System.in);
        Banco meuBanco = new Banco();

        while (acao != 0){
            System.out.println("========= MENU =========");
            System.out.println("Digite o número do que deseja fazer:\n");
            System.out.println("1 - Criar conta\n" +
                    "2 - Fazer depósito\n" +
                    "3 - Consultar saldo\n" +
                    "4 - Fazer saque\n" +
                    "5 - Fazer transferência\n" +
                    "6 - Ver lista de contas\n" +
                    "0 - Sair do programa");
            System.out.println("========================");
            try {
                acao = s.nextInt();
            }catch (Exception exception){
                System.out.println("Digite apenas um número!");
                acao = -1;
            }

            //Resetando scanner para receber próxima informação
            s = new Scanner(System.in);

            if(acao == 1){
                //Inicializando variável para entrar no while
                subAcao = -1;
                while (subAcao != 0){
                    System.out.println("========= QUAL O TIPO DE CONTA DESEJA CRIAR? =========");
                    System.out.println("1 - Conta corrente\n" +
                            "2 - Conta Poupança\n" +
                            "0 - Voltar ao menu anterior");
                    System.out.println("======================================================");

                    try{
                        subAcao = s.nextInt();
                        s = new Scanner(System.in);
                    }catch (Exception exception){
                        System.out.println("Digite apenas um número!");
                        acao = -1;
                        s = new Scanner(System.in);
                    }


                    if(subAcao == 1){
                        String senha;
                        System.out.print("Informe seu nome: ");
                        //Criando cliente, necessário para criar a conta
                        Cliente cliente = new Cliente(s.next());
                        s = new Scanner(System.in);
                        //Criando senha, necessário para criar conta
                        System.out.print("Crie uma senha: ");
                        senha = s.next();
                        s = new Scanner(System.in);

                        //Criando uma conta e adicionando ela na lista de contas do banco
                        meuBanco.contas.add(new ContaCorrente(cliente, senha));

                        //Voltando para o menu principal
                        subAcao = 0;
                    }else if(subAcao == 2){
                        String senha;
                        System.out.print("Informe seu nome: ");
                        //Criando cliente, necessário para criar a conta
                        Cliente cliente = new Cliente(s.next());
                        s = new Scanner(System.in);
                        //Criando senha, necessário para criar conta
                        System.out.print("Crie uma senha: ");
                        senha = s.next();
                        s = new Scanner(System.in);

                        //Criando uma conta e adicionando ela na lista de contas do banco
                        meuBanco.contas.add(new ContaPoupanca(cliente, senha));

                        //Voltando para o menu principal
                        subAcao = 0;
                    }
                }

            }else if(acao == 2){
                int indiceContaDestino;
                int contaDestino;
                double valor;
                System.out.print("DIGITE O NÚMERO DA CONTA: ");
                try{
                    contaDestino = s.nextInt();
                    s = new Scanner(System.in);
                    indiceContaDestino = meuBanco.acharConta(contaDestino);

                    if(indiceContaDestino == -1){
                        System.out.println("Número de conta inválida! Digite um número inteiro! Lembre-se que é possível consultar a lista de contas.");
                        acao = -1;
                    }else {
                        System.out.print("DIGITE O VALOR A SER DEPOSITADO: ");
                        valor = s.nextDouble();
                        s = new Scanner(System.in);
                        meuBanco.fazerDeposito(indiceContaDestino, valor);
                        acao = -1;
                    }
                }catch (Exception exception){
                    System.out.println("Digite apenas números para a informar a conta e o valor!");
                    acao = -1;
                    s = new Scanner(System.in);
                }

            }else if(acao == 3){
                int contaPesquisada;
                int indiceContaPesquisada;
                String senha;
                System.out.print("DIGITE O NÚMERO DA CONTA: ");
                try{
                    contaPesquisada = s.nextInt();
                    s = new Scanner(System.in);

                    indiceContaPesquisada = meuBanco.acharConta(contaPesquisada);
                    if(indiceContaPesquisada == -1){
                        System.out.println("Número de conta inválida! Digite um número inteiro! Lembre-se que é possível consultar a lista de contas.");
                        acao = -1;
                    }else {
                        System.out.print("INFORME SUA SENHA: ");
                        senha = s.next();
                        s = new Scanner(System.in);
                        if(meuBanco.contas.get(indiceContaPesquisada).conferirSenha(senha)) {
                            if(meuBanco.contas.get(indiceContaPesquisada).getTipoConta().equals("Conta Corrente")){
                                System.out.println("==== Extrato da Conta Corrente ====");
                                System.out.println(meuBanco.contas.get(indiceContaPesquisada).getDados());
                                System.out.println("==================================");

                                acao = -1;
                            }else {
                                System.out.println("\n==== Extrato da Conta Poupança ====");
                                System.out.print(meuBanco.contas.get(indiceContaPesquisada).getDadosPoupanca());
                                System.out.println("===================================\n");

                                acao = -1;
                            }
                        }else {
                            System.out.println("Senha informada não está correta, tente novamente! Lembre-se de diferenciar maiúsculas e minúsculas!");
                            acao = -1;
                        }
                    }
                }catch (Exception exception){
                    System.out.println("Digite apenas numeros inteiros para informar a conta");
                    acao = -1;
                    s = new Scanner(System.in);
                }

            }else if(acao == 4){
                int contaSaque;
                int indiceContaSaque;
                String senha;
                double valor;
                System.out.print("DIGITE O NÚMERO DA CONTA: ");
                try{
                    contaSaque = s.nextInt();
                    s = new Scanner(System.in);

                    indiceContaSaque = meuBanco.acharConta(contaSaque);
                    if(indiceContaSaque == -1){
                        System.out.println("Número de conta inválida! Digite um número inteiro! Lembre-se que é possível consultar a lista de contas.");
                        acao = -1;
                    }else {
                        System.out.print("INFORME SUA SENHA: ");
                        senha = s.next();
                        s = new Scanner(System.in);
                        if(meuBanco.contas.get(indiceContaSaque).conferirSenha(senha)) {
                            System.out.print("DIGITE O VALOR A SER SACADO: ");
                            valor = s.nextDouble();
                            s = new Scanner(System.in);
                            meuBanco.fazerSaque(indiceContaSaque, valor);
                            acao = -1;
                        }else {
                            System.out.println("Senha informada não está correta, tente novamente! Lembre-se de diferenciar maiúsculas e minúsculas!");
                            acao = -1;
                        }
                    }
                }catch (Exception exception){
                    System.out.println("Digite apenas números para informar a conta e o valor do saque!");
                    acao = -1;
                    s = new Scanner(System.in);
                }

            }else if(acao == 5){
                int contaOrigem;
                int contaDestino;
                int indiceContaOrigem;
                int indiceContaDestino;
                String senha;
                double valor;
                System.out.print("QUAL O NÚMERO DA SUA CONTA: ");
                try{
                    contaOrigem = s.nextInt();
                    s = new Scanner(System.in);
                    indiceContaOrigem = meuBanco.acharConta(contaOrigem);
                    if(indiceContaOrigem == -1){
                        System.out.println("Número de conta inválida! Digite um número inteiro! Lembre-se que é possível consultar a lista de contas.");
                        acao = -1;
                    }else {
                        System.out.print("QUAL O NÚMERO DA CONTA DESTINO: ");
                        contaDestino = s.nextInt();
                        s = new Scanner(System.in);
                        indiceContaDestino = meuBanco.acharConta(contaDestino);
                        if(indiceContaDestino == -1){
                            System.out.println("Número de conta inválida! Digite um número inteiro! Lembre-se que é possível consultar a lista de contas.");
                            acao = -1;
                        }else {
                            System.out.print("DIGITE O VALOR A SER TRANSFERIDO: ");
                            valor = s.nextDouble();
                            s = new Scanner(System.in);

                            System.out.print("INFORME SUA SENHA: ");
                            senha = s.next();
                            s = new Scanner(System.in);
                            if(meuBanco.contas.get(indiceContaOrigem).conferirSenha(senha)) {
                                meuBanco.fazerTransferencia(indiceContaOrigem, indiceContaDestino, valor);
                                acao = -1;
                            }else {
                                System.out.println("Senha informada não está correta, tente novamente! Lembre-se de diferenciar maiúsculas e minúsculas!");
                                acao = -1;
                            }
                        }
                    }
                }catch (Exception exception){
                    System.out.println("Digite apenas números para informar as contas e o valor da transferência!");
                    acao = -1;
                    s = new Scanner(System.in);
                }

            }else if(acao == 6){
                    meuBanco.getContas();
                    acao = -1;
            }
        }
    }
}
