package br.ccasj.sisauc.framework.dao;

import java.io.Serializable;
import java.util.List;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

public interface GenericEntityDAO<T extends EntidadeGenerica> extends Serializable {

	public T persist(T object);
	public T findById(Integer id);
	public T merge(T object);
	public void delete(Integer id);
	public List<T> findAll();

}
