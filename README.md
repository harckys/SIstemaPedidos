# Sistema de Gerenciamento de Pedidos

Sistema desktop em Java com interface gráfica (Swing) e persistência em banco de dados SQLite, desenvolvido para fins acadêmicos com base em requisitos funcionais e técnicos.

---

## Funcionalidades

### Menu Principal
- [1] Login
- [2] Cadastro de Cliente
- [3] Cadastro de Item
- [4] Novo Pedido
- [5] Listar Pedidos
- [6] Atualizar Status de Pedido
- [7] Relatório de Pedidos por Cliente
- [8] Sair
- [9] Ajuda

### Login
- Validação de credenciais com base na tabela `usuario`
- Acesso ao sistema permitido apenas após autenticação

### Cliente
- Cadastro com nome, CPF/CNPJ, e-mail e telefone
- Validação de CPF/CNPJ único

### Item
- Cadastro de itens com nome, descrição e preço
- Impede exclusão de item vinculado a pedido

### Pedido
- Criação de pedidos com seleção de cliente e múltiplos itens
- Cálculo automático do total
- Armazena data/hora e status inicial
- Impede mais de um pedido pendente por cliente

### Status de Pedido
- Status possíveis: `Pendente`, `Em Processamento`, `Finalizado`, `Cancelado`
- Regras de transição válidas entre os status

### Relatório
- Visualização dos pedidos por cliente
- Totais e status por cliente

### Ajuda
- Tela com instruções básicas de uso do sistema

---

##Tecnologias Utilizadas

- **Java 17**+
- **Swing (javax.swing)** para a interface gráfica
- **SQLite** como banco de dados local
- **JDBC** para persistência
- **IntelliJ IDEA** como IDE 

---

## Estrutura de Pastas

