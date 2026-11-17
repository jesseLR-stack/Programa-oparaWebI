package br.edu.ifto.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrinho {
    private final List<ItemCarrinho> itens = new ArrayList<>();

    public List<ItemCarrinho> getItens() { return itens; }

    public void adicionar(Produto produto) {
        Optional<ItemCarrinho> existente = itens.stream()
            .filter(item -> item.getProduto().getId() == produto.getId()).findFirst();
        if (existente.isPresent()) existente.get().incrementar();
        else itens.add(new ItemCarrinho(produto, 1));
    }

    public void remover(int id) {
        itens.removeIf(item -> item.getProduto().getId() == id);
    }

    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
    }

    public double getValorTotal() {
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }
}
