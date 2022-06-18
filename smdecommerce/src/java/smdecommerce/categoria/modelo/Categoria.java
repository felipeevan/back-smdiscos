package smdecommerce.categoria.modelo;

import java.io.Serializable;

/**
 * Classe que representa a entidade categoria
 */
public class Categoria implements Serializable {
    
    private Integer id;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
