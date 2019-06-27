package br.com.ufjf.dcc193.trab03.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufjf.dcc193.trab03.Models.Item;

public interface ItemRepository extends JpaRepository <Item, Long> {

    
}