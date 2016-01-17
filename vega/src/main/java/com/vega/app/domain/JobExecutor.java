package com.vega.app.domain;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.vega.domain.usecase.IUseCase;

public class JobExecutor<T> extends Job {

    private T data;

    private IUseCase<T> useCase;

    public JobExecutor(T data, Params params, IUseCase<T> useCase) {
        super(params);
        this.data = data;
        this.useCase = useCase;
    }

    @Override
    public void onAdded() { }

    @Override
    public void onRun() throws Throwable {
        useCase.execute(data);
    }

    @Override
    protected void onCancel() { }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
