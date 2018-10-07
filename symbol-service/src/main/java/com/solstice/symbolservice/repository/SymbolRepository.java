package com.solstice.symbolservice.repository;

import com.solstice.symbolservice.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, String> {

}
