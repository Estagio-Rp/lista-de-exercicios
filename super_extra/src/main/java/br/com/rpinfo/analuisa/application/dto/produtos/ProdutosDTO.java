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

    private Integer prodId;
    private String prodNome;
    private BigDecimal prodPreco;
    private String prodCategoria;
    private Integer prodEstoque;

    public Produto toEntity() {
        Produto produto = new Produto();

        produto.setId(this.prodId);
        produto.setNome(this.prodNome);
        produto.setPreco(this.prodPreco);
        produto.setCategoria(this.prodCategoria);
        produto.setEstoque(this.prodEstoque);

        return produto;
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