package smdecommerce.venda_produto.modelo;

import java.io.Serializable;

/*
 * Classe que representa a entidade venda_produto
 */

public class Venda_Produto implements Serializable {
    
    private Integer id_venda;
    private Integer id_produto;
    private Integer quantidade;
 
    public Integer getId_venda() {
        return id_venda;
    }

    public void setId_venda(Integer id) {
        this.id_venda = id_venda;
    }
    
    public Integer getId_produto() {
        return id_produto;
    }

    public void setId_produto(Integer id_produto) {
        this.id_produto = id_produto;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
