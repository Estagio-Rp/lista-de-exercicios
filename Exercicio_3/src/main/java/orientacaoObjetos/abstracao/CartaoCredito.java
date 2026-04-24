package orientacaoObjetos.abstracao;

public class CartaoCredito extends Pagamento {

    private String numeroCartao;
    private String nomeTitular;
    private String cvv;

    public CartaoCredito(double valor, String numeroCartao, String nomeTitular, String cvv) {
        super(valor);
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.cvv = cvv;
    }

    @Override
    public boolean validarDados() {
        if (valor <= 0) {
            System.out.println("Erro: o valor do pagamento deve ser maior que zero.");
            return false;
        }

        if (numeroCartao == null || numeroCartao.length() != 16) {
            System.out.println("Erro: o número do cartão deve conter 16 dígitos.");
            return false;
        }

        if (nomeTitular == null || nomeTitular.isEmpty()) {
            System.out.println("Erro: o nome do titular não pode estar vazio.");
            return false;
        }

        if (cvv == null || cvv.length() != 3) {
            System.out.println("Erro: o CVV deve conter 3 dígitos.");
            return false;
        }

        return true;
    }

    @Override
    public void processarPagamento() {
        if (validarDados()) {
            System.out.println("Pagamento via Cartão de Crédito processado com sucesso!");
            System.out.println("Valor pago: R$ " + valor);
            System.out.println("Titular: " + nomeTitular);
        }
    }
}