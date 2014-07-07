package com.cortez.wildfly.batch.batchlet;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class MyBatchlet extends AbstractBatchlet {
    @Override
    public String process() throws Exception {
        System.out.println("MyBatchlet.process");

        // return BatchStatus.COMPLETED.toString();
        return "COMPLETED";
    }
}
