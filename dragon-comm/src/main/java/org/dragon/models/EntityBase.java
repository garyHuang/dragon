package org.dragon.models;

import java.io.Serializable;

import org.dragon.annotations.ID;

public class EntityBase<PK> implements Serializable{

	private static final long serialVersionUID = 7279926465474652996L;
	@ID
	protected PK id ;
	
	public PK getId() {
		return id;
	}
	
	public void setId(PK id) {
		this.id = id;
	}
}
