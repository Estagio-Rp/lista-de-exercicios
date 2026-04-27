package br.com.rpinfo.analuisa;

public class Veiculo {

    protected String marca;
    protected String modelo;

    public Veiculo(String marca, String modelo){
        this.marca = marca;
        this.modelo = modelo;
    }

    public void exibirInformacoes(){
        System.out.println("Marca: " + marca);
        System.out.println("modelo" + modelo);

    }
}
