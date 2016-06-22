package com.zhangzhihao.FileUpload.Java.Service;


import com.zhangzhihao.FileUpload.Java.Dao.BaseDao;
import com.zhangzhihao.FileUpload.Java.Utils.PageResults;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * BaseService作为所有Service的基类，需要使用的话，需要先编写一个继承自此类的类
 *
 * @param <T> 实体类型
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class BaseService<T> {
	@Autowired
	private BaseDao<T> baseDao;
	private Class<T> modelClass;

	public BaseService() {
		modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 保存对象
	 *
	 * @param model 需要添加的对象
	 * @return 是否添加成功
	 */
	public Boolean save(T model) {
		return baseDao.save(model);
	}

	/**
	 * 添加并且返回Integer类型的ID
	 *
	 * @param model 需要添加的对象
	 * @return Integer类型的ID
	 */
	public Integer saveAndGetIntegerID(T model) {
		return (Integer) baseDao.saveAndGetIntegerID(model);
	}

	/**
	 * 添加并且返回String类型的ID
	 *
	 * @param model 需要添加的对象
	 * @return String类型的ID
	 */
	public String saveAndGetStringID(T model) {
		return (String) baseDao.saveAndGetStringID(model);
	}


	/**
	 * 删除对象
	 *
	 * @param model 需要删除的对象
	 *              失败会抛异常
	 */
	public void delete(T model) {
		baseDao.delete(model);
	}

	/**
	 * 批量删除对象
	 *
	 * @param modelList 需要删除的对象的集合
	 *                  失败会抛异常
	 */
	public void deleteAll(List<T> modelList) {
		modelList.stream().forEach(baseDao::delete);
	}

	/**
	 * 按照id删除对象
	 *
	 * @param id         需要删除的对象的id
	 *                   失败抛出异常
	 */
	public void deleteById(Serializable id) {
		baseDao.delete(this.getById(id));
	}

	/**
	 * 更新对象
	 *
	 * @param model 需要更新的对象
	 *              失败会抛出异常
	 */
	public void update(T model) {
		baseDao.update(model);
	}


	/**
	 * 添加或者更新
	 *
	 * @param model 需要更新或添加的对象
	 *              失败会抛出异常
	 */
	public void saveOrUpdate(T model) {
		baseDao.saveOrUpdate(model);
	}

	/**
	 * 通过主键, 查询对象
	 *
	 * @param id         主键(Serializable)
	 * @return model
	 */
	@Transactional(readOnly = true)
	public T getById(Serializable id) {
		return baseDao.getById(modelClass, id);
	}

	/**
	 * 获得全部
	 *
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<T> loadAll() {
		return baseDao.loadAll(modelClass);
	}


	/**
	 * 分页查询
	 *
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public List<T> getListByPage(Integer currentPageNumber, Integer pageSize) {
		return baseDao.getListByPage(modelClass, currentPageNumber, pageSize);
	}


	/**
	 * 按条件分页,条件以可变参形式传入，类型为Criterion [URL]http://zzk.cnblogs.com/s?t=b&w=Criteria
	 *
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @param criterions        查询条件数组，由Restrictions对象生成，如Restrictions.like("name","%x%")等;
	 * @param orders            查询后记录的排序条件,由Order对象生成
	 * @param projections       分组和聚合查询条件
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public PageResults<T> getListByPageAndRule(Integer currentPageNumber, Integer pageSize, final Criterion[] criterions, final Order[] orders,
	                                           final Projection[] projections) {
		return baseDao.getListByPageAndRule(modelClass, currentPageNumber, pageSize, criterions, orders, projections);
	}


	/**
	 * 获得符合对应条件的数量 利用Count(*)实现
	 *
	 * @param criterions 查询条件数组，由Restrictions对象生成，如Restrictions.like("name","%x%")等;
	 * @return 数量
	 */
	@Transactional(readOnly = true)
	public int getCountByRule(final Criterion[] criterions) {
		return baseDao.getCountByRule(modelClass, criterions);
	}


	/**
	 * 执行Sql语句
	 *
	 * @param sqlString sql
	 * @param values    不定参数数组
	 * @return 受影响的行数
	 */
	public int executeSql(String sqlString, Object... values) {
		return baseDao.executeSql(sqlString, values);
	}

	/**
	 * refresh 刷新实体状态
	 *
	 * @param t 实体
	 */
	public void refresh(T t) {
		baseDao.refresh(t);
	}
}
