package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CriarTabela {
    public static void inicializar() {
    try (Connection conn = Conexao.conectar();
         Statement stmt = conn.createStatement()) {

        stmt.execute("""
                CREATE TABLE IF NOT EXISTS usuario (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    login TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL
                );
            """);

        // Tabela cliente
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS cliente (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    cpf_cnpj TEXT NOT NULL UNIQUE,
                    email TEXT,
                    telefone TEXT
                );
            """);

        // Tabela item
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS item (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    descricao TEXT,
                    preco REAL NOT NULL
                );
            """);

        // Tabela pedido
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS pedido (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente_id INTEGER NOT NULL,
                    data TEXT NOT NULL,
                    status TEXT NOT NULL,
                    total REAL NOT NULL,
                    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
                );
            """);

        // Tabela pedido_item
        stmt.execute("""
                CREATE TABLE IF NOT EXISTS pedido_item (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pedido_id INTEGER NOT NULL,
                    item_id INTEGER NOT NULL,
                    quantidade INTEGER NOT NULL,
                    subtotal REAL NOT NULL,
                    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
                    FOREIGN KEY (item_id) REFERENCES item(id)
                );
            """);

        System.out.println("Tabelas criadas/verificadas com sucesso.");

    } catch (SQLException e) {
        System.err.println("Erro ao criar tabelas: " + e.getMessage());
    }

    }
}
