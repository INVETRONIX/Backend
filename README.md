# Backend

*Servicio de REGISTRO corriendo en:*

    #POST
    localhost:8080/api/usuarios

**registrar un cliente:**

    {
        "name": "Juan Pérez",
        "email": "juan.perez@example.com",
        "password": "MiClaveSegura123",
        "telefone": "+57 3101234567",
        "edad": 30
    }

**registrar un administrador**

    {
        "name": "Juan Pérez",
        "email": "juan.perez123@example.com",
        "password": "MiClaveSegura123",
        "experiencia": "heinshon",
        "sueldo": "30USD"
    }


*Obtener todos los registros*

    #GET
    localhost:8080/api/usuarios

*Buscar un registro en especifico por id*

    #GET
    localhost:8080/api/usuarios/{id}



**API DE PRODUCTOS**

*Servicio corriendo en:*

    #POST
    localhost:8080/api/products

**Registrar un producto**

    {
        "name": "gf",
        "description": "6",
        "price": 65.0,
        "category": "f",
        "stockQuantity": 6,
        "supplier": {
            "name": "juan",
            "company": "Nombre de Empresa Válido",
            "phone": "+57 3129348230",
            "email": "juan@gmail.com"
        }
    }

*Buscar un producto por id*

    #GET
    localhost:8080/api/products/{id}

*Obtener todos los registros*

    #GET
    localhost:8080/api/products

*modificar producto*

    #PUT
    localhost:8080/api/products/{id}

    {
        "name": "69",
        "description": "69",
        "price": 1.0,
        "category": "1",
        "stockQuantity": 69,
        "supplier": {
            "name": "69",
            "company": "69",
            "phone": "+57 3129348230",
            "email": "juan@gmail.com"
        }
    }

*Eliminar producto*

    #DELETE
    localhost:8080/api/products/{id}

*Buscar por filtros*

    #GET
    localhost:8080/api/products?nameProduct=uno
    localhost:8080/api/products?category=69
