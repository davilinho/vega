package com.vega.domain.usecase.executor;

/**
 * Component created on 24/11/2015.
 *
 * @author dmartin
 */

import com.squareup.otto.Bus;
import com.vega.domain.event.DataEvent;
import com.vega.domain.exception.DomainException;
import com.vega.domain.repository.IRepository;
import com.vega.domain.usecase.IUseCase;

public abstract class UseCaseExecutor<T1, T2> implements IUseCase<T1> {

    private IRepository repository;

    private Bus bus;

    private DataEvent<T2> event;

    public UseCaseExecutor(Bus bus, DataEvent<T2> event, IRepository repository) {
        this.bus = bus;
        this.event = event;
        this.repository = repository;
    }

    @Override
    public void execute(T1 data) {
        data = prepareRequestData(data);
        executeRequest(data);
        postEventBus();
    }

    protected abstract T1 prepareRequestData(T1 data);

    protected abstract T2 executeUseCaseRequest(T1 data) throws DomainException;

    protected IRepository getRepository() {
        return this.repository;
    }

    private void executeRequest(T1 data) {
        try {
            T2 response = executeUseCaseRequest(data);
            event.setData(response);
        } catch (DomainException e) {
            event.setError(e);
        }
    }

    private void postEventBus() {
        bus.post(event);
    }

}