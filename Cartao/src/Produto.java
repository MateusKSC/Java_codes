public class Produto {
    private String nomeItem;
    private double custo;

    public Produto(String nomeItem, double custo) {
        this.nomeItem = nomeItem;
        this.custo = custo;
    }

    public String getNomeItem() {
        return nomeItem;
    }


    public double getCusto() {
        return custo;
    }



    @Override
    public String toString() {
        return "Produto{" +
                "nomeItem='" + this.nomeItem + '\'' +
                ", custo=" + this.custo +
                '}';
    }
}
