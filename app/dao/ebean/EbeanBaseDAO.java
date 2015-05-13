package dao.ebean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.Update;

/**
 * Implements the generic CRUD data access operations.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 approach for Entity:DAO design.
 * <p>
 */
public abstract class EbeanBaseDAO<T, ID extends Serializable> {


	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	protected EbeanBaseDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	// create ------------------------------------------------------------------
	
	public T create(T entity) {
		Ebean.save(entity);
		return entity;
	}
	
	public int createAll(List<T> entities) {
		return Ebean.save(entities.iterator());
	}

	// find --------------------------------------------------------------------
	
	public T find(ID id) {
		return Ebean.find(getPersistentClass(), id);
	}
	
	public T findByQuery(Query<T> query) {
		return query.findUnique();
	}
	
	public List<T> findAllByQuery(Query<T> query) {
		return query.findList();
	}
	
	public PagingList<T> findAllByQuery(Query<T> query, int pageSize) {
		return query.findPagingList(pageSize);
	}
	
	public List<T> findByExample(T example) {
		Query<T> query = Ebean.createQuery(getPersistentClass());
		if (example != null) {
			query.where().iexampleLike(example);
		}
		return query.findList();
	}
	
	public PagingList<T> findByExample(T example, int pageSize) {
		Query<T> query = Ebean.createQuery(getPersistentClass());
		if (example != null) {
			query.where().iexampleLike(example);
		}
		return query.findPagingList(pageSize);
	}
	
	public List<T> findAll() {
		return Ebean.find(getPersistentClass()).findList(); 
	}
	
	public PagingList<T> findAll(int pageSize) {
		return Ebean.find(getPersistentClass()).findPagingList(pageSize);
	}

	// update ------------------------------------------------------------------
	
	public T update(T entity) {
		Ebean.save(entity);
		return entity;
	}
	
	public int updateAll(List<T> entities) {
		return Ebean.save(entities.iterator());
	}

	// delete ------------------------------------------------------------------
	
	public void delete(T entity) {
		Ebean.delete(entity);
	}
	
	public void deleteAll(List<T> entities) {
		Ebean.delete(entities.iterator());
	}

    public void deleteById(ID id) {
    	T entity = find(id);
    	Ebean.delete(entity);
	}
    
    public void deleteByIds(List<ID> ids) {
    	Query<T> query = Ebean.createQuery(getPersistentClass());
    	query.where().in(getPkFieldName(), ids);
    	deleteByQuery(query);
	}
    
	public void deleteByQuery(Query<T> query) {
		List<T> entities = findAllByQuery(query);
		Ebean.delete(entities.iterator());
	}
	
	public void deleteAll() {
		String query = "delete from " + this.getPersistentClass().getSimpleName();
		Update<T> deleteAll = Ebean.createUpdate(persistentClass, query);
		deleteAll.execute();
	}
	
	// boolean -----------------------------------------------------------------
	public boolean exists(ID id) {
		int count = Ebean.find(getPersistentClass())
			.where().eq(getPkFieldName(), id)
			.findRowCount();
		
		return (count != 0);
	}
	
	// query -------------------------------------------------------------------
	public Query<T> createQuery() {
		return Ebean.createQuery(getPersistentClass());
	}

	// schema info -------------------------------------------------------------
	
	/**
	 * Override it if the primary key has a different field name.
	 * @return The name of the primary key field
	 */
	public String getPkFieldName() {
		return "id";
	}
}