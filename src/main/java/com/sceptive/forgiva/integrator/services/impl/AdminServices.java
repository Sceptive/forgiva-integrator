package com.sceptive.forgiva.integrator.services.impl;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.StatCollector;
import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.core.db.objects.*;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Debug;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import org.reflections.Reflections;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSFileStore;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.net.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AdminServices {


private static long count_all_records() {

    long record_count = 0;


    EntityManager em = Database.get_instance().getEm();

    for (Class c: Database.entity_classes) {

        String class_name = c.getSimpleName();

        Long count = (Long) em.createQuery(
                "SELECT COUNT(m) FROM  "+class_name+" m")
                                   .getSingleResult();
        record_count += count.longValue();
    }

    return record_count;
}

private static Double cpu_usage_between_datetime(
        LocalDateTime _start,
        LocalDateTime _end
                                                                                      ) {
    EntityManager em = Database.get_instance().getEm();


    try {
        Double val = (Double) em.createQuery("SELECT AVG(m.value) FROM EStatistics m " +
                                                " WHERE m.action_time > :start AND m.action_time < :end  " +
                                                " AND m.key = :key GROUP BY m.key")
                                .setParameter("start",
                                              _start)
                                .setParameter("end",
                                              _end)
                                .setParameter("key",
                                              "CPU_USAGE_PERC")
                                .getSingleResult();
       return val;
    } catch (Exception ex) {
        Debug.get_instance().print("No result for %s - %s for cpu usage", _start,_end);
    }

    return null;

}

public static Response get_resource_usages(PostAdminResourceusageRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        PostAdminResourceusageResponse resp = new PostAdminResourceusageResponse();

        SystemInfo si = new SystemInfo();
        GlobalMemory     memory    = si.getHardware().getMemory();



        long disks_used = 0;
        long disks_total = 0;

        for (OSFileStore osfs : si.getOperatingSystem().getFileSystem().getFileStores()) {
            long total = osfs.getTotalSpace();

            disks_total += total;
            disks_used  += osfs.getFreeSpace();
        }

        resp.setTotalDiskspace(Long.valueOf(disks_total).toString());
        resp.setUsedDiskspace (Long.valueOf(disks_used).toString());


        resp.setCpuUsagePercentage(String.format("%.1f",
                                                 StatCollector.get_instance().get_current_cpu_usage_percentage()));


        resp.setTotalMemory(Long.valueOf(memory.getTotal()).toString());
        resp.setUsedMemory(Long.valueOf((memory.getTotal()-memory.getAvailable())).toString());

        EntityManager em = Database.get_instance().getEm();

        Long user_count = (Long) em.createQuery(
                "SELECT COUNT(m) FROM EUser m ")
                                    .getSingleResult();

        Long unique_hosts = (Long) em.createQuery("SELECT COUNT(distinct m.host) FROM EMetadata m")
             .getSingleResult();
        resp.setTotalUsers(user_count.toString());
        resp.setTotalUniqueHosts(unique_hosts.toString());


        final long then = System.nanoTime();
        em.createQuery("SELECT 1 FROM EMetadata m").getResultList();
        final long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then);

        resp.setServerPingTime(String.valueOf(millis));
        resp.setDatabaseSize(String.valueOf(count_all_records()));


        LocalDateTime now               = LocalDateTime.now().withMinute(0).withSecond(0);
        final int byhour_granularity_in_minutes   = 15;

        for (int i=24*60;i>0;i-=byhour_granularity_in_minutes) {

            LocalDateTime start = now.minusMinutes(i+byhour_granularity_in_minutes);
                // Hours(i+1).withMinute(0).withSecond(0);
            LocalDateTime end   = now.minusMinutes(i);

            Double cpuUsageByHour = cpu_usage_between_datetime(start,end);

            if (cpuUsageByHour != null) {
                resp.addCpuUsageByHourItem(new PostAdminResourceusageResponseCpuUsageByHour()
                        .hour(String.valueOf(start.toString()))
                        .percentage(cpuUsageByHour.intValue()));

            }

        }



        final int byday_granularity_in_minutes   = 60;

        for (int i=24*60*7;i>0;i-=byday_granularity_in_minutes) {

            LocalDateTime start = now.minusMinutes(i+byday_granularity_in_minutes);
                // Hours(i+1).withMinute(0).withSecond(0);
            LocalDateTime end   = now.minusMinutes(i);

            Double cpuUsageByDay = cpu_usage_between_datetime(start,end);

            if (cpuUsageByDay != null) {
                resp.addCpuUsageByDayItem(new PostAdminResourceusageResponseCpuUsageByDay()
                                         .day(String.valueOf(start.toString()))
                                          .percentage(cpuUsageByDay.intValue()));

            }

        }


        // TODO finalize the rest

        return Response.ok().entity(resp).build();
    });
}

private static String formatElapsedSecs(long secs) {
    long eTime = secs;
    final long days = TimeUnit.SECONDS.toDays(eTime);
    eTime -= TimeUnit.DAYS.toSeconds(days);
    final long hr = TimeUnit.SECONDS.toHours(eTime);
    eTime -= TimeUnit.HOURS.toSeconds(hr);
    final long min = TimeUnit.SECONDS.toMinutes(eTime);
    eTime -= TimeUnit.MINUTES.toSeconds(min);
    final long sec = eTime;
    return String.format("%d days, %02d hour(s) %02d minute(s) %02d second(s)", days, hr, min, sec);
}

public static Response get_system_information(PostAdminSysteminformationRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        PostAdminSysteminformationResponse resp = new PostAdminSysteminformationResponse();
        SystemInfo si = new SystemInfo();
        try {
            // Setting host name
            resp.setHostname(InetAddress.getLocalHost().getHostName());

            Vector<PostAdminSysteminformationResponseIpConfiguration> ipconf = new Vector<>(0);

            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();

                PostAdminSysteminformationResponseIpConfiguration ic = new PostAdminSysteminformationResponseIpConfiguration();

                ic.setDevice(iface.getDisplayName());

                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        ic.setIpv4(inetAddr.getHostAddress());
                    }
                }

                byte[] mac = iface.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s",
                                                mac[i],
                                                (i < mac.length - 1) ? "-" : ""));
                    }
                }

                ic.setMac(sb.toString());
                ipconf.add(ic);

            }
            // Setting IP configurations
            resp.setIpConfiguration(ipconf);


        }
        catch (UnknownHostException _e) {
            Warning.get_instance().print(_e);
        }
        catch (SocketException _e) {
            Warning.get_instance().print(_e);
        }
        // Setting operating system
        resp.setOperatingSystem(String.format("%s - %s - %s",System.getProperty("os.name"),
                                              System.getProperty("os.arch"), System.getProperty("os.version")));

        // Setting processor information
        CentralProcessor proc = si.getHardware().getProcessor();

        resp.setProcessorInformation(String.format("%s - %s - %s (%s cores)",
                                                   proc.getProcessorIdentifier().getName(),
                                                   proc.getProcessorIdentifier().getMicroarchitecture(),
                                                   proc.getProcessorIdentifier().getFamily(),
                                                   proc.getPhysicalProcessorCount()
                                                   ));
        // Setting time
        resp.setTimeOnSystem(new Timestamp(System.currentTimeMillis()).toString());
        // Setting java environment
        resp.setJavaEnvironment(String.format("%s : %s",
                                              System.getProperty("java.vm.name"),
                                              System.getProperty("java.vm.version")));
        // Forgiva Version
        resp.setForgivaVersion(Constants.FORGIVA_VERSION);
        // System uptime
        resp.setSystemUptime(formatElapsedSecs(si.getOperatingSystem().getSystemUptime()));


        PostAdminSysteminformationResponseEnabledFeatures features = new PostAdminSysteminformationResponseEnabledFeatures();

        features.activeDirectory(false);





        resp.setEnabledFeatures(features);

        return Response.ok().entity(resp).build();
    });
}

private static boolean check_user_data(User _user) {
    return _user != null && _user.getUserName() != null && _user.getEmail() != null  && _user.getFullName() != null;
}

public static Response add_user(PostAdminUserAddRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {

            if (!check_user_data(_request.getUser()) || _request.getPassword() == null ||
                !Common.check_if_hex(_request.getPassword())) {

                throw new InvalidValueException("Invalid data");
            }

            EntityManager em = Database.get_instance().getEm();
            // Check if same user name or email exists
            Long count = (Long) em.createQuery(
                    "SELECT COUNT(u) FROM EUser u WHERE u.username = :username " +
                    " OR u.email = :email")
                                  .setParameter("username", _request.getUser().getUserName())
                                  .setParameter("email", _request.getUser().getEmail())
                                  .getSingleResult();
            // If there is just throw an exception and exit.
            if (count > 0) {
                throw new InvalidValueException("Same user name or email already exists");
            }

            EUser new_user = new EUser();

            new_user.fullname = _request.getUser().getFullName();
            new_user.username = _request.getUser().getUserName();
            new_user.email    = _request.getUser().getEmail();
            new_user.externalUser = false;
            byte[] decrypted_password_hash = Asymmetric
                    .decrypt_using_session(Common.decodeHex(_request.getPassword()), session);


//            Info.get_instance().print("SAVE Decrypted user / passwd: %s / %s", new_user.username, Common.encodeHex(decrypted_password_hash));

            new_user.password = Common.encodeHex(FHashGenerator.hash_for_model(
                    Configuration.runtime.security_pw_hashing_model, decrypted_password_hash,
                    Common.decodeHex(Configuration.runtime.security_pw_hashing_salt)));

            if (_request.getUserGroupId() != null) {
                new_user.groupId = _request.getUserGroupId().longValue();
            } else {
                // If there is no groupId defined set it as default user group
                new_user.groupId = 0;
            }

            em.getTransaction().begin();
            em.persist(new_user);
            em.getTransaction().commit();
            em.close();
            // Create default metadata group for user
            CommonOperations.create_default_metadatagroup_for_user(new_user.id);

            ret.addAffectedRecordsItem(String.valueOf(new_user.id));

        }
        catch (Exception ex) {
           // Warning.get_instance().print(ex);
            ret.error(ex.getMessage());
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response update_user(PostAdminUserUpdateRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {
            User req_user = _request.getUser();

            if (!check_user_data(req_user) ||
                    req_user.getUserId() == null) {
                throw new InvalidValueException("Invalid data");
            }


                Integer s_u_id = req_user.getUserId();


                EntityManager em = Database.get_instance().getEm();

                Query query = em.createQuery("SELECT u FROM EUser u WHERE u.id <> :userId AND u.username = :username",
                                         EUser.class).setParameter("userId", s_u_id)
                                        .setParameter("username",req_user.getUserName());
                List<EUser> res = query.getResultList();

                if (res.size() > 0) {
                    throw new InvalidValueException("Same user name exists");
                }

                query = em.createQuery("SELECT u FROM EUser u WHERE u.id = :userId ",
                                             EUser.class).setParameter("userId", s_u_id);
                res = query.getResultList();

                if (res.size() == 1) {
                    EUser user = res.get(0);
                    user.fullname = req_user.getFullName();
                    user.email    = req_user.getEmail();
                    user.username = req_user.getUserName();

                    em.getTransaction().begin();
                    em.merge(user);

                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(s_u_id.toString());
                } else {
                    ret.error("User does not exists.");
                }

        }
        catch (Exception e) {

            ret.error(e.getMessage());
        }

        return Response.ok().entity(ret).build();
    });
}
public static Response remove_user(PostAdminUserRemoveRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {
            if (_request.getUserId() != null) {

                long u_id = Long.parseLong(_request.getUserId());

                EntityManager em = Database.get_instance().getEm();
                Query query =
                        em.createQuery("SELECT u FROM EUser u WHERE u.id = :uId ", EUser.class)
                          .setParameter("uId", u_id);
                List res = query.getResultList();

                if (res.size() == 1) {
                    em.getTransaction().begin();
                    em.remove(res.get(0));
                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(_request.getUserId());
                } else {
                    ret.error("User does not exists.");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}



public static EUserGroup add_usergroup_ex(String _groupName,
                                          String _description,
                                          String _parentGroupId) throws Exception {

    EUserGroup new_usergroup = new EUserGroup();
    new_usergroup.userGroupName = _groupName;

    if (_description != null) {
        new_usergroup.groupDescription = _description;
    }

    if (_parentGroupId != null) {
        new_usergroup.parentUserGroupId =
                Long.parseLong(_parentGroupId);
    }

    EntityManager em = Database.get_instance().getEm();
    em.getTransaction().begin();
    em.persist(new_usergroup);
    em.getTransaction().commit();
    em.close();
    return new_usergroup;
}

public static Response add_usergroup(PostAdminUsergroupAddRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {

            if (_request.getGroup() == null || _request.getGroup().getUserGroupName() == null) {

                throw new InvalidValueException("Invalid data");
            }

            EUserGroup new_usergroup = add_usergroup_ex(_request.getGroup().getUserGroupName(),
                                                        _request.getGroup().getUserGroupDescription(),
                                                        _request.getGroup().getParentUserGroupId());


            ret.addAffectedRecordsItem(String.valueOf(new_usergroup.id));

        }
        catch (Exception ex) {
            //  Info.get_instance().print(ex);
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}



private static int remove_usergroup_internal(Long _userGroupId) {

    int ret = 0;

    EntityManager em = Database.get_instance().getEm();
    Query sub_groups_query =
            em.createQuery("SELECT u FROM EUserGroup u WHERE " + " u.parentUserGroupId = :ugId",
                           EUserGroup.class).setParameter("ugId", _userGroupId);
    List<EUserGroup> sub_groups = sub_groups_query.getResultList();

    for (EUserGroup sub_group : sub_groups) {
        ret += remove_usergroup_internal(sub_group.id);
    }

    Query query = em.createQuery("DELETE FROM EUserGroup u WHERE u.id = :uId ")
                    .setParameter("uId", _userGroupId);
    em.getTransaction().begin();
    int rowCount = query.executeUpdate();
    em.getTransaction().commit();
    // If any usergroup is deleted then remove the users related to it
    if (rowCount > 0) {

        query = em.createQuery("DELETE FROM EUser u WHERE u.groupId = :ugId ")
                  .setParameter("ugId", _userGroupId);
        em.getTransaction().begin();
        rowCount = query.executeUpdate();
        em.getTransaction().commit();

        ret += rowCount;
    }

    em.close();

    return ret;
}

public static Response remove_usergroup(PostAdminUsergroupRemoveRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {
            if (_request.getUserGroupId() != null) {

                long ug_id = Long.parseLong(_request.getUserGroupId());

                // Not allowed to remove root user group
                if (ug_id != 0) {
                    int count = remove_usergroup_internal(ug_id);
                    ret.info(String.format("%d user(s) removed.", count));
                }
            }
        }
        catch (Exception e) {
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response update_usergroup(PostAdminUsergroupUpdateRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {
            UserGroup req_usergroup = _request.getUserGroup();
            if (req_usergroup != null && req_usergroup.getUserGroupId() != null) {

                String s_ug_id = req_usergroup.getUserGroupId();
                long   l_ug_id = Long.parseLong(s_ug_id);

                EntityManager em = Database.get_instance().getEm();
                Query query = em.createQuery("SELECT u FROM EUserGroup u WHERE u.id = :ugId ",
                                             EUserGroup.class).setParameter("ugId", l_ug_id);
                List<EUserGroup> res = query.getResultList();

                if (res.size() == 1) {
                    EUserGroup userGroup = res.get(0);
                    if (req_usergroup.getUserGroupName() != null)
                        userGroup.userGroupName = req_usergroup.getUserGroupName();
                    if (req_usergroup.getParentUserGroupId() != null)
                        userGroup.parentUserGroupId =
                                Long.parseLong(req_usergroup.getParentUserGroupId());
                    if (req_usergroup.getUserGroupDescription() != null)
                        userGroup.groupDescription = req_usergroup.getUserGroupDescription();
                    em.getTransaction().begin();
                    em.merge(userGroup);

                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(s_ug_id);
                } else {
                    ret.error("User group does not exists.");
                }
            }
        }
        catch (Exception e) {
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

private static LocalDateTime get_last_login(Long user_id) {

    LocalDateTime ret = null;

    EntityManager em = Database.get_instance().getEm();

    List<ESession> sess =
            em.createQuery("SELECT s FROM ESession s WHERE s.user_id = :userId " +
                           "    AND s.authenticated = true ORDER BY s.auth_ts DESC",
                           ESession.class)
              .setParameter("userId",user_id)
              .setMaxResults(1).getResultList();
    for (ESession session : sess) {
        ret = session.auth_ts;
    }

    return ret;
}

public static Response get_users_by_usergroup(PostAdminUserByusergroupRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        PostAdminUserByusergroupResponse _response = new PostAdminUserByusergroupResponse();
        try {

            if (_request.getUserGroupId() == null || _request.getStartIdx() == null ||
                _request.getCount() == null) {
                throw new InvalidValueException(
                        "User group id, start index and count cannot be null");
            }

            int start_idx = _request.getStartIdx();
            int count     = _request.getCount();

            EntityManager em = Database.get_instance().getEm();

            Long g_id = Long.parseLong(_request.getUserGroupId());

            Long u_count = (Long) em.createQuery(
                    "SELECT COUNT(u) FROM EUser u WHERE u.groupId = :groupId")
                                  .setParameter("groupId",g_id)
                                  .getSingleResult();

            _response.setTotalUserCount(u_count.intValue());

            List<EUser> users =
                    em.createQuery("SELECT u FROM EUser u WHERE u.groupId = :groupId ORDER BY u.id",
                                   EUser.class)
                      .setParameter("groupId", Long.parseLong(_request.getUserGroupId()))
                      .setFirstResult(start_idx).setMaxResults(count)
                      .getResultList();

            for (EUser user : users) {
                LocalDateTime ldt = get_last_login(user.id);

                _response.addUsersItem(new User().email(user.email).userName(user.username)
                                                 .userId(Integer.parseInt(String.valueOf(user.id)))
                                                 .fullName(user.fullname).externalUser(user.externalUser)
                                       .lastLogin(com.sceptive.forgiva.integrator.core.util.Common.format_local_date_time(ldt))
                                      );
            }

        }
        catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(_response).build();
    });
}

public static Response get_usergroups(PostAdminUsergroupsRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        PostAdminUsergroupsResponse ret = new PostAdminUsergroupsResponse();
        try {

            EntityManager em = Database.get_instance().getEm();
            Query query =
                    em.createQuery("SELECT u FROM EUserGroup u WHERE u.parentUserGroupId = 0 ORDER BY u.id ", EUserGroup.class);
            List<EUserGroup> res = query.getResultList();
            for (EUserGroup eu : res) {
                ret.addUserGroupsItem(
                        new UserGroup().parentUserGroupId(String.valueOf(eu.parentUserGroupId))
                                       .userGroupDescription(eu.groupDescription)
                                       .userGroupName(eu.userGroupName)
                                       .userGroupId(String.valueOf(eu.id)));
                Query sub_group_query = em.createQuery(" SELECT u FROM EUserGroup u WHERE u.parentUserGroupId = :id", EUserGroup.class)
                      .setParameter("id", eu.id);
                List<EUserGroup> sub_groups = sub_group_query.getResultList();
                for (EUserGroup eu_sub : sub_groups) {
                    ret.addUserGroupsItem(
                            new UserGroup().parentUserGroupId(String.valueOf(eu_sub.parentUserGroupId))
                                           .userGroupDescription(eu_sub.groupDescription)
                                           .userGroupName(eu_sub.userGroupName)
                                           .userGroupId(String.valueOf(eu_sub.id)));
                }
            }

        }
        catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response add_host(PostAdminHostAddRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {
            if (_request.getHost() == null || _request.getHost().getHostName() == null || _request.getHost().getDnsName() == null) {
                throw new InvalidValueException("Invalid data");
            }

            EHost new_host = new EHost();
            new_host.hostName = _request.getHost().getHostName();
            new_host.dnsName = _request.getHost().getDnsName();

            EntityManager em = Database.get_instance().getEm();
            // Check if same host name or dns name exists
            Long count = (Long) em.createQuery(
                    "SELECT COUNT(h) FROM EHost h WHERE h.hostName = :hostName " +
                    " OR h.dnsName = :dnsName")
                                  .setParameter("hostName", _request.getHost().getHostName())
                                  .setParameter("dnsName", _request.getHost().getDnsName())
                                  .getSingleResult();
            // If there is just throw an exception and exit.
            if (count > 0) {
                throw new InvalidValueException("Same host name or same dns name already exists");
            }


            if (_request.getHost().getOperatingSystemName() != null) {
                new_host.operatingSystemName = _request.getHost().getOperatingSystemName();
            }

            if (_request.getHost().getOperatingSystemVersion() != null) {
                new_host.operatingSystemVersion = _request.getHost().getOperatingSystemVersion();
            }

            if (_request.getHost().getDescription() != null) {
                new_host.description = _request.getHost().getDescription();
            }

            em.getTransaction().begin();
            em.persist(new_host);
            em.getTransaction().commit();

            ret.addAffectedRecordsItem(String.valueOf(new_host.hostId));

        } catch (Exception ex) {
              Info.get_instance().print(ex);
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });


}



public static Response add_application(PostAdminApplicationAddRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {
            if (_request.getApplication() == null || _request.getApplication().getApplicationName() == null
                || _request.getApplication().getHostId() == null) {
                throw new InvalidValueException("Invalid data");
            }

            EApplication new_application = new EApplication();
            new_application.applicationName = _request.getApplication().getApplicationName();
            new_application.hostId = _request.getApplication().getHostId();

            EntityManager em = Database.get_instance().getEm();
            // Check if same user name or email exists
            Long count = (Long) em.createQuery(
                    "SELECT COUNT(a) FROM EApplication a WHERE a.applicationName = :applicationName " +
                    " AND a.hostId = :hostId")
                                  .setParameter("applicationName", _request.getApplication().getApplicationName())
                                  .setParameter("hostId", _request.getApplication().getHostId())
                                  .getSingleResult();
            // If there is just throw an exception and exit.
            if (count > 0) {
                throw new InvalidValueException("Same application name on the same host IP already exists");
            }

            if (_request.getApplication().getVersion() != null) {
                new_application.version = _request.getApplication().getVersion();
            }

            if (_request.getApplication().getPortRunning() != null) {
                new_application.portRunning = _request.getApplication().getPortRunning();
            }

            if (_request.getApplication().getLoginPageURL() != null) {
                new_application.loginPageURL = _request.getApplication().getLoginPageURL();
            }

            if (_request.getApplication().getHomePageURL() != null) {
                new_application.homePageURL = _request.getApplication().getHomePageURL();
            }

            if (_request.getApplication().getMonitoringFingerprint() != null) {
                new_application.monitoringFingerprint = _request.getApplication().getMonitoringFingerprint();
            }

            em.getTransaction().begin();
            em.persist(new_application);
            em.getTransaction().commit();

            ret.addAffectedRecordsItem(String.valueOf(new_application.applicationId));

        } catch (Exception ex) {
            //  Info.get_instance().print(ex);
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response remove_application(PostAdminApplicationRemoveRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {
            if (_request.getApplicationId() != null) {
                long a_id = Long.parseLong(_request.getApplicationId());

                EntityManager em = Database.get_instance().getEm();
                Query query =
                        em.createQuery("SELECT a FROM EApplication a WHERE a.applicationId = :aId ", EApplication.class)
                          .setParameter("aId", a_id);
                List res = query.getResultList();

                if (res.size() == 1) {
                    em.getTransaction().begin();
                    em.remove(res.get(0));
                    em.getTransaction().commit();
                    ret.addAffectedRecordsItem(_request.getApplicationId());
                } else {
                    ret.error("Application does not exists.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response update_application(PostAdminApplicationUpdateRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {
            Application req_application = _request.getApplication();
            if (req_application != null && req_application.getApplicationId() != null) {

                int a_id = req_application.getApplicationId();

                EntityManager em = Database.get_instance().getEm();
                Query query = em.createQuery("SELECT a FROM EApplication a WHERE a.applicationId = :aId ",
                                             EHost.class).setParameter("aId", a_id);

                List<EApplication> res = query.getResultList();

                if (res.size() == 1) {
                    EApplication application = res.get(0);
                    if (req_application.getHostId() != null)
                        application.hostId = req_application.getHostId();
                    if (req_application.getApplicationName() != null)
                        application.applicationName = req_application.getApplicationName();
                    if (req_application.getVersion() != null)
                        application.version = req_application.getVersion();
                    if (req_application.getPortRunning() != null)
                        application.portRunning = req_application.getPortRunning();
                    if (req_application.getLoginPageURL() != null)
                        application.loginPageURL = req_application.getLoginPageURL();
                    if (req_application.getHomePageURL() != null)
                        application.homePageURL = req_application.getHomePageURL();
                    if (req_application.getMonitoringFingerprint() != null)
                        application.monitoringFingerprint = req_application.getMonitoringFingerprint();
                    em.getTransaction().begin();
                    em.merge(application);

                    em.getTransaction().commit();
                    ret.addAffectedRecordsItem(String.valueOf(a_id));
                } else {
                    ret.error("Host does not exists.");
                }
            }
        } catch (Exception e) {
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response get_applications_by_host(PostAdminApplicationsRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {

        PostAdminApplicationsResponse _response = new PostAdminApplicationsResponse();

        try {
            EntityManager em = Database.get_instance().getEm();
            List<EApplication> res = new ArrayList<>();
            //If Host id is null getting whole applications
            if (_request.getHostId() == null) {

                Query query =
                        em.createQuery("SELECT a FROM EApplication a", EApplication.class);

                res = query.getResultList();

            }
            //If there is a specified Host id just fetch it's applications.
            else {
                long h_id = (long) _request.getHostId();
                Query query =
                        em.createQuery("SELECT a FROM EApplication a WHERE a.hostId = :hostId", EApplication.class)
                          .setParameter("hostId", h_id);
                res = query.getResultList();

            }
            if (res.size() > 0) {
                for (EApplication ea : res) {
                    _response.addApplicationsItem(
                            new Application().applicationId((int) ea.applicationId)
                                             .hostId((int) ea.hostId)
                                             .applicationName(ea.applicationName)
                                             .version(ea.version)
                                             .portRunning(ea.portRunning)
                                             .loginPageURL(ea.loginPageURL)
                                             .homePageURL(ea.homePageURL)
                                             .monitoringFingerprint(ea.monitoringFingerprint));
                }

            } else {

                Info.get_instance().print("There is no application(s) in this host: " + _request.getHostId());
            }
        } catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(_response).build();
    });
}

public static Response remove_host(PostAdminHostRemoveRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();
        try {
            if (_request.getHostId() != null) {
                long h_id = Long.parseLong(_request.getHostId());

                if (h_id != 0) {

                    EntityManager em = Database.get_instance().getEm();

                    // If any host is deleted then remove the applications related to it
                    Query app_query =
                            em.createQuery("SELECT a FROM EApplication a WHERE " + " a.hostId = :hId",
                                           EApplication.class).setParameter("hId", h_id);
                    List<EApplication> apps = app_query.getResultList();
                    if (apps.size() > 0) {
                        for (EApplication app : apps) {
                            em.getTransaction().begin();
                            em.remove(app);
                            em.getTransaction().commit();
                        }
                    } else {
                        ret.info("There is no application(s) already");
                    }

                    Query query =
                            em.createQuery("SELECT h FROM EHost h WHERE h.hostId = :hId ", EHost.class)
                              .setParameter("hId", h_id);
                    List res = query.getResultList();

                    if (res.size() == 1) {
                        em.getTransaction().begin();
                        em.remove(res.get(0));
                        em.getTransaction().commit();
                        ret.addAffectedRecordsItem(_request.getHostId());
                    } else {
                        ret.error("Host does not exists.");
                    }
                } else {
                    ret.error("Host does not exists.");
                }
            }
        } catch (Exception e) {
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response update_host(PostAdminHostUpdateRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        OperationResult ret = new OperationResult();

        try {
            Host req_host = _request.getHost();
            if (req_host != null && req_host.getHostId() != null) {

                int h_id = req_host.getHostId();

                EntityManager em = Database.get_instance().getEm();
                Query query = em.createQuery("SELECT h FROM EHost h WHERE h.hostId = :hId ",
                                             EHost.class).setParameter("hId", h_id);

                List<EHost> res = query.getResultList();

                if (res.size() == 1) {
                    EHost host = res.get(0);
                    if (req_host.getHostName() != null)
                        host.hostName = req_host.getHostName();
                    if (req_host.getDnsName() != null)
                        host.dnsName = req_host.getDnsName();
                    if (req_host.getOperatingSystemName() != null)
                        host.operatingSystemName = req_host.getOperatingSystemName();
                    if (req_host.getOperatingSystemVersion() != null)
                        host.operatingSystemVersion = req_host.getOperatingSystemVersion();
                    if (req_host.getDescription() != null)
                        host.description = req_host.getDescription();
                    em.getTransaction().begin();
                    em.merge(host);

                    em.getTransaction().commit();
                    ret.addAffectedRecordsItem(String.valueOf(h_id));
                } else {
                    ret.error("Host does not exists.");
                }
            }
        } catch (Exception e) {
            ret.error("Invalid data");
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response get_hosts(PostAdminHostsRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        PostAdminHostsResponse ret = new PostAdminHostsResponse();

        try {
            EntityManager em = Database.get_instance().getEm();
            Query query =
                    em.createQuery("SELECT h FROM EHost h", EHost.class);


            List<EHost> res = query.getResultList();
            for (EHost eh : res) {
                ret.addHostsItem(
                        new Host().hostId((int) eh.hostId)
                                  .hostName(eh.hostName)
                                  .dnsName(eh.dnsName)
                                  .operatingSystemName(eh.operatingSystemName)
                                  .operatingSystemVersion(eh.operatingSystemVersion)
                                  .description(eh.description));
            }
        } catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(ret).build();
    });
}

public static Response get_reports(PostAdminReportsRequest _request) {
    return SecurityManager.secure_invoke(_request.getHeader(), true, (session) -> {
        // TODO Will be implemented
        return Response.ok().build();
    });
}
}
