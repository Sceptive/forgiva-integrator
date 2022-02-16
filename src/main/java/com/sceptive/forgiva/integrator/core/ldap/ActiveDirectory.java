package com.sceptive.forgiva.integrator.core.ldap;

import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;

public class ActiveDirectory {



    public static boolean check_compatible(LdapConnection _conn,
                                           String         _dc) throws CursorException,
                                                                      LdapException {

        EntryCursor cursor = _conn.search(_dc,
                                         "(&(&(&(&(samAccountType=805306369)(primaryGroupId=516))(objectCategory=computer)(operatingSystem=*))))",
                                         SearchScope.SUBTREE);
        return cursor.next();
    }


    private static String convert_username_plain(String _username) {
        return (_username.split("@")[0]).split("/")[0];
    }

    public static LdapUserInfo check_user_info(
            LdapConnection _conn,
            String _username,
            String _dc) throws LdapException, CursorException {

        EntryCursor ec2 = _conn.search(_dc,
                                      "(&(samAccountName="+convert_username_plain(_username)+"))",
                                      SearchScope.SUBTREE);
        LdapUserInfo ret = new LdapUserInfo();
        if (ec2.next()) {
            Entry e = ec2.get();

            ret.userDn = e.getDn().toString();

            for (Attribute att : e.getAttributes()) {
                String id = att.getId();
                if (id.equalsIgnoreCase("displayName")) {
                    ret.userFullName = att.getString();
                } else if (id.equalsIgnoreCase("mail")) {
                    ret.userEmail    = att.getString();
                }
            }
        }

        if (ret.userEmail == null && _username.contains("@")) {
            ret.userEmail = _username;
        } else if (ret.userEmail == null && _username.contains("/")) {
            String parts[] = _username.split("/+");
            if (parts.length == 2) {
                ret.userEmail = parts[1]+"@"+parts[0];
            }
        }

        return ret;

    }

    private static byte[] to_unicodePwd(String _value) {

        String quotedPassword = "\"" + _value + "\"";
        char[] unicodePwd = quotedPassword.toCharArray();
        byte[] pwdArray = new byte[unicodePwd.length * 2];
        for (int i = 0; i < unicodePwd.length; i++) {
            pwdArray[i * 2 + 1] = (byte)(unicodePwd[i] >>> '\b');
            pwdArray[i * 2 + 0] = (byte)(unicodePwd[i] & 0xFF);
        }

        return pwdArray;
    }


    public static void update_logonto(LdapConnection _conn,
                                      String _userDn,
                                      String _logonTo) throws Exception {
        Modification changeLogonTo = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE,
                                                             "logonTo",
                                                             _logonTo);
        _conn.modify(_userDn,changeLogonTo);
    }

    public static void change_user_password(LdapUserInfo _info, String _old_password, String _password, LdapConnection _conn) throws LdapException {
        byte[] unicode_old_pw = to_unicodePwd(_old_password);
        byte[] unicode_new_pw = to_unicodePwd(_password);
        //Modification empty          = new Def

        boolean couldChange = false;
        try {
            Modification changePassword = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE,
                                                                  "UnicodePwd",
                                                                  unicode_new_pw);
            _conn.modify(_info.userDn,
                         changePassword);

            couldChange = true;
        } catch (LdapException _ex) {
            Warning.get_instance().print("Active Directory password could not directly replaced.");
        }

        if (!couldChange) {
            Modification deletePassword = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE,
                                                                  "UnicodePwd",
                                                                  unicode_old_pw);
            Modification updatePassword = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE,
                                                                  "UnicodePwd",
                                                                  unicode_new_pw);
            _conn.modify(_info.userDn,
                         deletePassword,
                         updatePassword);
        }

        Info.get_instance().print("%s password changed on AD Server over LDAPS",_info.userDn);
        //this.ldapContext.modifyAttributes("cn=" + username + "," + this.authLdapSearchBase, mods);
    }
}
