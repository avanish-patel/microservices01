package com.solstice.symbolservice.service;

import com.solstice.symbolservice.model.Symbol;
import com.solstice.symbolservice.repository.SymbolRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SymbolService {

    private SymbolRepository symbolRepository;

    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public Optional<Symbol> getIdForSymbol(String symbol) {

        Optional<Symbol> optionalSymbol = symbolRepository.findById(symbol);

        return optionalSymbol;
    }

    public Iterable<Symbol> insertSymbols() {

        List<Symbol> symbols = Arrays.asList(

                new Symbol(1, "APPL"),
                new Symbol(2, "GOOG"),
                new Symbol(3, "MSFT"),
                new Symbol(4, "PVTL"),
                new Symbol(5, "AMZN")
        );

        return symbolRepository.saveAll(symbols);
    }
}
