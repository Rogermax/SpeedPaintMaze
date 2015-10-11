package com.gmail.rogermoreta.speedpaintmaze;

import android.app.Application;
import android.test.ApplicationTestCase;

public class BurbujitaTest extends ApplicationTestCase<Application> {
    public BurbujitaTest() {
        super(Application.class);
    }
    @Override
    protected void setUp() throws Exception {
        createApplication();
    }

}