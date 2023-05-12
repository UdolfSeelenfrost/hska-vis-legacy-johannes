package model.businessLogic.manager.impl;


import model.businessLogic.manager.ProductManager;
import model.database.dataAcessObjects.ProductDAO;
import model.database.dataObjects.Product;

import java.util.List;

public class ProductManagerImpl implements ProductManager {
	private ProductDAO helper;
	
	public ProductManagerImpl() {
		helper = new ProductDAO();
	}

	public List<Product> getProducts() {
		return helper.getObjectList();
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {	
		return new ProductDAO().getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice);
	}

	public Product getProductById(int id) {
		return helper.getObjectById(id);
	}

	public Product getProductByName(String name) {
		return helper.getObjectByName(name);
	}
	
	public int addProduct(String name, double price, int categoryId, String details) {
		int productId = -1;

		//TODO Handle AddProduct with a Category
		/*
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);

		if(category != null){
			Product product;
			if(details == null){
				product = new Product(name, price, category);	
			} else{
				product = new Product(name, price, category, details);
			}
			
			helper.saveObject(product);
			productId = product.getId();
		}
		*/
		return productId;
	}
	

	public void deleteProductById(int id) {
		helper.deleteById(id);
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
