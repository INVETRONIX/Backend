package com.invetronix.backend.APIsuppliers.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIsuppliers.models.Supplier;
import com.invetronix.backend.APIsuppliers.repositories.RepositorySupplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServiceSupplier implements IServiceSupplier{

    private final RepositorySupplier repositorySupplier;

    @Override
    public Supplier save(Supplier product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<Supplier> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Supplier> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<Supplier> delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<Supplier> update(String id, Supplier product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
