# Loja Web com banco de dados — Programação para Web I

Aplicação Web de comércio eletrônico (Servlets 4.0 / JSP / JSTL) adaptada da **Prova 2**
para usar **banco de dados PostgreSQL via JDBC**, em vez dos repositórios em memória.

## O que mudou em relação à Prova 2

| Antes (memória) | Agora (banco de dados) |
|---|---|
| `ProdutoMemoriaRepository` (lista fixa) | `ProdutoDAO` → tabela `produto` |
| `UsuarioMemoriaRepository` (senha em texto puro) | `UsuarioDAO` → tabela `usuario` (senha em hash SHA-256) |
| Carrinho só na `HttpSession` | Usuário logado: carrinho gravado na tabela `carrinho_item` (continua lá após logout/novo login). Visitante: carrinho na sessão, transferido para o banco ao fazer login. |

Estrutura nova em `loja-web-sessao-starter/src/main`:

```
java/br/edu/ifto/web/
├── config/ConnectionFactory.java     # conexão JDBC (variáveis DB_URL, DB_USER, DB_PASSWORD)
├── config/DatabaseInitializer.java   # @WebListener: cria tabelas e dados iniciais ao subir
├── dao/ProdutoDAO.java
├── dao/UsuarioDAO.java
├── dao/CarrinhoDAO.java
├── dao/DAOException.java
├── service/CarrinhoService.java      # regra: carrinho no banco (logado) ou na sessão (visitante)
└── util/SenhaUtil.java               # hash SHA-256 das senhas
resources/db/schema.sql               # script SQL (tabelas + dados iniciais)
```

Todas as consultas usam `PreparedStatement` (evita SQL injection) e
try-with-resources (fecha conexões automaticamente).

## Endpoints

| Método | URL | Descrição |
|---|---|---|
| GET | `/` | Página inicial |
| GET | `/produtos` | Lista os produtos do banco |
| GET | `/carrinho` | Mostra o carrinho |
| DELETE | `/carrinho` | Limpa o carrinho |
| POST | `/carrinho/itens` (`id`) | Adiciona produto (404 se não existir, 400 se id inválido) |
| DELETE | `/carrinho/itens?id=N` | Remove produto do carrinho |
| GET/POST | `/login` | Formulário / autenticação |
| GET | `/logout` | Encerra a sessão |

Usuário de teste: **jesse@email.com** / senha **1234567**.

## Como executar

### Opção 1 — GitHub Codespaces

1. No GitHub: **Code → Codespaces → Create codespace on main**.
2. O `.devcontainer` instala Java 17, Maven e Docker e executa `docker compose up -d --build`
   automaticamente (PostgreSQL + Tomcat 9).
3. Abra a aba **Ports** e acesse a porta **8080**. Para deixar o acesso público,
   clique com o botão direito na porta → **Port Visibility → Public**.

### Opção 2 — Docker local

```bash
docker compose up --build
```

Acesse http://localhost:8080 (ou http://localhost:8080/loja-web-sessao).

### Opção 3 — Tomcat próprio (NetBeans, IntelliJ, Eclipse)

1. Crie o banco no PostgreSQL:
   ```sql
   CREATE USER loja PASSWORD 'loja';
   CREATE DATABASE loja OWNER loja;
   ```
2. `cd loja-web-sessao-starter && mvn package` e implante `target/loja-web-sessao.war` num Tomcat 9.
3. As tabelas são criadas automaticamente na primeira inicialização.
   Para usar outro banco/usuário, defina as variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD`.

## Consultando o banco

```bash
docker compose exec db psql -U loja -d loja -c "SELECT * FROM produto;"
```
