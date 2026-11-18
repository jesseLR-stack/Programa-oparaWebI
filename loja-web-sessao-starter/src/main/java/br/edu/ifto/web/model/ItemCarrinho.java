package br.edu.ifto.web.model;

public class ItemCarrinho {
    private final Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public void incrementar() { quantidade++; }
    public void incrementar(int valor) { quantidade += valor; }
    public double getSubtotal() { return produto.getPreco() * quantidade; }
}
