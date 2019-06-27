package br.com.ufjf.dcc193.trab03.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo;
    
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Anotacao> anotacoes;

    @OneToMany(mappedBy = "itemEtiqueta", cascade = CascadeType.ALL)
    private Set<ItemEtiqueta> itemEtiquetas;

    @OneToMany(mappedBy = "itemVinculo", cascade = CascadeType.ALL)
    private Set<ItemVinculo> itemVinculo;

    public Item() {
    }

    public Item(String titulo, Set<Anotacao> anotacoes, Set<ItemEtiqueta> itemEtiquetas, Set<ItemVinculo> itemVinculo) {
        this.titulo = titulo;
        this.anotacoes = anotacoes;
        this.itemEtiquetas = itemEtiquetas;
        this.itemVinculo = itemVinculo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<ItemEtiqueta> getItemEtiquetas() {
        return itemEtiquetas;
    }

    public void setItemEtiquetas(Set<ItemEtiqueta> itemEtiquetas) {
        this.itemEtiquetas = itemEtiquetas;
    }

    public Set<ItemVinculo> getItemVinculo() {
        return itemVinculo;
    }

    public void setItemVinculo(Set<ItemVinculo> itemVinculo) {
        this.itemVinculo = itemVinculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(Set<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    
}