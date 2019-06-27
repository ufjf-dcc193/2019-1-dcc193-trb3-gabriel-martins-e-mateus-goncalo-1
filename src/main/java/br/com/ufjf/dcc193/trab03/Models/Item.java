package br.com.ufjf.dcc193.trab03.Models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo;
    
    @OneToMany(mappedBy = "itemEtiqueta")
    private Set<ItemEtiqueta> itemEtiquetas;

    @OneToMany(mappedBy = "item")
    private Set<Anotacao> anotacoes;

    @OneToMany(mappedBy = "vinculoEntrada")
    private Set<Vinculo> vinculosEntrada;

    @OneToMany(mappedBy = "vinculoSaida")
    private Set<Vinculo> vinculosSaida;

    public Item() {
    }

    public Item(String titulo, Set<ItemEtiqueta> itemEtiquetas) {
        this.titulo = titulo;
        this.itemEtiquetas = itemEtiquetas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(Set<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    } 

    public Set<Vinculo> getVinculosEntrada() {
        return vinculosEntrada;
    }

    public void setVinculosEntrada(Set<Vinculo> vinculosEntrada) {
        this.vinculosEntrada = vinculosEntrada;
    }

    public Set<Vinculo> getVinculosSaida() {
        return vinculosSaida;
    }

    public void setVinculosSaida(Set<Vinculo> vinculosSaida) {
        this.vinculosSaida = vinculosSaida;
    }

    
    
}