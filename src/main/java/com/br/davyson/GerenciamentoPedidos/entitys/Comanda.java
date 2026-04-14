package com.br.davyson.GerenciamentoPedidos.entitys;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comanda")
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer mesa;
    private String atendenteNome;

    private List<String> comidaNome = new ArrayList<>();
    private String observacao;
    private LocalDateTime dataLancamento = LocalDateTime.now();

    public Comanda(Long id, Integer mesa, Atendente atendente, List<Comida> comida, String observacao, LocalDateTime dataLancamento) {
        this.id = id;
        this.mesa = mesa;
        this.atendenteNome = atendente.getNome();
        this.comidaNome = comida.stream().map(Comida::getNome).toList();
        this.observacao = observacao;
        this.dataLancamento = dataLancamento;
    }
    public Comanda(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getAtendenteNome() {
        return atendenteNome;
    }

    public void setAtendenteNome(String atendenteNome) {
        this.atendenteNome = atendenteNome;
    }

    public List<String> getComidaNome() {
        return comidaNome;
    }

    public void setComidaNome(List<String> comidaNome) {
        this.comidaNome = comidaNome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
