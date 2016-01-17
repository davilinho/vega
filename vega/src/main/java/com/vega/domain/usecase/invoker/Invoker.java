package com.vega.domain.usecase.invoker;

import com.vega.domain.usecase.IUseCase;

/**
 * Component created on 19/11/2015.
 *
 * @author dmartin
 */
public interface Invoker<T> {

    void execute(T data, IUseCase<T> useCase);

    void execute(T data, IUseCase<T> useCase, Priority priority);

}
