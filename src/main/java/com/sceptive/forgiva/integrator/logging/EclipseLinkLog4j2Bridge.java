package com.sceptive.forgiva.integrator.logging;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.LogLevel;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

public class EclipseLinkLog4j2Bridge extends AbstractSessionLog implements SessionLog {

    @Override
    public void log(SessionLogEntry sessionLogEntry) {
        boolean has_message     = sessionLogEntry.hasMessage();
        boolean has_exception   = sessionLogEntry.hasException();

        if (sessionLogEntry.getLevel() == SessionLog.INFO) {

            if (!has_exception) {
                Info.get_instance().print(formatMessage(sessionLogEntry));
            } else {
                Info.get_instance().print(sessionLogEntry.getException());
            }

        } else if (sessionLogEntry.getLevel() == SessionLog.WARNING) {

            if (!has_exception) {
                Warning.get_instance().print(formatMessage(sessionLogEntry));
            } else {
                Warning.get_instance().print(sessionLogEntry.getException());
            }

        } else if (sessionLogEntry.getLevel() == SessionLog.SEVERE) {

            if (!has_exception) {
                Fatal.get_instance().print(formatMessage(sessionLogEntry));
            } else {
                Fatal.get_instance().print(sessionLogEntry.getException());
            }

        }
    }
}
