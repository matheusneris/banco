import java.util.ArrayList;

public class Banco {
    //Lista de contas do banco
    protected ArrayList<Conta> contas = new ArrayList<>();

    //Método para imprimir a lista com a agência, número e titular de todas as contas criadas no banco
    public void getContas(){
        for(int i = 0; i<contas.size();i++) {
            System.out.println(contas.get(i).imprimirInformacoesContas());
        }
    }

    //Método para encontrar o índice do objeto na lista de contas a partir do número da conta
    public int acharConta(int numero){
        //Inicializando retorno como -1 como o valor padrão para quando não achar uma conta
        int retorno = -1;
        for(int i=0;i<contas.size(); i++){
            //Caso ache uma iguandade entre o número digitado e algum objeto da lista muda-se o retorno para aquele índice
            if(contas.get(i).getNumero() == numero){
                retorno = i;
                break;
            }
        }return retorno;
    }

    //Operação de fazer depósito no banco, que chama o método da classe Conta que executa o procedimento
    public void fazerDeposito(int indexContas, double valor){ contas.get(indexContas).depositar(valor);}

    //Operação de fazer saque no banco, que chama o método da classe Conta que executa o procedimento
    public void fazerSaque(int indexContas, double valor){ contas.get(indexContas).sacar(valor);}

    //Operação de fazer transferência no banco, que chama o método da classe Conta que executa o procedimento
    public void fazerTransferencia(int indexContaOrigem, int indexContaDestino, double valor){
        contas.get(indexContaOrigem).transferir(contas.get(indexContaDestino),valor);
    }
}
