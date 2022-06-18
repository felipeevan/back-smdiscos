package smdecommerce.cliente.modelo;

import java.io.Serializable;

/*
 * Classe que representa a entidade cliente
 */

public class Cliente implements Serializable {
    
    private Integer id;
    private String endereco;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
}
