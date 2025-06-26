package view;

import javax.swing.*;

public class AjudaView extends JFrame {

    public AjudaView() {
        super("Ajuda do Sistema");

        JTextArea textoAjuda = new JTextArea();
        textoAjuda.setEditable(false);
        textoAjuda.setLineWrap(true);
        textoAjuda.setWrapStyleWord(true);

        textoAjuda.setText("""
             Instruções básicas de uso do sistema:

            1. Faça login com usuário autorizado para acessar o sistema.
            2. Cadastre clientes com nome, CPF/CNPJ, email e telefone.
            3. Cadastre itens com nome, descrição e preço unitário.
            4. Crie novos pedidos:
               - Selecione o cliente
               - Adicione itens com quantidade
               - O total será calculado automaticamente
               - A data e o status inicial são registrados automaticamente
            5. Um cliente só pode ter 1 pedido pendente por vez.
            6. Atualize o status dos pedidos de forma válida:
               Pendente → Em Processamento → Finalizado ou Cancelado
            7. Acesse o relatório para ver o total de pedidos por cliente.

            💾 Todas as informações são armazenadas em banco SQLite.
            """ );

        JScrollPane scroll = new JScrollPane(textoAjuda);

        add(scroll);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
