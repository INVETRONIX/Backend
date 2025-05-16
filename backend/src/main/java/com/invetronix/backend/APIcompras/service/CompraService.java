package com.invetronix.backend.APIcompras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIcompras.exception.CompraNoEncontrada;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.repository.CompraRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompraService implements ICompraService{

    @Autowired
    private CompraRepository compraRepository;

    @Override
    public Optional<Compra> update(Long id, Compra compra) {
        if(compraRepository.getCompraById(id).isPresent()){
            Compra updated = compraRepository.getCompraById(id).get();

            updated.setFecha(LocalDate.now());
            updated.setHora(LocalTime.now().withSecond(0).withNano(0));
            updated.setUsuario(compra.getUsuario());
            updated.setProducto(compra.getProducto());

            Compra saved = compraRepository.save(updated);
            return Optional.of(saved);
        }else{
            throw new CompraNoEncontrada("La compra con id : " + id + "No fue encontrada");
        }
    }

    @Override
    public Optional<Compra> delete(Long id) {
        Optional<Compra> compra = compraRepository.getCompraById(id);
        if(compra.isPresent()){
            compraRepository.delete(compra.get());
            return compra;
        }else{
            throw new CompraNoEncontrada("La compra con id : " + id + "No fue encontrada");
        }
    }

    @Override
    public Compra create(Compra compra) {
        return compraRepository.save(compra);
    }

    @Override
    public Optional<Compra> getCompraById(Long id) {
        if(compraRepository.getCompraById(id).isPresent()){
            return Optional.of(compraRepository.getCompraById(id).get());
        }else{
            throw new CompraNoEncontrada("La compra con id : " + id + "No fue encontrada");
        }
    }

    @Override
    public List<Compra> getAll() {
        return compraRepository.findAll();
    }

    @Override
    public List<Compra> findByFecha(LocalDate fecha) {
        return compraRepository.findByFecha(fecha);
    }

    @Override
    public List<Compra> findByUsuarioId(Long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Compra> findByHora(LocalTime hora) {
        return compraRepository.findByHora(hora);
    }

    @Override
    public List<Compra> findByFilters(LocalDate fecha, Long usuarioId, LocalTime hora) {
        return compraRepository.findByFilters(fecha, usuarioId, hora);
    }
    
}
