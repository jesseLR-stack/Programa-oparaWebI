-- =============================================================
-- Loja Web - esquema do banco de dados (PostgreSQL)
-- Executado automaticamente na inicialização da aplicação
-- (DatabaseInitializer). Pode ser executado várias vezes.
-- =============================================================

CREATE TABLE IF NOT EXISTS produto (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100)  NOT NULL,
    descricao VARCHAR(255),
    preco     NUMERIC(10,2) NOT NULL CHECK (preco >= 0)
);

CREATE TABLE IF NOT EXISTS usuario (
    id         SERIAL PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    senha_hash CHAR(64)     NOT NULL
);

-- Carrinho persistido por usuário autenticado
CREATE TABLE IF NOT EXISTS carrinho_item (
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    produto_id INTEGER NOT NULL REFERENCES produto(id) ON DELETE CASCADE,
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    PRIMARY KEY (usuario_id, produto_id)
);

-- Dados iniciais
INSERT INTO produto (id, nome, descricao, preco) VALUES
    (1, 'Notebook', 'Notebook para trabalho e estudos.', 3500.00),
    (2, 'Mouse', 'Mouse óptico USB para uso diário.', 80.00),
    (3, 'Teclado Mecânico', 'Teclado mecânico para produtividade.', 250.00),
    (4, 'Monitor', 'Monitor para estação de trabalho.', 1200.00),
    (5, 'Headset', 'Headset para aulas, reuniões e jogos.', 180.00)
ON CONFLICT (id) DO NOTHING;

SELECT setval('produto_id_seq', (SELECT MAX(id) FROM produto));

-- Senha "1234567" armazenada como hash SHA-256
INSERT INTO usuario (id, nome, email, senha_hash) VALUES
    (1, 'Jesse', 'jesse@email.com',
     '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414')
ON CONFLICT (id) DO UPDATE SET
    nome = EXCLUDED.nome, email = EXCLUDED.email, senha_hash = EXCLUDED.senha_hash;

SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM usuario));
