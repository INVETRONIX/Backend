package com.invetronix.backend.APIcompras.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.service.CompraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Tag(name = "Compras", description = "API para la gestión de compras")
public class CompraController {
    
    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> create(@RequestBody Compra compra){
        Compra saved = compraService.create(compra);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> getAll(){
        List<Compra> compras = compraService.getAll();
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getById(@PathVariable Long id){
        Optional<Compra> compra = compraService.getCompraById(id);
        return compra.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> update(@PathVariable Long id, @RequestBody Compra compra){
        Optional<Compra> updated = compraService.update(id, compra);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        compraService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Compra>> findByFecha(@RequestParam LocalDate fecha){
        List<Compra> compras = compraService.findByFecha(fecha);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Compra>> findByUsuarioId(@PathVariable Long id){
        List<Compra> compras = compraService.findByUsuarioId(id);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/hora")
    public ResponseEntity<List<Compra>> findByHora(@RequestParam LocalTime hora){
        List<Compra> compras = compraService.findByHora(hora);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Compra>> findByFilters(@RequestParam(required = false) LocalDate fecha,
                                                    @RequestParam(required = false) Long usuarioId,
                                                    @RequestParam(required = false) LocalTime hora){
        List<Compra> compras = compraService.findByFilters(fecha, usuarioId, hora);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

}
