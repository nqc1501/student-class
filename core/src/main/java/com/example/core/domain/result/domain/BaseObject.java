package com.example.core.domain.result.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseObject {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final ExecutorService callbackExecutor
            = Executors.newFixedThreadPool(4);

}
