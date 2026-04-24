package orientacaoObjetos.abstracao;

public class Boleto extends Pagamento {

    private String codigoBoleto;

    public Boleto(double valor, String codigoBoleto) {
        super(valor);
        this.codigoBoleto = codigoBoleto;
    }

    @Override
    public boolean validarDados() {
        if (valor <= 0) {
            System.out.println("Erro: o valor do pagamento deve ser maior que zero.");
            return false;
        }

        if (codigoBoleto == null || codigoBoleto.length() < 10) {
            System.out.println("Erro: o código do boleto deve conter pelo menos 10 caracteres.");
            return false;
        }

        return true;
    }

    @Override
    public void processarPagamento() {
        if (validarDados()) {
            System.out.println("Pagamento via Boleto Bancário processado com sucesso!");
            System.out.println("Valor pago: R$ " + valor);
            System.out.println("Código do boleto: " + codigoBoleto);
        }
    }
}