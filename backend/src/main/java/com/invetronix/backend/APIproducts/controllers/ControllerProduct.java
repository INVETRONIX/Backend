package com.invetronix.backend.APIproducts.controllers;
 
import java.util.List;
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
import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIproducts.exceptions.ProductAlreadyRegisteredException;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.services.IServiceProduct;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Productos", description = "API para gestión de productos")
@RequiredArgsConstructor
@RequestMapping("api/products")
@RestController
public class ControllerProduct {
    private final IServiceProduct serviceProduct;



    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = DtoProduct.class))
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "El producto ya está registrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<?> save(
        @Parameter(description = "Datos del producto a crear") 
        @Valid 
        @RequestBody DtoProduct productDto
    ){
        
        try {

            Product product= MapperProduct.toModel(productDto);

            Product saved = serviceProduct.save(product);

            DtoProduct productSaved = MapperProduct.toDto(saved);

            return new ResponseEntity<>(productSaved, HttpStatus.CREATED);

        } catch (ProductAlreadyRegisteredException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

        }catch (RuntimeException e){

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }



    @Operation(
        summary = "Obtener un producto por ID",
        description = "Recupera los detalles de un producto específico por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = DtoProduct.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Producto no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
        @Parameter(description = "ID del producto a buscar", required = true)
        @PathVariable String id
    ){

        try {

            return serviceProduct.findById(id)
            .map(product -> new ResponseEntity<>(MapperProduct.toDto(product), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Unexpected error: bus not found after validation."));

        } catch (ProductNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch(RuntimeException e){

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }



    @Operation(
        summary = "Listar todos los productos",
        description = "Recupera una lista de todos los productos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de productos encontrados",
            content = @Content(schema = @Schema(implementation = List.class))
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No hay productos registrados"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "No se encontraron productos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Product> products = serviceProduct.findAll();

            // Convertimos la lista de Product a DtoProduct
            List<DtoProduct> productsDto = products.stream()
                    .map(MapperProduct::toDto)
                    .toList();

            return productsDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(productsDto, HttpStatus.OK);

        } catch (ProductNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }



    @Operation(
        summary = "Actualizar un producto",
        description = "Actualiza los datos de un producto existente por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = DtoProduct.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Producto no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto en la actualización",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PutMapping("/{id}")                   
    public ResponseEntity<?> update( 
        @Parameter(description = "ID del producto a actualizar", required = true)
        @PathVariable String id, 
    
        @Parameter(description = "Datos del producto a actualizar", required = true)
        @Valid @RequestBody DtoProduct productDto
    ) {
        try {

            Product productUpdate = MapperProduct.toModel(productDto);
            return serviceProduct.update(id, productUpdate)
            .map(product -> new ResponseEntity<>(MapperProduct.toDto(product), HttpStatus.OK))
            .orElseThrow(()-> new RuntimeException("Unexpected error: bus not found after validation."));

        } catch (ProductNotFoundException e) {
            
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

        } catch (IllegalArgumentException e) {
        
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } catch (RuntimeException e) {
        
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }



    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto del sistema por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Producto eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto en la eliminación",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @Parameter(description = "ID del producto a eliminar", required = true)
        @PathVariable String id
    ) {
        try {
            return serviceProduct.delete(id)
            .map(product -> new ResponseEntity<>(MapperProduct.toDto(product), HttpStatus.OK))
            .orElseThrow(()-> new RuntimeException("Unexpected error: bus not found after validation."));
        } catch (ProductNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(
    summary = "Buscar productos por filtros",
    description = "Recupera una lista de productos que coincidan con los criterios de búsqueda especificados. " +
                  "Se pueden combinar múltiples filtros o usar uno solo."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Productos encontrados",
            content = @Content(schema = @Schema(implementation = List.class))
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No se encontraron productos con los criterios especificados"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "No se proporcionó ningún criterio de búsqueda",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "No se encontraron productos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> findByFilters(
            @Parameter(
                description = "Nombre o parte del nombre del producto a buscar (opcional)",
                example = "laptop"
            )
            @RequestParam(name = "nameProduct", required = false) String nameProduct, 
            
            @Parameter(
                description = "Categoría del producto a buscar (opcional)",
                example = "electrónica"
            )
            @RequestParam(name = "category", required = false) String category, 
            
            @Parameter(
                description = "Nombre del proveedor del producto a buscar (opcional)",
                example = "Acme Corp"
            )
            @RequestParam(name = "nameSupplier", required = false) String nameSupplier
        ){
        try {
            List<Product> products = serviceProduct.findByFilters(nameProduct, category, nameSupplier);
            List<DtoProduct> productsDto = products.stream()
                    .map(MapperProduct::toDto)
                    .toList();

            return productsDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(productsDto, HttpStatus.OK);

        } catch (ProductNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }

}