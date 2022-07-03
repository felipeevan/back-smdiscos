package smdecommerce.produto_categoria.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import smdecommerce.produto.modelo.Produto;
import smdecommerce.produto.modelo.ProdutoDAO;

import smdecommerce.ServerConf;
import smdecommerce.categoria.modelo.Categoria;
import smdecommerce.categoria.modelo.CategoriaDAO;
/**
 *
 * Classe que implementa o padrão DAO para a entidade produto_categoria
 */
public class Produto_CategoriaDAO {
    
    /**
     * Método utilizado para obter os produtos de uma categoria
     *
     * @param id_categoria
     * @return
     * @throws Exception
     */
    public List<Produto> obterProdutos(int id_categoria) throws Exception {
       List<Produto> produtos = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_produto FROM produto_categoria WHERE  id_categoria = ?");
        preparedStatement.setInt(1, id_categoria);
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
           // throw new Exception("Não foi possível encontrar os produto da categoria");
     //   }
        return produtos;
    }
    
    public List<Produto> obterProdutosEmEstoque(int id_categoria) throws Exception {
       List<Produto> produtos = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT produto.id FROM produto_categoria JOIN produto ON produto.id = produto_categoria.id_produto WHERE quantidade > 0 AND id_categoria = ?");
        preparedStatement.setInt(1, id_categoria);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            Produto produto = new Produto();
            produto = produtoDAO.obter(resultSet.getInt("id"));
            produtos.add(produto);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //if (produtos.isEmpty()) {
           // throw new Exception("Não foi possível encontrar os produto da categoria");
     //   }
        return produtos;
    }
  
    public List<Categoria> obterCategorias(int id_produto) throws Exception {
       List<Categoria> categorias = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_categoria FROM produto_categoria WHERE  id_produto = ?");
        preparedStatement.setInt(1, id_produto);
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = new Categoria();
            categoria = categoriaDAO.obter(resultSet.getInt("id_categoria"));
            categorias.add(categoria);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return categorias;
    }
    
    public List<Categoria> obterTodasCategorias() throws Exception {
       List<Categoria> categorias = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT id_categoria FROM produto_categoria");
        ResultSet resultSet;
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = new Categoria();
            categoria = categoriaDAO.obter(resultSet.getInt("id_categoria"));
            categorias.add(categoria);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return categorias;
    }

    /**
     * Método utilizado para inserir um produto em uma categoria
     *
     * @param id_produto
     * @param id_categoria
     * @throws Exception
     */
    public void inserir( Integer id_produto,Integer id_categoria) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO produto_categoria (id_produto, id_categoria) VALUES (?, ?)");
        preparedStatement.setInt(1, id_produto);
        preparedStatement.setInt(2, id_categoria);

        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível inserir o produto na categoria");
        }
    }
    
    /**
     * Método utilizado para atualizar a categoria de um produto
     * 
     * @param id_produto
     * @param id_categoria
     * @param novoId_categoria
     * @throws Exception
     */
    public void atualizarCategoria(Integer id_produto, Integer id_categoria, Integer novoId_categoria) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE produto_categoria SET id_categoria=? WHERE id_produto = ? AND id_categoria = ?");
        preparedStatement.setInt(1, novoId_categoria);
        preparedStatement.setInt(2, id_produto);
        preparedStatement.setInt(3, id_categoria);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível atualizar a categoria do produto");
        }
    }
    
    /**
     * Método utilizado para excluir um  produto da categoria
     * 
     * @param id_produto
     * @param id_categoria
     * @throws Exception
     */
    public void excluir(Integer id_produto,  Integer id_categoria) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + ServerConf.URL +":" +
                ServerConf.PORT + "/" + ServerConf.DATABASE, ServerConf.USER, ServerConf.PASS);     
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM produto_categoria WHERE id_produto = ? AND id_categoria = ?");
        preparedStatement.setInt(1, id_produto);
        preparedStatement.setInt(2, id_categoria);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (resultado != 1) {
            throw new Exception("Não foi possível excluir o produto da categoria");
        }
    }
}
