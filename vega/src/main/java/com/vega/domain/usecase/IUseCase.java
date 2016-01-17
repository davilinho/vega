package com.vega.domain.usecase;

/**
 * Component created on 19/11/2015.
 *
 * @author dmartin
 */
public interface IUseCase<T> {

    void execute(T data);

}
