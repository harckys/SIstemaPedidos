package dao;

import dao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioSeeder {

    public static void inserirUsuarioPadrao() {
        String sql = "INSERT INTO usuario (nome, login, senha) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Administrador");
            stmt.setString(2, "admin");
            stmt.setString(3, "1234");

            stmt.executeUpdate();
            System.out.println("Usuário admin criado com sucesso.");

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Usuário 'admin' já existe.");
            } else {
                System.err.println("Erro ao inserir usuário: " + e.getMessage());
            }
        }
    }
}
