package com.sceptive.forgiva.integrator.core.db;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.SessionEvent;
import org.eclipse.persistence.sessions.SessionEventAdapter;
import org.eclipse.persistence.sessions.UnitOfWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DBSessionCustomizer implements SessionCustomizer {


    @Override
    public void customize(Session session) throws Exception {
        session.getEventManager().addListener(new SessionEventAdapter() {
            @Override
            public void postLogin(SessionEvent event) {



            }
        });

        session.getPlatform().setConversionManager(new DBConversionManager());
    }
}
