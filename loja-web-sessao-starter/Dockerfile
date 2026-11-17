# Etapa 1: Build da aplicação com Maven e JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia dependências e código fonte
COPY pom.xml .
COPY src ./src

# Compila e gera o pacote WAR
RUN mvn clean package -DskipTests

# Etapa 2: Servidor Apache Tomcat 9 (Java EE 8 / javax.servlet)
FROM tomcat:9.0-jdk11-corretto

# Limpa aplicações padrão do Tomcat para evitar conflitos
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado tanto como loja-web-sessao.war quanto como ROOT.war
# para permitir acesso tanto em /loja-web-sessao quanto diretamente na raiz /
COPY --from=build /app/target/loja-web-sessao.war /usr/local/tomcat/webapps/loja-web-sessao.war
COPY --from=build /app/target/loja-web-sessao.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
