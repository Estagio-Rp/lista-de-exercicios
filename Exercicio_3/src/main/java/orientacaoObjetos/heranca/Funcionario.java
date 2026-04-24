package orientacaoObjetos.heranca;

public class Funcionario {

    protected String nome;
    protected double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;

        if (salarioBase >= 0) {
            this.salarioBase = salarioBase;
        } else {
            System.out.println("Erro: o salario base não pode ser negativo");
            this.salarioBase = 0.0;
        }

    }

    public double calcularSalario() {
        return salarioBase;
    }

    public void exibirDados() {
        System.out.println("DADOS DO FUCIONÁRIO");
        System.out.println("nome:" + nome);
        System.out.println("Salário final: R$ " + calcularSalario());
    }
}