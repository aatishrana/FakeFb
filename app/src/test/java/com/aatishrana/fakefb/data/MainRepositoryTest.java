package com.aatishrana.fakefb.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


/**
 * Created by Aatish Rana on 20-Nov-17.
 */
@RunWith(RobolectricTestRunner.class)
public class MainRepositoryTest
{
    @Test
    public void processDataCaseOne() throws Exception
    {
        MainRepository repository = new MainRepository();
        repository.processData();
    }
}