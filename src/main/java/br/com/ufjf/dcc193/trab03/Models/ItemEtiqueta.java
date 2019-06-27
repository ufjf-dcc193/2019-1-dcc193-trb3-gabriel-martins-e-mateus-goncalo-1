package br.com.ufjf.dcc193.trab03.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemEtiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;    
    
    @ManyToOne
    @JoinColumn
    private Item itemEtiqueta;

    @ManyToOne
    @JoinColumn
    private Etiqueta etiquetaItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return itemEtiqueta;
    }

    public void setItem(Item item) {
        this.itemEtiqueta = item;
    }

    public Etiqueta getEtiqueta() {
        return etiquetaItem;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiquetaItem = etiqueta;
    }

    public ItemEtiqueta(Item item, Etiqueta etiqueta) {
        this.itemEtiqueta = item;
        this.etiquetaItem = etiqueta;
    }

    public ItemEtiqueta() {
    }
    
    
}