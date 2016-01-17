package com.vega.app.domain;


import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.Params;
import com.vega.domain.usecase.IUseCase;
import com.vega.domain.usecase.invoker.Invoker;
import com.vega.domain.usecase.invoker.Priority;

public class InvokerExecutor<T> implements Invoker<T> {

    private JobManager jobManager;

    public InvokerExecutor(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public void execute(T data, IUseCase<T> useCase) {
        execute(data, useCase, Priority.MEDIUM);
    }

    @Override
    public void execute(T data, IUseCase<T> useCase, Priority priority) {
        jobManager.addJob(useCaseToJob(data, useCase, priority));
        jobManager.start();
    }

    private Job useCaseToJob(T data, IUseCase<T> useCase, Priority priority) {
        Params params = new Params(priority.getPriorityValue());
        return new JobExecutor<>(data, params, useCase);
    }
}
