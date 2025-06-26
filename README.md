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
src/
├── model/ # Classes de domínio (Cliente, Item, Pedido, etc.)
├── dao/ # Acesso a dados (DAOs e conexão)
└── view/ # Interfaces gráficas (Swing)

---

## Banco de Dados

- Arquivo SQLite localizado em: `banco/banco.db`
- Criação automática das tabelas ao iniciar a aplicação (`CriarTabelas.java`)

Tabelas:
- `usuario`
- `cliente`
- `item`
- `pedido`
- `pedido_item`

---

## 👤 Usuário padrão para login

| Login  | Senha |
|--------|-------|
| admin  | 1234  |

Use o método `UsuarioSeeder.inserirUsuarioPadrao()` para cadastrar o usuário inicial.

---

## Como Executar

1. Clone ou baixe o repositório
2. Abra o projeto no IntelliJ IDEA
3. Certifique-se de que o SQLite JDBC está adicionado às bibliotecas (`sqlite-jdbc-x.x.x.jar`)
4. Rode a classe `Main.java`
5. O banco será criado automaticamente na pasta `banco/`
6. Acesse com o usuário `admin / 1234`

---

##Requisitos do Projeto (Acadêmico)

- Interface gráfica funcional com navegação por menu
- Cadastro de entidades com validações
- Persistência em banco relacional
- Regras de negócio implementadas
- Separação em camadas (MVC)
- Diagrama de classes UML (opcional)
- Código comentado

---

##Desenvolvido por

Lucas Fernandes
Vitor Hugo

---


