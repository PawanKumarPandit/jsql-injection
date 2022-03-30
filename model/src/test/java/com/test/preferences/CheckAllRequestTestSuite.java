package com.test.preferences;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

import org.junitpioneer.jupiter.RepeatFailedTest;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;
import com.test.vendor.mysql.ConcreteMySqlTestSuite;

public class CheckAllRequestTestSuite extends ConcreteMySqlTestSuite {
    
    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.subscribe(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initializeQueryString("http://localhost:8080/post");
        model.getMediatorUtils().getParameterUtil().setListQueryString(Arrays.asList(
            new SimpleEntry<>("tenant", "mysql")
        ));
        model.getMediatorUtils().getParameterUtil().setListRequest(Arrays.asList(
            new SimpleEntry<>("name", ""),
            new SimpleEntry<>("fake", "empty")
        ));
        
        model.setIsScanning(true);
        
        model
        .getMediatorUtils()
        .getPreferencesUtil()
        .withCheckingAllRequestParam();
        
        model
        .getMediatorUtils()
        .getConnectionUtil()
        .withMethodInjection(model.getMediatorMethod().getRequest())
        .withTypeRequest("POST");
        
        model.beginInjection();
    }
    
    @Override
    @RepeatFailedTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }
}
