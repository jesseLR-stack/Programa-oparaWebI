package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Produto;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProdutoMemoriaRepository {
    private final List<Produto> produtos = Collections.unmodifiableList(Arrays.asList(
        new Produto(1, "Notebook", "Notebook para trabalho e estudos.", 3500.00),
        new Produto(2, "Mouse", "Mouse óptico USB para uso diário.", 80.00),
        new Produto(3, "Teclado Mecânico", "Teclado mecânico para produtividade.", 250.00),
        new Produto(4, "Monitor", "Monitor para estação de trabalho.", 1200.00),
        new Produto(5, "Headset", "Headset para aulas, reuniões e jogos.", 180.00)
    ));

    public List<Produto> listarTodos() { return produtos; }

    public Optional<Produto> buscarPorId(int id) {
        return produtos.stream().filter(produto -> produto.getId() == id).findFirst();
    }
}
