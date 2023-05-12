package model.database.dataAcessObjects;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.database.GenericHibernateDAO;
import model.database.dataObjects.Product;
import model.sessionFactory.util.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO extends GenericHibernateDAO<Product, Integer> {
	
	public List<Product> getProductListByCriteria(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice){
		
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		List<Product> productList = null;

	    try {
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Product> crit = cb.createQuery(Product.class);
			Root<Product> root = crit.from(Product.class);
			
			// Define Search HQL:
			if (searchDescription != null && searchDescription.length() > 0)
			{	// searchValue is set:
				searchDescription = "%"+searchDescription+"%";
				crit.where(cb.like(cb.lower(root.get("details")), searchDescription.toLowerCase()));
			}

			if (( searchMinPrice != null) && ( searchMaxPrice != null)) {
				crit.where(cb.between(root.get("price"), searchMinPrice,  searchMaxPrice));
				}
			else if( searchMinPrice != null) {
				crit.where(cb.ge(root.get("price"), searchMinPrice));
					}
			else if ( searchMaxPrice != null) {
				crit.where(cb.le(root.get("price"), searchMaxPrice));
			}

			Query<Product> query = session.createQuery(crit);
			productList = query.getResultList();
		
			transaction.commit(); 
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	    return productList;
	}

	
}
