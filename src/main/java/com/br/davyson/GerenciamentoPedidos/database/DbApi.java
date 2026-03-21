package com.br.davyson.GerenciamentoPedidos.database;

import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.entitys.CartaoCliente;
import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.entitys.Comida;
import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import com.br.davyson.GerenciamentoPedidos.repositorys.AtendenteRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.CartaoClienteRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.CategoriaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class DbApi {

    @Autowired
    public CategoriaRepository categoriaRepository;
    @Autowired
    public ComidaRepository comidaRepository;
    @Autowired
    public CartaoClienteRepository cartaoClienteRepository;
    @Autowired
    public AtendenteRepository atendenteRepository;

    public void instanciarMenu(){
        Categoria c1 = new Categoria("Sobremesas");
        Categoria c2 = new Categoria("Crepes");
        Categoria c3 = new Categoria("Sanduíches");
        Categoria c4 = new Categoria("Bebidas");
        Categoria c5 = new Categoria("Shakes");
        Categoria c6 = new Categoria("Pizzas");
        Categoria c7 = new Categoria("Acompanhamentos");
        Categoria c8 = new Categoria("Refeições");
        Categoria c9 = new Categoria("Sucos");


        Comida cm1 = new Comida("Milkshake de Cupuaçú","Sorvete de creme batido com Creme de Cupuaçú e leite integral",new BigDecimal("24.99"));
        Comida cm2 = new Comida("Guaraná do Amazonas", "Vitamina de banana batida com gelo, xarope e pó de guaraná, farinha de amendoinm e granola", new BigDecimal("14.99"));
        Comida cm3 = new Comida("Milkshake de Graviola", "Sorvete de creme batido com polpa de graviola pura e leite condensado", new BigDecimal("22.50"));
        Comida cm4 = new Comida("Delícia Vermelha", "Sorvete de baunilha batido com morango, e calda de caramelo", new BigDecimal("25.90"));

        Comida cm5 = new Comida("Crepe de Carne de Sol", "Massa fina recheada com carne de sol desfiada e mussarela derretido", new BigDecimal("32.00"));
        Comida cm6 = new Comida("Crepe de Frango com Catupiry", "Frango desfiado temperado com ervas finas e legítimo Catupiry", new BigDecimal("29.00"));
        Comida cm7 = new Comida("Crepe Doce de Nutella", "Recheado com Nutella e morangos frescos picados", new BigDecimal("26.50"));

        Comida cm8 = new Comida("X-Regional", "Hambúrguer artesanal 180g, queijo coalho, mel de engenho e cebola caramelizada", new BigDecimal("30.90"));
        Comida cm9 = new Comida("Sanduíche de Pernil", "Pernil desfiado marinado no vinho branco, servido no pão baguete crocante com cebola dourada", new BigDecimal("31.90"));
        Comida cm10 = new Comida("Club Sandwich", "Triplo deck com peito de peru, alface, tomate, bacon crocante e maionese da casa", new BigDecimal("29.90"));

        Comida cm11= new Comida("Refrigerante Lata", "Coca-Cola, Guaraná Antártica ou Fanta (350ml)", new BigDecimal("6.50"));
        Comida cm12 = new Comida("Água Mineral", "Com ou sem gás (500ml)", new BigDecimal("4.50"));
        Comida cm13 = new Comida("Cerveja Artesanal", "IPA local gelada (600ml)", new BigDecimal("18.99"));

        Comida cm14 = new Comida("Pizza Marguerita", "Molho de tomate, muçarela, tomate cereja e manjericão fresco", new BigDecimal("45.00"));
        Comida cm15 = new Comida("Pizza de Calabresa", "Muçarela, calabresa fatiada e cebola roxa", new BigDecimal("48.00"));
        Comida cm16 = new Comida("Pizza Portuguesa", "Muçarela, presunto, ovos, cebola, azeitonas e pimentão", new BigDecimal("52.00"));

        Comida cm17 = new Comida("Batata Frita Especial", "Batatas crocantes com cobertura de cheddar cremoso e bacon em cubos", new BigDecimal("20.90"));
        Comida cm18 = new Comida("Macaxeira Frita", "Porção de macaxeira frita na manteiga de garrafa, crocante por fora e macia por dentro", new BigDecimal("18.00"));
        Comida cm19 = new Comida("Pastéis de Vento", "Porção com 6 mini pastéis (carne, queijo e frango) acompanhados de geleia de pimenta", new BigDecimal("25.90"));

        Comida cm20 = new Comida("Filé à Parmegiana", "Filé mignon empanado, coberto com molho de tomate artesanal e muçarela, servido com arroz e fritas", new BigDecimal("58.00"));
        Comida cm21 = new Comida("Baião de Dois", "Arroz com feijão fradinho, queijo coalho, charque picadinha e coentro, servido com farofa", new BigDecimal("42.00"));
        Comida cm22 = new Comida("Moqueca de Peixe", "Postas de peixe cozidas no leite de coco e azeite de dendê, acompanhado de pirão e arroz", new BigDecimal("65.00"));

        Comida cm23 = new Comida("Suco de Acerola com Laranja", "Combinação refrescante rica em Vitamina C, feita com frutas selecionadas (400ml)", new BigDecimal("12.00"));
        Comida cm24 = new Comida("Suco de Abacaxi com Hortelã", "Abacaxi pérola batido com folhas de hortelã fresca e gelo", new BigDecimal("11.50"));
        Comida cm25 = new Comida("Suco de Cajá", "Polpa concentrada de cajá nordestino, batido na hora (500ml)", new BigDecimal("10.00"));


        //shakes
        cm1.setCategoria(c5);
        cm2.setCategoria(c5);
        cm3.setCategoria(c5);
        cm4.setCategoria(c5);
        //crepes
        cm5.setCategoria(c2);
        cm6.setCategoria(c2);
        cm7.setCategoria(c2);
        //sanduíches
        cm8.setCategoria(c3);
        cm9.setCategoria(c3);
        cm10.setCategoria(c3);
        //bebidas
        cm11.setCategoria(c4);
        cm12.setCategoria(c4);
        cm13.setCategoria(c4);

        //pizzas
        cm14.setCategoria(c6);
        cm15.setCategoria(c6);
        cm16.setCategoria(c6);

        //acompanhamentos
        cm17.setCategoria(c7);
        cm18.setCategoria(c7);
        cm19.setCategoria(c7);

        //refeições
        cm20.setCategoria(c8);
        cm21.setCategoria(c8);
        cm22.setCategoria(c8);

        //sucos
        cm23.setCategoria(c9);
        cm24.setCategoria(c9);
        cm25.setCategoria(c9);

        categoriaRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9));

        comidaRepository.saveAll(Arrays.asList(cm1, cm2, cm3, cm4, cm5, cm6, cm7, cm8, cm9, cm10,
                cm11, cm12, cm13, cm14, cm15, cm16, cm17, cm18, cm19, cm20, cm21, cm22, cm23, cm24, cm25));
    }

    public void instanciarCartoes(){
            CartaoCliente cartao1 = new CartaoCliente(null, BandeiraCartao.VISA, FormaPagamento.CREDITO, new BigDecimal("250.00"), "@#1234");
            CartaoCliente cartao2 = new CartaoCliente(null, BandeiraCartao.MASTERCARD, FormaPagamento.CREDITO, new BigDecimal("125.40"), "@#9876");
            CartaoCliente cartao3 = new CartaoCliente(null, BandeiraCartao.ELO, FormaPagamento.DEBITO, new BigDecimal("200.00"), "@#5544");
            CartaoCliente cartao4 = new CartaoCliente(null, BandeiraCartao.AMERICAN_EXPRESS, FormaPagamento.DEBITO, new BigDecimal("150.00"), "@#102030");
            CartaoCliente cartao5 = new CartaoCliente(null, BandeiraCartao.ALELO, FormaPagamento.CREDITO, new BigDecimal("340.00"), "@#8899");
            CartaoCliente cartao6 = new CartaoCliente(null, BandeiraCartao.SODEXO, FormaPagamento.DEBITO, new BigDecimal("500.00"), "@#1122");
            CartaoCliente cartao7 = new CartaoCliente(null, BandeiraCartao.MAESTRO, FormaPagamento.VOUCHER, new BigDecimal("85.00"), "@#3344");
            CartaoCliente cartao8 = new CartaoCliente(null, BandeiraCartao.HIPERCARD, FormaPagamento.DEBITO, new BigDecimal("62.00"), "@#5566");
            CartaoCliente cartao9 = new CartaoCliente(null, BandeiraCartao.DINERS_CLUB, FormaPagamento.VOUCHER, new BigDecimal("70.00"), "@#7788");
            CartaoCliente cartao10 = new CartaoCliente(null, BandeiraCartao.CABAL, FormaPagamento.CREDITO, new BigDecimal("111.00"), "@#9900");

        cartaoClienteRepository.saveAll(List.of(cartao1, cartao2,cartao3,cartao4,cartao5,
                cartao6, cartao7, cartao8, cartao9, cartao10));

    }

    public void instanciarUser(){
        Atendente user1 = new Atendente("jose eduardo","José Eduardo", "user2026");
        Atendente user2 = new Atendente("armando oliveira", "Armando Oliveira", "admin2026");

        atendenteRepository.saveAll(List.of(user1,user2));
    }

}
