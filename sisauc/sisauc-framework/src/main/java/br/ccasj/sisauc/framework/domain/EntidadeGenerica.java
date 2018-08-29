package br.ccasj.sisauc.framework.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntidadeGenerica implements Serializable {

	private static final long serialVersionUID = 5948756185957797085L;
	
	public static final String SCHEMA_SISAUC = "sch_sisauc";
	public static final String SCHEMA_SDGA = "sch_sdga";

	protected Integer id;
	
	public EntidadeGenerica(){
		super();
	}
	
	public EntidadeGenerica(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadeGenerica other = (EntidadeGenerica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
