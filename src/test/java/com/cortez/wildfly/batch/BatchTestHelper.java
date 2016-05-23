package com.cortez.wildfly.batch;

import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

/**
 * @author Roberto Cortez
 */
public class BatchTestHelper {
    private static final int MAX_TRIES = 10;
    private static final int THREAD_SLEEP = 1000;

    private BatchTestHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * We need to keep the test running because JobOperator runs the batch job in an asynchronous way.
     * Returns when either the job execution completes or we have polled the maximum number of tries.
     *
     * @param jobExecution the JobExecution of the job that is being runned on JobOperator.
     * @return the most recent JobExecution obtained for this execution
     * @throws InterruptedException thrown by Thread.sleep.
     */
    public static JobExecution keepTestAlive(JobExecution jobExecution) throws InterruptedException {
        int maxTries = 0;
        while (!jobExecution.getBatchStatus().equals(BatchStatus.COMPLETED)) {
            if (maxTries < MAX_TRIES) {
                maxTries++;
                Thread.sleep(THREAD_SLEEP);
                jobExecution = BatchRuntime.getJobOperator().getJobExecution(jobExecution.getExecutionId());
            } else {
                break;
            }
        }
        return jobExecution;
    }
}
