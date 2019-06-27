package br.com.ufjf.dcc193.trab03.Persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.ItemEtiqueta;

public interface ItemEtiquetaRepository extends JpaRepository <ItemEtiqueta, Long> {



    
}