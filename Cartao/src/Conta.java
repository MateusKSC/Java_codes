import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Conta {
    private String titular;
    private double saldo;
    List<Produto> produtos;
    private int custoCompraTotal;

    public Conta(String titular, double saldo) {
        this.titular = titular;
        this.saldo = saldo;
        this.produtos = new ArrayList<>();
    }


    public int somaCustoTotal(Produto produto) {
        return custoCompraTotal += produto.getCusto();
    }

    public boolean checaSaldoDisponivel(Produto produto) {
        if (getSaldo() - somaCustoTotal(produto) > 0)
        {
            produtos.add(produto);
            return true;
        }
        else {
            return false;
        }
}

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Produto> getProdutosOrdenados() {
        produtos.sort(Comparator.comparing(Produto::getCusto));
        return produtos;
    }

    public int getCustoCompraTotal() {
        return custoCompraTotal;
    }
}
