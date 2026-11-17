<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Loja Web</title>
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
            <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <span class="usuario-logado">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
                    <a class="btn-sair" href="${pageContext.request.contextPath}/logout">Sair</a>
                </c:when>
                <c:otherwise>
                    <a class="ativo" href="${pageContext.request.contextPath}/login">Entrar</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>

<main class="login-container">
    <section class="login-card">
        <div class="login-cabecalho">
            <div class="login-icone">
                <svg width="44" height="44" viewBox="0 0 24 24" fill="none" stroke="#359830" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
            </div>
            <h2>Entre na sua conta</h2>
            <p>Informe seu e-mail e senha para continuar.</p>
        </div>

        <c:if test="${not empty erro}">
            <div class="mensagem erro">
                <c:out value="${erro}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="campo">
                <label for="email">E-mail</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value="<c:out value='${email}'/>"
                    placeholder="Digite seu e-mail"
                    autocomplete="username"
                    required>
            </div>

            <div class="campo">
                <label for="senha">Senha</label>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                    placeholder="Digite sua senha"
                    autocomplete="current-password"
                    required>
            </div>

            <button class="btn btn-primario btn-bloco" type="submit">
                Entrar
            </button>
        </form>

        <div class="login-ajuda">
            <p>Entre com sua conta para acessar os recursos da Loja Web.</p>
        </div>
    </section>
</main>

<footer>
    <div class="container">
        Programação para Web I
    </div>
</footer>

</body>
</html>
