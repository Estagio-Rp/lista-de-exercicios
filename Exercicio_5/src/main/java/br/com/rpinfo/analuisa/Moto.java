package br.com.rpinfo.analuisa;

public class Moto extends Veiculo {

    private int cilindradas;

    public Moto(String marca, String modelo, int cilindradas) {
        super(marca, modelo);
        this.cilindradas = cilindradas;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- MOTO ---");
        super.exibirInformacoes();
        System.out.println("Cilindradas: " + cilindradas + "cc");
    }
}
