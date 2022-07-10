package smdecommerce.venda_produto.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import smdecommerce.ServerConf;
import smdecommerce.produto.modelo.Produto;
import smdecommerce.produto.modelo.ProdutoDAO;


/**
 *
 * Classe que implementa o padrão DAO para a venda_produto
 */
public class Venda_ProdutoDAO {

    /**
     * Método utilizado para obter os id do produtos de uma venda e suas quantidades
     *
     * @param id_venda
     * @return
     * @throws Exception
     */
    public List<Venda_Produto> obterVendaProduto(int id_venda) throws Exception {
        List<Venda_Produto> produtos = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_venda, id_produto, quantidade FROM venda_produto WHERE id_venda = ?");
        preparedStatement.setInt(1, id_venda);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Venda_Produto venda_produto = new Venda_Produto();
            venda_produto.setId_venda(resultSet.getInt("id_venda"));
            venda_produto.setId_venda(resultSet.getInt("id_produto"));
            venda_produto.setQuantidade(resultSet.getInt("quantidade"));
            produtos.add(venda_produto);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //if (produtos.isEmpty()) {
           // throw new Exception("Não foi possível encontrar os produto da venda");
     //   }
        return produtos;
    }
    
    /**
     * Método utilizado para obter os produtos de uma venda
     *
     * @param id_venda
     * @return
     * @throws Exception
     */
    
    public List<Produto> obterProdutos(int id_venda) throws Exception {
        List<Produto> produtos = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_venda, id_produto, quantidade FROM venda_produto WHERE id_venda = ?");
        preparedStatement.setInt(1, id_venda);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            Produto produto = new Produto();
            produto = produtoDAO.obter(resultSet.getInt("id_produto"));
            produtos.add(produto);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //if (produtos.isEmpty()) {
           // throw new Exception("Não foi possível encontrar os produto da venda");
     //   }
        return produtos;
    }

    
    /**
     * Método utilizado para obter os produtos de uma venda
     *
     * @param id_produto
     * @return
     * @throws Exception
     */
    
    public List<Venda_Produto> obterVendas(int id_produto) throws Exception {
       List<Venda_Produto> vendas = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_venda, id_produto, quantidade FROM venda_produto WHERE id_produto = ?");
        preparedStatement.setInt(1, id_produto);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Venda_Produto venda_produto = new Venda_Produto();
            venda_produto.setId_venda(resultSet.getInt("id_venda"));
            venda_produto.setId_venda(resultSet.getInt("id_produto"));
            venda_produto.setQuantidade(resultSet.getInt("quantidade"));
            vendas.add(venda_produto);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //if (vendas.isEmpty()) {
            //throw new Exception("Não foi possível encontrar as vendas do produto");
       // }
        return vendas;
    }
    
    /**
     * Método utilizado para inserir um produto em uma venda
     *
     * @param id_produto
     * @param id_venda
     * @param quantidade
     * @throws Exception
     */
    public void inserir( Integer id_venda, Integer id_produto,Integer quantidade) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO venda_produto (id_venda, id_produto, quantidade) VALUES (?, ?, ?)");
        preparedStatement.setInt(1, id_venda);
        preparedStatement.setInt(2, id_produto);
        preparedStatement.setInt(3, quantidade);

        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível inserir o produto na venda");
        }
    }
    
    /**
     * Método utilizado para atualizar a quantidade de produto em uma venda
     * 
     * @param id_venda
     * @param id_produto
     * @param quantidade
     * @throws Exception
     */
    public void atualizarQuantidade(Integer id_venda, Integer id_produto, Integer quantidade) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE venda_produto SET quantidade = ? WHERE id_venda = ? AND id_produto = ?");
        preparedStatement.setInt(1, quantidade);
        preparedStatement.setInt(2, id_venda);
        preparedStatement.setInt(3, id_produto);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível atualizar a quantidade produto");
        }
    }
    
    /**
     * Método utilizado para excluir um produto de uma venda
     * 
     * @param id_venda
     * @param id_produto
     * @throws Exception
     */
    public void excluir(Integer id_venda, Integer id_produto) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM venda_produto WHERE id_venda = ? AND id_produto = ?");
        preparedStatement.setInt(1, id_venda);
        preparedStatement.setInt(2, id_produto);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível excluir o produto da venda");
        }
    }
    
    public void excluirProdutos(Integer id_venda) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM venda_produto WHERE id_venda = ?");
        preparedStatement.setInt(1, id_venda);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível excluir os produtos da venda");
        }
    }
}
