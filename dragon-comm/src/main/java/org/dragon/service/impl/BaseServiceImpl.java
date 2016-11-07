package org.dragon.service.impl;

import org.dragon.dao.impl.BaseDaoImpl;
import org.dragon.service.BaseService;

public class BaseServiceImpl<T, PK> extends BaseDaoImpl<T, PK> implements
		BaseService<T, PK> {
	
}
