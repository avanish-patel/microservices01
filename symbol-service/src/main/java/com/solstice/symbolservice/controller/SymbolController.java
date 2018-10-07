package com.solstice.symbolservice.controller;

import com.solstice.symbolservice.model.Symbol;
import com.solstice.symbolservice.service.SymbolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SymbolController {

    private SymbolService symbolService;

    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping("/{symbol}")
    public Optional<Symbol> getIdForSymbol(@PathVariable("symbol") String symbol) {

        return symbolService.getIdForSymbol(symbol);
    }

    @GetMapping("/load")
    public Iterable<Symbol> insertSymbols() {

        return symbolService.insertSymbols();
    }
}
