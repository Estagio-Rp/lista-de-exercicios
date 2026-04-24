package orientacaoObjetos.heranca;

public class Desenvolvedor extends Funcionario {

    public Desenvolvedor(String nome, double salarioBase) {
        super(nome, salarioBase);
    }
    @Override
    public double calcularSalario() {
        return salarioBase + (salarioBase * 0.10);
    }
}
