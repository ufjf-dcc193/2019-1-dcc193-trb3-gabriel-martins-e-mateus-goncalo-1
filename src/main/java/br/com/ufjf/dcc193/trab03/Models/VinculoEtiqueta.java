package br.com.ufjf.dcc193.trab03.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VinculoEtiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;    
    
    @ManyToOne
    @JoinColumn
    private Vinculo vinculoEtiqueta;

    @ManyToOne
    @JoinColumn
    private Etiqueta etiquetaVinculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vinculo getVinculoEtiqueta() {
        return vinculoEtiqueta;
    }

    public void setVinculoEtiqueta(Vinculo vinculoEtiqueta) {
        this.vinculoEtiqueta = vinculoEtiqueta;
    }

    public Etiqueta getEtiquetaVinculo() {
        return etiquetaVinculo;
    }

    public void setEtiquetaVinculo(Etiqueta etiquetaVinculo) {
        this.etiquetaVinculo = etiquetaVinculo;
    }

    public VinculoEtiqueta() {
    }    
    
}