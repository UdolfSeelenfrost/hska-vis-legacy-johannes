package com.products;

import model.businessLogic.manager.ProductManager;
import model.businessLogic.manager.impl.ProductManagerImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

    @GetMapping("/products")
    public String AddProduct(){
        ProductManager productManager = new ProductManagerImpl();
        int productId = productManager.addProduct("Erstelltes Produkt", Double.parseDouble("6969"), 2,"Tolle Details");

        String result = "failed";
        if (productId > 0) {
            result = "success";
        }
        return result;
    }


}
