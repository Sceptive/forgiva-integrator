package com.sceptive.forgiva.integrator.services;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.ESession;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.gen.model.Header;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SecurityManager {
/*
    Creates unauthenticated empty session
 */
public static ESession create_unauthenticated_session(String _client_public_key) {
    ESession new_session = ESession.new_instance();
    new_session.client_public_key = _client_public_key;
    EntityManager em = Database.get_instance()
                               .getEm();
    em.getTransaction()
      .begin();
    try {
        em.persist(new_session);
    }
    catch (Exception e) {
        Warning.get_instance()
               .print(e);
    }
    em.getTransaction()
      .commit();
    em.close();
    return new_session;
}

/*
    Invalidates session object as removing from database
 */
public static void invalidate_session(ESession s) {
    EntityManager em = Database.get_instance()
                               .getEm();
    em.getTransaction()
      .begin();
    ESession es = em.merge(s);
    em.remove(es);
    em.getTransaction()
      .commit();
    em.close();
}

/*
    Returns session object from database tied with session_id

 */
private static ESession get_session(String _id) {

    EntityManager em = Database.get_instance()
                               .getEm();

    List res = em.createQuery("SELECT s FROM ESession s WHERE s.id = :sid")
                 .setParameter("sid",
                               _id)
                 .getResultList();
    
    em.close();

    if (res.size() == 0) {
        return null;
    }
    return (ESession) res.get(0);
}

/*
    Validates session and returns SecurityPermission object containing (if valid) Session and
    Response objects
    resides within SecurityPermission.

    * If session is valid then Response object will be null and Session object will be holding
    session
    information.

    * If session is not valid then Session object will be null and Response object will hold
    appropriate
    status code

 */
public static SecurityPermission validate_session(Header _request_header) {
    // Create permission object
    SecurityPermission ret = new SecurityPermission();
    // If request header is not null
    if (_request_header != null) {
        // Get session id from header
        String session_id = _request_header.getSessionId();
        // If session id is not null
        if (session_id != null) {
            // Fetch session object from database and set to the permission object
            ret.session = get_session(session_id);
            // If there is a session and it is authenticated, check for it's expiration
            if (ret.session != null && ret.session.authenticated != null &&
                ret.session.authenticated) {
                long minutes = ret.session.init_ts.until(LocalDateTime.now(),
                                                         ChronoUnit.MINUTES);
                // If it is expired then invalidate session
                if (minutes > Configuration.runtime.session_lifetime) {
                    // Delete session from database
                    invalidate_session(ret.session);
                    ret.session = null;
                    // Set response as 440 - Session Expired
                    ret.response = Response.status(Response.Status.fromStatusCode(440))
                                           .build();
                }
            }
        }
    }
    // If we have not a session object and there is no response set
    if (ret.session == null && ret.response == null)
        // Set respond as 401 - UNAUTHORIZED
        ret.response = Response.status(Response.Status.UNAUTHORIZED)
                               .build();
    // Return security permission
    return ret;
}

public static Response secure_invoke(Header header, boolean _requires_admin_rights,
                                     Function<ESession, Response> valid) {
    SecurityPermission permission = SecurityManager.validate_session(header);

        /*
            Administrator account is only able to view administrator services for strict isolation.
         */
    if (permission.session != null
        // If user is authenticated
        && permission.session.authenticated
        // If service does not requires admin rights and user is not admin
        && ((!_requires_admin_rights && !permission.session.administrator_session)
            // or service requires admin rights and user is admin
            ||
            (_requires_admin_rights && permission.session.administrator_session))) {
        return valid.apply(permission.session);
    } else {
        return Response.status(401)
                       .build();
    }
}
}
