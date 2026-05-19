package br.com.rpinfo.analuisa.application.dto.produtos;

import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosDTO {

    private Integer id;
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private Integer estoque;

    public Produto toEntity() {
        return new Produto(
                this.id,
                this.nome,
                this.preco,
                this.categoria,
                this.estoque
        );
    }

    public static ProdutosDTO fromEntity(Produto produto) {
        return new ProdutosDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria(),
                produto.getEstoque()
        );
    }
}