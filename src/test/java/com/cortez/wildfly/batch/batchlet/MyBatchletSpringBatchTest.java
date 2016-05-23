package com.cortez.wildfly.batch.batchlet;

import com.cortez.wildfly.batch.BatchTestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import java.util.Properties;

import static com.cortez.wildfly.batch.BatchTestHelper.keepTestAlive;
import static org.junit.Assert.assertEquals;

/**
 * @author Roberto Cortez
 */
@RunWith(Arquillian.class)
public class MyBatchletSpringBatchTest {
    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                                   .addClass(BatchTestHelper.class)
                                   .addClass(MyBatchlet.class)
                                   .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                                   .addAsWebInfResource("META-INF/jboss-deployment-structure.xml")
                                   .addAsResource("META-INF//batch.xml")
                                   .addAsResource("META-INF/batch-jobs/batchlet-job.xml");
        System.out.println(war.toString(true));
        return war;
    }

    /**
     * In the test, we're just going to invoke the batch execution and wait for completion. To validate the test
     * expected behaviour we just need to check the Batch Status in the +JbExecution+ object. We should get a
     * +BatchStatus.COMPLETED+.
     *
     * @throws Exception an exception if the batch could not complete successfully.
     */
    @Test
    public void testBatchletProcess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("batchlet-job", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        keepTestAlive(jobExecution);

        // Spring Batch does not update JobExecution with updated data. We need to query it again.
        assertEquals(BatchStatus.COMPLETED, jobOperator.getJobExecution(executionId).getBatchStatus());
    }
}
