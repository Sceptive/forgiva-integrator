package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadataGroup;
import com.sceptive.forgiva.integrator.core.util.Common;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WSBackup {

    private static String encapsulate_backup_data(BackupData _data) throws IOException {

        String _data_as_string = Common.jckOMapper.writeValueAsString(_data);

        return Common.compress_string(_data_as_string);

    }


    private static BackupData decapsulate_backup_data(String _enc_data) throws IOException {

        String _string_as_data = Common.decompress_string(_enc_data);

        return (BackupData) Common.jckOMapper.readValue(_string_as_data,BackupData.class);

    }

    public static Response export_service(PostUserBackupExportRequest _request)
             {

        return SecurityManager
                .secure_invoke(_request.getHeader(), false, (session) -> {
                    OperationResult response = new OperationResult();
                    EntityManager em = Database.get_instance().getEm();

                    try {


                        long group_id = _request.getMetadataGroupId() != null
                                            ? Long.parseLong(_request.getMetadataGroupId())
                                            : 0;

                        BackupData ret = new BackupData() ;

                        List<EMetadataGroup> groups;
                        List<EMetadata>      metadatas = new ArrayList<>();

                        Query groups_query =
                                (_request.getMetadataGroupId() == null ?
                                em.createQuery(
                                "SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.parentGroupId = 0",
                                        EMetadataGroup.class)
                                        .setParameter("userId", session.user_id)
                                :
                                em.createQuery("SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.id = :gId",
                                        EMetadataGroup.class)
                                        .setParameter("userId", session.user_id)
                                        .setParameter("gId", group_id));

                        groups = groups_query.getResultList();

                        ret.groups = new EMetadataGroup[groups.size()];

                        Info.get_instance().print("Exporting %d groups...",groups.size());

                        long group_count    = 0;
                        long metadata_count = 0;

                        for (int i=0;i<ret.groups.length;i++) {

                            EMetadataGroup m = groups.get(i);
                            m.userId = 0;

                            ret.groups[i] = m;

                            Query _metadatasQuery           =
                                    em.createQuery("SELECT m FROM EMetadata m WHERE " +
                                                            " m.userId = :userId AND m.metadataGroupId = :gId",
                                                    EMetadata.class)
                                            .setParameter("userId", session.user_id)
                                            .setParameter("gId",m.id);

                            List<EMetadata>  group_metadatas = _metadatasQuery.getResultList();
                            for (EMetadata emeta : group_metadatas) {
                                emeta.userId = 0;

                                metadatas.add(emeta);

                                metadata_count++;
                            }
                            group_count++;

                            Info.get_instance().print("Exported %d of %d groups, with total %d metadata.",
                                            group_count,groups.size(),metadata_count);

                        }
                        ret.metadatas = new EMetadata[metadatas.size()];
                        ret.metadatas = metadatas.toArray(ret.metadatas);

                        response.setResultData(encapsulate_backup_data(ret));

                    } catch (Exception e) {
                        Warning.get_instance().print(e);
                        response.setError(e.getMessage());
                    }

                    em.close();
                    return Response.ok().entity(response).build();
                });
    }


    public static Response import_service(PostUserBackupImportRequest _request)
    {

        return SecurityManager
                .secure_invoke(_request.getHeader(), false, (session) -> {
                    OperationResult response = new OperationResult();
                    EntityManager em = Database.get_instance().getEm();

                    try {

                        BackupData import_data = decapsulate_backup_data(_request.getData());

                        Info.get_instance().print("Importing %d groups...",import_data.groups.length);

                        long group_count    = 0;
                        long metadata_count = 0;

                        for (EMetadataGroup emg : import_data.groups) {

                            EMetadataGroup new_emg      = new EMetadataGroup();
                            new_emg.groupName           = emg.groupName;
                            new_emg.groupDescription    = emg.groupDescription;
                            new_emg.parentGroupId       = 0;
                            new_emg.userId              = session.user_id;

                            em.getTransaction().begin();
                            em.persist(new_emg);
                            em.getTransaction().commit();

                            for (EMetadata emeta : import_data.metadatas) {

                                if (emeta.metadataGroupId == emg.id) {

                                    EMetadata new_emeta = new EMetadata();
                                    new_emeta.lastRenewal       = emeta.lastRenewal;
                                    new_emeta.generatedBefore   = emeta.generatedBefore;
                                    new_emeta.complexity        = emeta.complexity;
                                    new_emeta.account           = emeta.account;
                                    new_emeta.host              = emeta.host;
                                    new_emeta.passwordLength    = emeta.passwordLength;
                                    new_emeta.optLowercase      = emeta.optLowercase;
                                    new_emeta.optNumbers        = emeta.optNumbers;
                                    new_emeta.optSymbols        = emeta.optSymbols;
                                    new_emeta.optUppercase      = emeta.optUppercase;
                                    new_emeta.metadataGroupId   = new_emg.id;
                                    new_emeta.userId            = session.user_id;

                                    em.getTransaction().begin();
                                    em.persist(new_emeta);
                                    em.getTransaction().commit();

                                    response.addAffectedRecordsItem(String.valueOf(new_emeta.id));

                                    metadata_count++;
                                }

                            }

                            group_count++;

                            Info.get_instance().print("Imported %d of %d groups with total of %d metadata.",
                                                    group_count,import_data.groups.length, metadata_count);



                        }



                    } catch (Exception e) {
                        Warning.get_instance().print(e);
                        response.setError(e.getMessage());
                    }

                    em.close();
                    return Response.ok().entity(response).build();
                });
    }
}
