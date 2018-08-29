package br.ccasj.sisauc.framework.dao;

import java.io.Serializable;

public interface GenericEntityHistoricoDAO<T> extends Serializable {

	public T merge(T object);

}
