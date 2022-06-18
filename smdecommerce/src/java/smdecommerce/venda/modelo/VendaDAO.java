package smdecommerce.venda.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import smdecommerce.ServerConf;

/**
 *
 * Classe que implementa o padrão DAO para a entidade venda
 */
public class VendaDAO {

    /**
     * Método utilizado para obter uma venda pelo seu identificador
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Venda obter(int id) throws Exception {
        Venda venda = null;
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id, data_hora, id_usuario, pagamento, status_pag, entrega, status_ent FROM venda WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            venda = new Venda();
            venda.setId(resultSet.getInt("id"));
            venda.setData_hora(resultSet.getString("data_hora"));
            venda.setId_usuario(resultSet.getInt("id_usuario"));
            venda.setPagamento(resultSet.getInt("pagamento"));
            venda.setStatus_pagamento(resultSet.getString("status_pag"));
            venda.setPagamento(resultSet.getInt("entrega"));
            venda.setStatus_pagamento(resultSet.getString("status_ent"));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        if (venda == null) {
            throw new Exception("Venda não encontrada");
        }
        return venda;
    }


    /**
     * Método utilizado para inserir uma nova venda
     *
     * @param id_usuario
     * @param pagamento
     * @param status_pag
     * @param entrega
     * @param status_ent
     * @throws Exception
     */
    public void inserir(Integer id_usuario, Integer pagamento, String status_pag, Integer entrega, String status_ent) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO venda (data_hora, id_usuario, pagamento, status_pag, entrega, status_ent) VALUES (NOW(),?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, id_usuario);
        preparedStatement.setInt(2, pagamento);
        preparedStatement.setString(3, status_pag);
        preparedStatement.setInt(4, entrega);
        preparedStatement.setString(5, status_ent);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível inserir a venda");
        }
    }
    
    /**
     * Método utilizado para atualizar uma  venda
     * 
     * @param id
     * @param id_usuario
     * @param pagamento
     * @param status_pag
     * @param entrega
     * @param status_ent
     * @throws Exception
     */
    public void atualizar(Integer id, Integer id_usuario, Integer pagamento, String status_pag, Integer entrega,String status_ent) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE venda SET data_hora=NOW(), id_usuario=?, pagamento=?, status_pag=?, entrega=?, status_ent=? WHERE id = ?");
        preparedStatement.setInt(1, id_usuario);
        preparedStatement.setInt(2, pagamento);
        preparedStatement.setString(3, status_pag);
        preparedStatement.setInt(4, entrega);
        preparedStatement.setString(5, status_ent);
        preparedStatement.setInt(6, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível atualizar a venda");
        }
    }
    
    /**
     * Método utilizado para excluir uma  venda
     * 
     * @param id
     * @throws Exception
     */
    public void excluir(Integer id) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM venda WHERE id = ?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível excluir a venda");
        }
    }
}
