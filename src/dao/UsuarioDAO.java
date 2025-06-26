package dao;
import model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
