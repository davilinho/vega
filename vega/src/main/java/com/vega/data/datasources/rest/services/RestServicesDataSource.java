package com.vega.data.datasources.rest.services;

import com.vega.data.datasources.rest.RestDataSource;
import com.vega.data.service.RestClientService;
import com.vega.repository.datasources.IRestDataSource;

/**
 * Component created on 30/11/2015.
 *
 * @author dmartin
 */
public class RestServicesDataSource extends RestDataSource implements IRestDataSource {

    public RestServicesDataSource(RestClientService restClientService) {
        super(restClientService);
    }
}
