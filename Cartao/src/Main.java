import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Conta contaBanco = new Conta("Mateus", 1000);
        Scanner teclado = new Scanner(System.in);
        Calculo ferramentaCalculo = new Calculo();
        String nomeProduto;
        double valorProduto;
        int continua = 1;
        System.out.println("Bem vindo ao banco, onde você notifica tudo que" +
                        "irá comprar, senhor " + contaBanco.getTitular()+
                        "! Seu saldo é de: " + contaBanco.getSaldo() + " reais.");
        while (continua == 1) {
            System.out.println("Qual o preço?");
            valorProduto = teclado.nextDouble();
            System.out.println("O que você quer comprar?");
            teclado.nextLine();
            nomeProduto= teclado.nextLine();
            Produto produtoCompra = new Produto(nomeProduto,valorProduto);
            if(contaBanco.checaSaldoDisponivel(produtoCompra)){
                continua = 1;
            }
            else {
                continua = 0;
            }
        }
        System.out.println("Pare! Você gastou tudo que podia. Segue abaixo " +
                "uma lista de todas suas compras:\n");
        contaBanco.produtos.sort(Comparator.comparing(Produto::getCusto));
        System.out.println(contaBanco.produtos);
        System.out.println("\n" + (contaBanco.getSaldo() - contaBanco.getCustoCompraTotal()));


    }
}
