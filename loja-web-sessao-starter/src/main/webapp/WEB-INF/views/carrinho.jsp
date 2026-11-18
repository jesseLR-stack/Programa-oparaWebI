<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrinho - Loja Web</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<header class="topo">
    <div class="container topo-conteudo">
        <div>
            <h1>Loja Web</h1>
            <span>Programação para Web I</span>
        </div>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
            <a href="${pageContext.request.contextPath}/produtos">Produtos</a>
            <a class="ativo" href="${pageContext.request.contextPath}/carrinho">Carrinho <span class="contador"><c:out value="${quantidadeTotal}"/></span></a>
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <div class="usuario-painel">
                        <span class="usuario-logado">Olá, ${sessionScope.usuarioLogado.nome}</span>
                        <a class="btn-sair" href="${pageContext.request.contextPath}/logout">Sair</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="btn-entrar" href="${pageContext.request.contextPath}/login">Entrar</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>

<main class="container secao">
    <div class="titulo-pagina">
        <div>
            <h2>Meu carrinho</h2>
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <p>Seu carrinho está salvo no banco de dados e fica disponível sempre que você entrar.</p>
                </c:when>
                <c:otherwise>
                    <p>Seu carrinho fica na sessão atual. Entre na sua conta para salvá-lo no banco de dados.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty itens}">
            <section class="card">
                <h3>Seu carrinho está vazio</h3>
                <p>Adicione produtos para vê-los aqui.</p>
                <a class="btn btn-primario" href="${pageContext.request.contextPath}/produtos">Ver produtos</a>
            </section>
        </c:when>
        <c:otherwise>
            <div class="carrinho-layout">
                <section class="carrinho-produtos">
                    <c:forEach var="item" items="${itens}">
                        <article class="item-carrinho">
                            <div class="item-icone">
                                <c:choose>
                                    <c:when test="${item.produto.id == 1}">
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                            <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
                                            <line x1="2" y1="20" x2="22" y2="20"></line>
                                        </svg>
                                    </c:when>
                                    <c:when test="${item.produto.id == 2}">
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                            <rect x="6" y="3" width="12" height="18" rx="6"></rect>
                                            <line x1="12" y1="7" x2="12" y2="11"></line>
                                        </svg>
                                    </c:when>
                                    <c:when test="${item.produto.id == 3}">
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                            <rect x="2" y="4" width="20" height="16" rx="2"></rect>
                                            <line x1="6" y1="8" x2="6" y2="8"></line>
                                            <line x1="10" y1="8" x2="10" y2="8"></line>
                                            <line x1="14" y1="8" x2="14" y2="8"></line>
                                            <line x1="18" y1="8" x2="18" y2="8"></line>
                                            <line x1="6" y1="12" x2="6" y2="12"></line>
                                            <line x1="10" y1="12" x2="10" y2="12"></line>
                                            <line x1="14" y1="12" x2="14" y2="12"></line>
                                            <line x1="18" y1="12" x2="18" y2="12"></line>
                                            <line x1="8" y1="16" x2="16" y2="16"></line>
                                        </svg>
                                    </c:when>
                                    <c:when test="${item.produto.id == 4}">
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                            <rect x="2" y="3" width="20" height="14" rx="2"></rect>
                                            <line x1="8" y1="21" x2="16" y2="21"></line>
                                            <line x1="12" y1="17" x2="12" y2="21"></line>
                                        </svg>
                                    </c:when>
                                    <c:otherwise>
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                            <path d="M3 18v-6a9 9 0 0 1 18 0v6"></path>
                                            <path d="M21 19a2 2 0 0 1-2 2h-1a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2h3zM3 19a2 2 0 0 0 2 2h1a2 2 0 0 0 2-2v-3a2 2 0 0 0-2-2H3z"></path>
                                        </svg>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="item-info">
                                <h3><c:out value="${item.produto.nome}"/></h3>
                                <span><fmt:formatNumber value="${item.produto.preco}" type="currency" currencyCode="BRL"/></span>
                            </div>
                            <div class="quantidade">
                                <span>Quantidade</span>
                                <strong><c:out value="${item.quantidade}"/></strong>
                            </div>
                            <div class="subtotal">
                                <span>Subtotal</span>
                                <strong><fmt:formatNumber value="${item.subtotal}" type="currency" currencyCode="BRL"/></strong>
                            </div>
                            <button class="btn-remover" type="button" data-id="${item.produto.id}">Remover</button>
                        </article>
                    </c:forEach>
                </section>

                <aside class="resumo">
                    <h3>Resumo do pedido</h3>
                    <div class="resumo-linha">
                        <span>Quantidade total</span>
                        <strong><c:out value="${quantidadeTotal}"/></strong>
                    </div>
                    <div class="resumo-linha total">
                        <span>Total</span>
                        <strong><fmt:formatNumber value="${valorTotal}" type="currency" currencyCode="BRL"/></strong>
                    </div>
                    <a class="btn btn-secundario btn-bloco" href="${pageContext.request.contextPath}/produtos">Continuar comprando</a>
                    <button id="limpar-carrinho" class="btn btn-perigo btn-bloco" type="button">Limpar carrinho</button>
                </aside>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<footer><div class="container">Programação para Web I</div></footer>

<script>
(() => {
    const context = '${pageContext.request.contextPath}';
    document.querySelectorAll('.btn-remover').forEach(button => {
        button.addEventListener('click', async () => {
            button.disabled = true;
            try {
                const response = await fetch(context + '/carrinho/itens?id=' + encodeURIComponent(button.dataset.id), { method: 'DELETE' });
                if (!response.ok) throw new Error('Não foi possível remover o produto.');
                window.location.reload();
            } catch (error) {
                alert(error.message);
                button.disabled = false;
            }
        });
    });

    const limpar = document.getElementById('limpar-carrinho');
    if (limpar) {
        limpar.addEventListener('click', async () => {
            if (!confirm('Deseja realmente limpar o carrinho?')) return;
            limpar.disabled = true;
            try {
                const response = await fetch(context + '/carrinho', { method: 'DELETE' });
                if (!response.ok) throw new Error('Não foi possível limpar o carrinho.');
                window.location.reload();
            } catch (error) {
                alert(error.message);
                limpar.disabled = false;
            }
        });
    }
})();
</script>
</body>
</html>
