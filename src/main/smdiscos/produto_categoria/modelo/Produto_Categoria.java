package smdiscos.produto_categoria.modelo;

import java.io.Serializable;

/*
 * Classe que representa a entidade produto_categoria
 */

public class Produto_Categoria implements Serializable {
    
    private Integer id_produto;
    private Integer id_categoria;
    
 
    public Integer getId_produto() {
        return id_produto;
    }

    public void setId_produto(Integer id_produto) {
        this.id_produto = id_produto;
    }
    
    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }
    
}
