package dao;

import model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public boolean salvar(Item item) {
        String sql = "INSERT INTO item (nome, descricao, preco) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescricao());
            stmt.setDouble(3, item.getPreco());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Item> listar() {
        List<Item> lista = new ArrayList<>();
        String sql = "SELECT * FROM item ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setNome(rs.getString("nome"));
                item.setDescricao(rs.getString("descricao"));
                item.setPreco(rs.getDouble("preco"));
                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
