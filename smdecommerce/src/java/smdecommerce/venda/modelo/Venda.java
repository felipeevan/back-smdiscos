package smdecommerce.venda.modelo;

import java.io.Serializable;

/*
 * Classe que representa a entidade Venda
 */

public class Venda implements Serializable {
    
    private Integer id;
    private String data_hora;
    private Integer id_usuario;
    private Integer pagamento;
    private String status_pagamento;
    private Integer entrega;
    private String status_entrega;
    private String status_pedido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData_hora() {
        return data_hora;
    }

    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getPagamento() {
        return pagamento;
    }

    public void setPagamento(Integer pagamento) {
        this.pagamento = pagamento;
    }

    public String getStatus_pagamento() {
        return status_pagamento;
    }

    public void setStatus_pagamento(String status_pagamento) {
        this.status_pagamento = status_pagamento;
    }

    public Integer getEntrega() {
        return entrega;
    }

    public void setEntrega(Integer entrega) {
        this.entrega = entrega;
    }
    
    public String getStatus_entrega() {
        return status_entrega;
    }

    public void setStatus_entrega(String status_entrega) {
        this.status_entrega = status_entrega;
    }

   public String getStatus_pedido() {
        return status_pedido;
    }

    public void setStatus_pedido(String status_pedido) {
        this.status_pedido = status_pedido;
    }
}
