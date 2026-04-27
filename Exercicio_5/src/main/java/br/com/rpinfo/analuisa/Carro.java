package br.com.rpinfo.analuisa;

public class Carro extends Veiculo {

    private String tipoCombustivel;

    public Carro(String marca, String modelo, String tipoCombustivel) {
        super(marca, modelo);
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("\n--- CARRO ---");
        super.exibirInformacoes();
        System.out.println("Tipo de combustível: " + tipoCombustivel);
    }
}
