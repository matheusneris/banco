public class ContaCorrente extends Conta{

    public ContaCorrente(Cliente cliente, String senha) {
        super(cliente, senha, "Conta Corrente");
    }

    public void imprimirExtrato(){
        System.out.println("==== Extrato da Conta Corrente ====");
        System.out.println(getDados());
        System.out.println("==================================");
    }
}