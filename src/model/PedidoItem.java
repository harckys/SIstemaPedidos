package model;

public class PedidoItem {
    private Item item;
    private int quantidade;
    private double subtotal;

    // Getters e Setters
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public double getSubtotal() { return subtotal; }

    public void calcularSubtotal() {
        if (item != null && quantidade > 0) {
            this.subtotal = item.getPreco() * quantidade;
        }
    }
}
