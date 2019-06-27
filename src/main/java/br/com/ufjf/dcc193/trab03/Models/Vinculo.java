package br.com.ufjf.dcc193.trab03.Models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Vinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    private Item vinculoEntrada;

    @ManyToOne
    private Item vinculoSaida;

    @OneToMany(mappedBy = "vinculo")
    private Set<Anotacao> anotacoes;

    @OneToMany(mappedBy = "vinculoEtiqueta")
    private Set<VinculoEtiqueta> vinculoEtiqueta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getVinculoEntrada() {
        return vinculoEntrada;
    }

    public void setVinculoEntrada(Item vinculoEntrada) {
        this.vinculoEntrada = vinculoEntrada;
    }

    public Item getVinculoSaida() {
        return vinculoSaida;
    }

    public void setVinculoSaida(Item vinculoSaida) {
        this.vinculoSaida = vinculoSaida;
    }

    public Set<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(Set<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public Vinculo() {

    }

    public Set<VinculoEtiqueta> getVinculoEtiqueta() {
        return vinculoEtiqueta;
    }

    public void setVinculoEtiqueta(Set<VinculoEtiqueta> vinculoEtiqueta) {
        this.vinculoEtiqueta = vinculoEtiqueta;
    }

    

    
}