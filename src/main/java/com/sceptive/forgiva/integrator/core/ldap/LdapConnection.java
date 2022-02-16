package com.sceptive.forgiva.integrator.core.ldap;

import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import java.io.IOException;

public class LdapConnection implements AutoCloseable {

LdapServerType                                      server_type = LdapServerType.OTHER;
org.apache.directory.ldap.client.api.LdapConnection connection  = null;
boolean                                             ssl         = false;
boolean                                             authorized  = false;
String                                              rootDn      = null;
LdapUserInfo                                        userInfo    = null;

public LdapConnection(String _ldap_host, String _ldap_domain, String _username, String _password) {
    ssl = _ldap_host.toLowerCase()
                    .startsWith("ldaps://");
    if (!ssl && !_ldap_host.toLowerCase()
                           .startsWith("ldap://")) {
        Warning.get_instance()
               .print("Invalid LDAP Host definition. It should start with ldap:// or ldaps:// : " +
                      "%s ",
                      _ldap_host);
        return;
    }
    String[] host_parts = _ldap_host.split("://");
    if (host_parts.length != 2) {
        Warning.get_instance()
               .print("Invalid LDAP Host definition : %s ",
                      _ldap_host);
        return;
    }
    LdapConnectionConfig lcc = new LdapConnectionConfig();
    rootDn = convert_username_to_dn(_username,
                                    _ldap_domain);
    String ldap_host_name = host_parts[1];
    lcc.setLdapHost(ldap_host_name);
    if (ssl) {
        lcc.setUseSsl(true);
        lcc.setLdapPort(636);
    } else {
        lcc.setLdapPort(389);
    }
    lcc.setName(_username);
    lcc.setCredentials(_password);
    try {
        connection = new LdapNetworkConnection(lcc);
        connection.bind();

        authorized = true;
        if (ActiveDirectory.check_compatible(connection,
                                             rootDn)) {
            server_type = LdapServerType.ACTIVE_DIRECTORY;
            userInfo    = ActiveDirectory.check_user_info(connection,
                                                          _username,
                                                          rootDn);
        }
    }
    catch (LdapAuthenticationException _e) {
        Warning.get_instance()
               .print("Invalid credentials for %s ",
                      _username);
        authorized = false;
    }
    catch (LdapInvalidDnException _invalidDn) {
        Warning.get_instance()
               .print(_invalidDn);
    }
    catch (LdapException _ldape) {
        Warning.get_instance()
               .print(_ldape);
    }
    catch (CursorException _e) {
        Warning.get_instance()
               .print(_e);
    }
}

private static String convert_domain_to_dcs(String _domain) {
    StringBuilder ret      = new StringBuilder();
    String[]      dc_parts = _domain.split("\\.");
    for (String dc_part : dc_parts) {
        ret.append("DC=")
           .append(dc_part)
           .append(",");
    }
    if (ret.length() > 0) {
        ret = new StringBuilder(ret.substring(0,
                                              ret.length() - 1));
    }
    return ret.toString();
}

/**
 * Converts abc@domain.com or domain.com/abc type of usernames
 * into DC=domain,DC=com type strings.
 *
 * @param _username User name for login
 * @param _domain   Domain for login suchs as domain.com, abc.net etc.
 * @return
 */
private static String convert_username_to_dn(String _username, String _domain) {
    if (_username.contains("/")) {
        return convert_domain_to_dcs(_username.split("/")[0]);
    } else if (_username.contains("@")) {
        String[] domain_defs = _username.split("@");
        if (domain_defs.length > 1) {
            return convert_domain_to_dcs(domain_defs[1]);
        }
    } else {
        return convert_domain_to_dcs(_domain);
    }
    return null;
}

public boolean change_user_password(String _old_password, String _new_password)
        throws InvalidValueException {
    if (server_type != LdapServerType.ACTIVE_DIRECTORY) {
        return false;
    }
    try {
        ActiveDirectory.change_user_password(userInfo,
                                             _old_password,
                                             _new_password,
                                             connection);
    }
    catch (LdapException _e) {
        throw new InvalidValueException(_e.getMessage());
    }
    return true;
}

public void close() {
    try {
        if (connection != null) {
            connection.close();
        }
    }
    catch (IOException _e) {
        Warning.get_instance()
               .print(_e);
    }
}

public LdapServerType getServer_type() {
    return server_type;
}

public org.apache.directory.ldap.client.api.LdapConnection getConnection() {
    return connection;
}

public boolean isAuthorized() {
    return authorized;
}

public LdapUserInfo getUserInfo() {
    return userInfo;
}

public String getRootDn() {
    return rootDn;
}

public static enum LdapServerType {
    ACTIVE_DIRECTORY, OTHER
}
}
