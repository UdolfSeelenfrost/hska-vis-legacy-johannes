package model.database;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.database.dataAcessObjects.IGenericDAO;
import model.database.dataObjects.BaseEntity;
import model.database.dataObjects.Product;
import model.sessionFactory.util.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public  class GenericHibernateDAO<E extends BaseEntity, PK extends Serializable> implements IGenericDAO<E, PK> {

	
	/**
	 * The class of the pojo being persisted.
	 */
	protected Class<E> entityClass;
	 
//	private static Logger log = Logger.class(GenericHibernateDAO.class);
 //   private static final Logger log = LogManager.getLogger("GenericHibernateDAO");

	
		@SuppressWarnings("unchecked")
		protected GenericHibernateDAO() {
//			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
//			this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
			final Type thisType = getClass().getGenericSuperclass();
			final Type type;
			if (thisType instanceof ParameterizedType) {
				type = ((ParameterizedType) thisType).getActualTypeArguments()[0];
			} else if (thisType instanceof Class) {
				type = ((ParameterizedType) ((Class) thisType).getGenericSuperclass()).getActualTypeArguments()[0];
			} else {
				throw new IllegalArgumentException("Problem handling type construction for " + getClass());
			}
			
			if (type instanceof Class) {
				this.entityClass = (Class<E>) type;
			} else if (type instanceof ParameterizedType) {
				this.entityClass = (Class<E>) ((ParameterizedType) type).getRawType();
			} else {
				throw new IllegalArgumentException("Problem determining the class of the generic for " + getClass());
			}
	 
		}
	 
		public void saveObject(E entity) { 		
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	 		try
			{
				session.beginTransaction();
				session.save(entity);
	            session.getTransaction().commit();
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}

		}
	 
		@SuppressWarnings("unchecked")
		public E getObjectById(PK id) {
					
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    
		    session.beginTransaction();

		    E entity = (E) session.get(entityClass, id);
		    session.getTransaction().commit();
		    return (E) entity;
		}

		
		@SuppressWarnings("unchecked")
		public E getObjectByName(String name) {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try
			{
				E entity = null;
				session.beginTransaction();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<E> crit = cb.createQuery(entityClass);
				Root<E> root = crit.from(entityClass);

				crit.where(cb.equal(root.get("name"), name));
				Query<E> query = session.createQuery(crit);

	            List<E> resultList = query.list();
	            if (resultList.size() > 0) {
	            	entity = (E) query.list().get(0);
	            }
	            session.getTransaction().commit();
	            return entity;
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		}
	

		public void deleteObject(E entity) {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

			try
			{
				session.beginTransaction();
				session.delete(entity);
				session.getTransaction().commit();
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}
	 
		}
	 
		
		public void deleteById(PK id) {
			    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

				try
				{
					session.beginTransaction();
				    E entity = (E) session.get(entityClass, id);
					session.delete(entity);
					session.getTransaction().commit();
				}
				catch (HibernateException e)
				{
					//log.error("Hibernate Exception" + e.getMessage());
					session.getTransaction().rollback();
					throw new RuntimeException(e);
				}
		 
			
		}


		@SuppressWarnings("unchecked")
		public List<E> get(E entity) {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    try
		    {
		    
		    session.beginTransaction();

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<E> crit = cb.createQuery(entityClass);
			Root<E> root = crit.from(entityClass);

			crit.where(cb.equal(root.get("id"), entity.getId()));
			Query<E> query = session.createQuery(crit);

		    List <E> resultList = query.list();
		    session.getTransaction().commit();
			return resultList;
		    }
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		}

		
		@SuppressWarnings("unchecked")
		public List<E> getObjectList() {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    
			try
			{		
				session.beginTransaction();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<E> crit = cb.createQuery(entityClass);
				Query<E> query = session.createQuery(crit);

	            List<E> resultList = query.list();
	            session.getTransaction().commit();
				return resultList;
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}

		}


		
		@SuppressWarnings("unchecked")
		public List<E> getSortedList(String sortOrder, String sortProperty) {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    
			try
			{		
				session.beginTransaction();

				CriteriaBuilder cb = session.getCriteriaBuilder();
				CriteriaQuery<E> crit = cb.createQuery(entityClass);
				Root<E> root = crit.from(entityClass);

				if (!sortProperty.equals("")){
					if (sortOrder.equals("asc")){
						crit.orderBy(cb.asc(root.get(sortProperty)));
					}
					else if (sortOrder.equals("desc")){
						crit.orderBy(cb.desc(root.get(sortProperty)));
						}
				}

				Query<E> query = session.createQuery(crit);
	            List<E> resultList = query.list();
	            session.getTransaction().commit();
				return resultList;
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}

		}

		public void updateObject(E entity) {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();

			try
			{
				session.beginTransaction();
				session.update(entity);
				session.getTransaction().commit();
			}
			catch (HibernateException e)
			{
				//log.error("Hibernate Exception" + e.getMessage());
				session.getTransaction().rollback();
				throw new RuntimeException(e);
			}
		}



}
