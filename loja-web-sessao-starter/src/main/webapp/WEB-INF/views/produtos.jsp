<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produtos - Loja Web</title>
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
            <a class="ativo" href="${pageContext.request.contextPath}/produtos">Produtos</a>
            <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
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
            <h2>Produtos</h2>
            <p>Confira os equipamentos disponíveis e adicione-os ao seu carrinho.</p>
        </div>
    </div>

    <div class="grade-produtos">
        <c:forEach var="produto" items="${produtos}">
            <article class="produto-card">
                <div class="produto-icone">
                    <c:choose>
                        <c:when test="${produto.id == 1}">
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
                                <line x1="2" y1="20" x2="22" y2="20"></line>
                            </svg>
                        </c:when>
                        <c:when test="${produto.id == 2}">
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="6" y="3" width="12" height="18" rx="6"></rect>
                                <line x1="12" y1="7" x2="12" y2="11"></line>
                            </svg>
                        </c:when>
                        <c:when test="${produto.id == 3}">
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
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
                        <c:when test="${produto.id == 4}">
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="2" y="3" width="20" height="14" rx="2"></rect>
                                <line x1="8" y1="21" x2="16" y2="21"></line>
                                <line x1="12" y1="17" x2="12" y2="21"></line>
                            </svg>
                        </c:when>
                        <c:otherwise>
                            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M3 18v-6a9 9 0 0 1 18 0v6"></path>
                                <path d="M21 19a2 2 0 0 1-2 2h-1a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2h3zM3 19a2 2 0 0 0 2 2h1a2 2 0 0 0 2-2v-3a2 2 0 0 0-2-2H3z"></path>
                            </svg>
                        </c:otherwise>
                    </c:choose>
                </div>
                <h3><c:out value="${produto.nome}"/></h3>
                <p class="produto-descricao"><c:out value="${produto.descricao}"/></p>
                <strong class="preco"><fmt:formatNumber value="${produto.preco}" type="currency" currencyCode="BRL" /></strong>
                <form action="${pageContext.request.contextPath}/carrinho/itens" method="post">
                    <input type="hidden" name="id" value="${produto.id}">
                    <button class="btn btn-primario btn-bloco" type="submit">Adicionar ao carrinho</button>
                </form>
            </article>
        </c:forEach>
    </div>
</main>

<footer><div class="container">Programação para Web I</div></footer>
</body>
</html>
