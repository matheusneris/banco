import java.text.DecimalFormat;

public class Conta extends Banco{
    private int agencia;
    private int numero;
    protected double saldo;
    private String titular;
    private String senha;
    //Tipo: Conta Corrente ou Conta Poupança
    private String tipoConta;
    //Variável para armazenar o rendimento bruto das contas poupanças ao longo do tempo
    private double rendimento;
    /*Variável que se incrementa a medida que o programa vai sendo usando para
    simular o passar do tempo (agregando remuneração de capital na conta poupança)
    sendo usado posteriormente para o cálculo de juros compostos dos ganhos da conta poupança*/
    private double contadorPoupanca = 0;
    //Variável para representar a taxa de remuneração de capital para a conta poupança
    private static final double rendimentoPoupanca = 0.06;

    private static final int agenciaPadrao = 1;
    private static int sequencial = 1;

    public Conta(Cliente cliente, String senha, String tipoConta) {
        //Agência com valor fixo (1)
        this.agencia = agenciaPadrao;
        //Número da conta é sequencial a medida que contas vão sendo criadas, começando em 1
        this.numero = sequencial++;
        //Atribuindo o nome do cliente à variável de titular da conta
        this.titular = cliente.getNome();
        this.senha = senha;
        this.tipoConta = tipoConta;
    }

    public void depositar(double valor){
        //Lidando com o caso de tentativa de depósito de valores negativos
        if (valor <= 0) {
            System.out.println("Só é possível depositar valores positivos!");
        }else {
            if (tipoConta.equals("Conta Poupança")){
                double saldoAntigo = saldo;
                saldo = saldo*Math.pow(1+rendimentoPoupanca,contadorPoupanca);
                rendimento += saldo - saldoAntigo;
                saldo += valor;
                System.out.println("Depósito efetuado com sucesso!");
                contadorPoupanca = 0;
            }else {
                saldo += valor;
                System.out.println("Depósito efetuado com sucesso!");
            }
        }
    }

    public void sacar (double valor) {
        //Lidando com o caso de tentativa de saque maior que o saldo da conta
        if (this.getSaldo() < valor) {
            System.out.println("Saldo insuficiente! Seu saldo é: "+getSaldo());
        } else {
            if (tipoConta.equals("Conta Poupança")){
                contadorPoupanca ++;
                double saldoAntigo = saldo;
                saldo = saldo*Math.pow(1+rendimentoPoupanca,contadorPoupanca);
                rendimento += saldo - saldoAntigo;
                saldo -= valor;
                System.out.println("Saque efetuado com sucesso!\nVocê sacou: R$" +valor+"\nSeu saldo novo é: "+getSaldo()+"\n");
                contadorPoupanca = 0;
            }else {
                saldo -= valor;
                System.out.println("Saque efetuado com sucesso!\nVocê sacou: R$" +valor+"\nSeu saldo novo é: "+getSaldo()+"\n");
            }
        }
    }

    //Método para transferir de uma conta para outra do banco
    public void transferir(Conta contaDestino, double valor) {
        //Lidando com o caso de tentar transferir algum valor maior que o saldo atual da conta
        if (this.getSaldo() < valor) {
            System.out.println("Saldo insuficiente para transferência! Seu saldo é: " + this.getSaldo() + "\n");
        } else {
            if (tipoConta.equals("Conta Poupança")){
                contaDestino.contadorPoupanca ++;
                this.contadorPoupanca ++;
                double saldoAntigoContaOrigem = saldo;
                double saldoAntigoContaDestino = contaDestino.saldo;
                this.saldo = saldo*Math.pow(1+rendimentoPoupanca, this.contadorPoupanca);
                contaDestino.saldo = contaDestino.saldo*Math.pow(1+rendimentoPoupanca, contaDestino.contadorPoupanca);
                rendimento += saldo - saldoAntigoContaOrigem;
                contaDestino.rendimento += contaDestino.saldo - saldoAntigoContaDestino;
                this.saldo -= valor;
                contaDestino.saldo += valor;
                this.contadorPoupanca = 0;
                contaDestino.contadorPoupanca = 0;
                System.out.println("Transferência feita com sucesso!");

            }else {
                this.saldo -= valor;
                contaDestino.saldo += valor;
                System.out.println("Transferência feita com sucesso!");

            }
        }
    }

    //Método para validar a senha cadastrada com a senha solicitada ao usuário em alguma operação
    public boolean conferirSenha(String senhaInformada){
        if(senhaInformada.equals(senha)){
            return true;
        }else {
            return false;
        }
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    public String getTipoConta() {return tipoConta;}

    //Método que exibe todas as informações da conta
    public String getDados(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "Agência: " + this.agencia + "\nConta: " + this.numero + "\nTitular: "+this.titular+"\nSaldo: R$ "+df.format(this.saldo);
    }

    /*Metodo que exibe informações da conta poupança, se diferenciando do método de conta corrente
    pelo calculo de juros compostos e rendimento do dinheiro na conta*/
    public String getDadosPoupanca(){
        contadorPoupanca ++;
        double saldoAntigo = saldo;
        saldo = saldo*Math.pow(1+rendimentoPoupanca,contadorPoupanca);
        rendimento += saldo - saldoAntigo;
        contadorPoupanca = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        return "Agência: " + this.agencia + "\nConta: " + this.numero + "\nTitular: " + this.titular + "\nSaldo: R$ " + df.format(this.saldo) +
                "\nRendimentos desde a criação da conta: R$ " +(df.format(rendimento))+"\n";
    }

    //Método que exibe as informações da conta sem o saldo, para exibir com segurança a lista completa de contas que o banco tem
    public String imprimirInformacoesContas() {
        return "Agência: " + this.agencia + "\nConta: " + this.numero + "\nTitular: "+this.titular+"\nTipo: "+this.tipoConta+"\n";
    }
}
