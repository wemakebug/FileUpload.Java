package com.zhangzhihao.FileUpload.Java.Dao;


import com.zhangzhihao.FileUpload.Java.Utils.PageResults;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * 只需要一个BaseDao就可以，需要访问数据库的地方，比如BaseService<T>只需要private BaseDao<T> baseDao即可
 *
 * @param <T> 实体类型
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Transactional(timeout = 1)
@Repository
@Primary
public class BaseDao<T> {

	@Autowired
	private HibernateTemplate hibernateTemplate;


	/**
	 * 保存对象
	 *
	 * @param model 需要添加的对象
	 * @return 是否添加成功
	 */
	public Boolean save(final T model) {
		Serializable save = hibernateTemplate.save(model);
		if (save != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加并且返回Integer类型的ID
	 *
	 * @param model 需要添加的对象
	 * @return Integer类型的ID
	 */
	public Integer saveAndGetIntegerID(final T model) {
		return (Integer) hibernateTemplate.save(model);
	}

	/**
	 * 添加并且返回String类型的ID
	 *
	 * @param model 需要添加的对象
	 * @return String类型的ID
	 */
	public String saveAndGetStringID(final T model) {
		return (String) hibernateTemplate.save(model);
	}


	/**
	 * 删除对象
	 *
	 * @param model 需要删除的对象
	 *              失败会抛异常
	 */
	public void delete(final T model) {
		hibernateTemplate.delete(model);
	}

	/**
	 * 批量删除对象
	 *
	 * @param modelList 需要删除的对象的集合
	 *                  失败会抛异常
	 */
	public void deleteAll(final List<T> modelList) {
		modelList.stream().forEach(hibernateTemplate::delete);
	}

	/**
	 * 按照id删除对象
	 *
	 * @param modelClass 类型，比如User.class
	 * @param id         需要删除的对象的id
	 *                   失败抛出异常
	 */
	public void deleteById(final Class<T> modelClass, Serializable id) {
		hibernateTemplate.delete(this.getById(modelClass, id));
	}

	/**
	 * 更新对象
	 *
	 * @param model 需要更新的对象
	 *              失败会抛出异常
	 */
	public void update(final T model) {
		hibernateTemplate.update(model);
	}


	/**
	 * 添加或者更新
	 *
	 * @param model 需要更新或添加的对象
	 *              失败会抛出异常
	 */
	public void saveOrUpdate(final T model) {
		hibernateTemplate.saveOrUpdate(model);
	}

	/**
	 * 通过主键, 查询对象
	 *
	 * @param modelClass 类型，比如User.class
	 * @param id         主键(Serializable)
	 * @return model
	 */
	@Transactional(readOnly = true)
	public T getById(Class<T> modelClass, final Serializable id) {
		return hibernateTemplate.get(modelClass, id);
	}

	/**
	 * 获得全部
	 *
	 * @param modelClass 类型，比如User.class
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<T> loadAll(Class<T> modelClass) {
		return hibernateTemplate.loadAll(modelClass);
	}


	/**
	 * 分页查询
	 *
	 * @param modelClass        类型，比如User.class
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public List<T> getListByPage(Class<T> modelClass,final  Integer currentPageNumber,final  Integer pageSize) {
		if (currentPageNumber <= 0 || pageSize <= 0) {
			return null;
		}
		Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(modelClass);
		criteria.setFirstResult((currentPageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}


	/**
	 * 按条件分页,条件以可变参形式传入，类型为Criterion [URL]http://zzk.cnblogs.com/s?t=b&w=Criteria
	 *
	 * @param modelClass        类型，比如User.class
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @param criterions        查询条件数组，由Restrictions对象生成，如Restrictions.like("name","%x%")等;
	 * @param orders            查询后记录的排序条件,由Order对象生成
	 * @param projections       分组和聚合查询条件
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public PageResults<T> getListByPageAndRule(Class<T> modelClass, Integer currentPageNumber, Integer pageSize, final Criterion[] criterions, final Order[] orders,
	                                           final Projection[] projections) {
		Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(modelClass);
		//添加条件
		if (criterions != null && criterions.length > 0) {
			for (int i = 0; i < criterions.length; i++) {
				criteria.add(criterions[i]);
			}
		}
		//添加排序
		if (orders != null && orders.length > 0) {
			for (int i = 0; i < orders.length; i++) {
				criteria.addOrder(orders[i]);
			}
		}
		//添加分组统计
		if (projections != null && projections.length > 0) {
			for (int i = 0; i < projections.length; i++) {
				criteria.setProjection(projections[i]);
			}
		}

		//参数验证
		int totalCount = getCountByRule(modelClass, criterions);
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		if (currentPageNumber > pageCount && pageCount != 0) {
			currentPageNumber = pageCount;
		}

		//查看是否要分页
		if (currentPageNumber >= 0 && pageSize >= 0) {
			criteria.setFirstResult((currentPageNumber - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}
		return new PageResults<T>(currentPageNumber + 1, currentPageNumber, pageSize, totalCount, pageCount, criteria.list());
	}


	/**
	 * 获得符合对应条件的数量 利用Count(*)实现
	 *
	 * @param modelClass 类型，比如User.class
	 * @param criterions 查询条件数组，由Restrictions对象生成，如Restrictions.like("name","%x%")等;
	 * @return 数量
	 */
	@Transactional(readOnly = true)
	public int getCountByRule(Class<T> modelClass, final Criterion[] criterions) {
		Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(modelClass);
		//添加条件
		if (criterions != null && criterions.length > 0) {
			for (int i = 0; i < criterions.length; i++) {
				criteria.add(criterions[i]);
			}
		}
		criteria.setProjection(Projections.rowCount());
		long uniqueResult = 0;
		try {
			uniqueResult = (long) criteria.uniqueResult();
		} catch (Exception ex) {
			uniqueResult = 0;
		}
		return (int) uniqueResult;
	}


	/**
	 * 执行Sql语句
	 *
	 * @param sqlString sql
	 * @param values    不定参数数组
	 * @return 受影响的行数
	 */
	public int executeSql(String sqlString, Object... values) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery(sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sqlQuery.setParameter(i, values[i]);
			}
		}
		return sqlQuery.executeUpdate();
	}

	/**
	 * refresh 刷新实体状态
	 *
	 * @param t 实体
	 */
	public void refresh(T t) {
		hibernateTemplate.refresh(t);
	}
}

