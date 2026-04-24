package orientacaoObjetos.encapsulamento;

public class ContaBancaria {

    private String titular;
    private double saldo;

    public ContaBancaria(String titular, double saldoInicial) {
        this.titular = titular;

        if (saldoInicial >= 0) {
            this.saldo = saldoInicial;
        } else {
            System.out.println("Erro: o saldo inicial não pode ser negativo!");
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito realizado com sucesso!");
            System.out.println("Valor depositado: R$ " + valor);
        }else{
            System.out.println("Erro: o valor do saldo deve ser maior que zero!");
        }

    }

    public void sacar(double valor){
        if (valor <= 0){
            System.out.println("Erro: o valor do saque deve ser maior que zero");

        }else if (valor > saldo){
            System.out.println("Erro: saldo insuficiente");
        }else{
            saldo -= valor;
            System.out.println("DADOS DA CONTA");
            System.out.println("Titular" + titular);
            System.out.println("Saldo incial: R$" + saldo);
        }
    }
    public void exibirDados() {
        System.out.println("\n--- DADOS DA CONTA ---");
        System.out.println("Titular: " + titular);
        System.out.println("Saldo atual: R$ " + saldo);
    }
}

