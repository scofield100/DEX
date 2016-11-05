package com.delivery2go.base.data.web;

import com.enterlib.conetivity.WebRepository;
import com.enterlib.conetivity.WebClientContext;
import com.delivery2go.base.models.DeliveryTarif;
import com.delivery2go.base.data.repository.IDeliveryTarifRepository;

public class DeliveryTarifWebRepository extends WebRepository<DeliveryTarif> implements IDeliveryTarifRepository{

	public DeliveryTarifWebRepository(WebClientContext webContext, String baseUrl, String sessionId) {
		super(webContext, DeliveryTarif.class, baseUrl, "DeliveryTarif");
		setSessionId(sessionId);
	}

	public DeliveryTarif getInstance() {
		return new DeliveryTarif();
	}

}