package com.invetronix.backend.APIproducts.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.services.in.IDeleteProduct;
import com.invetronix.backend.APIproducts.services.in.IFindAllProducts;
import com.invetronix.backend.APIproducts.services.in.IFindProductById;
import com.invetronix.backend.APIproducts.services.in.IFindProductsByFilters;
import com.invetronix.backend.APIproducts.services.in.ISaveProduct;
import com.invetronix.backend.APIproducts.services.in.IUpdateProduct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceProduct implements IDeleteProduct, IFindAllProducts, IFindProductById, IFindProductsByFilters, ISaveProduct, IUpdateProduct{
    private final IDeleteProduct deleteProduct;
    private final IFindAllProducts findAllProducts;
    private final IFindProductById findProductById;
    private final IFindProductsByFilters findProductsByFilters;
    private final ISaveProduct saveProduct;
    private final IUpdateProduct updateProduct;

    @Override
    public Optional<Product> updateById(String id, Product Product) {
       return updateProduct.updateById(id, Product);
    }

    @Override
    public Product save(Product product) {
        return saveProduct.save(product);
    }
       
    @Override
    public List<Product> findByFilters(String nameProduct, String category, String nameSupplier) {
        return findProductsByFilters.findByFilters(nameProduct, category, nameSupplier);
    }

    @Override
    public Optional<Product> findById(String id) {
        return findProductById.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return findAllProducts.findAll();
    }

    @Override
    public Optional<Product> deleteById(String id) {
       return deleteProduct.deleteById(id);
    }

}