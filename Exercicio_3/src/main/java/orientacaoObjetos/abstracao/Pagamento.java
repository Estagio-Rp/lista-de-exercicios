package orientacaoObjetos.abstracao;

public abstract class Pagamento {

    protected double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public abstract boolean validarDados();

    public abstract void processarPagamento();
}