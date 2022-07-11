package smdecommerce.cliente.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import smdecommerce.ServerConf;

/**
 *
 * Classe que implementa o padrão DAO para a entidade cliente
 */
public class ClienteDAO {

    /**
     * Método utilizado para obter um cliente pelo seu identificador
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Cliente obter(int id) throws Exception {
        Cliente cliente = null;
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_usuario, endereco FROM cliente WHERE id_usuario = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cliente = new Cliente();
            cliente.setId(resultSet.getInt("id_usuario"));
            cliente.setEndereco(resultSet.getString("endereco"));
          
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        if (cliente == null) {
            throw new Exception("Cliente não encontrado");
        }
        return cliente;
    }

     /**
     * Método utilizado para inserir um novo cliente
     *
     * @param id_usuario
     * @param endereco
     * @throws Exception
     */
    public void inserir( Integer id_usuario,String endereco) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO cliente (id_usuario, endereco) VALUES (?, ?)");
        preparedStatement.setInt(1, id_usuario);
        preparedStatement.setString(2, endereco);
       
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível inserir o cliente");
        }
    }
    
    /**
     * Método utilizado para atualizar o endereco de um cliente
     * 
     * @param id_usuario
     * @param endereco
     * @throws Exception
     */
    public void atualizarEndereco (Integer id_usuario, String endereco) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE cliente SET endereco=? WHERE id_usuario = ?");
        preparedStatement.setString(1, endereco);
        preparedStatement.setInt(2, id_usuario);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível atualizar o endereco do cliente");
        }
    }

    
    /**
     * Método utilizado para excluir um cliente
     * 
     * @param id_usuario
     * @throws Exception
     */
    public void excluir(Integer id_usuario) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM cliente WHERE id_usuario = ?");
        preparedStatement.setInt(1, id_usuario);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível excluir o cliente");
        }
    }
}
