package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.Permission;
import com.delivery2go.base.data.repository.IPermissionRepository;

public class PermissionWebRepository extends WebRepository<Permission> implements IPermissionRepository{

	public PermissionWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, Permission.class, baseUrl, "Permission");
		setSessionId(sessionId);
	}

	public Permission getInstance() {
		return new Permission();
	}

}