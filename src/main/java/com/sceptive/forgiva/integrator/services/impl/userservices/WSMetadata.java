package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadata;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

public class WSMetadata {
    /**
 * Adds new metadata to user metadata table
 *
 * @param _postUserMetadataAddRequest
 * @return
 */
public static Response add_metadata(PostUserMetadataAddRequest _postUserMetadataAddRequest) {

    return SecurityManager
            .secure_invoke(_postUserMetadataAddRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {

                    EMetadata metadata = new EMetadata();

                    metadata.userId = session.user_id;

                    if (_postUserMetadataAddRequest.getPasswordLength() == null ||
                        _postUserMetadataAddRequest.getMetadata() == null ||
                        _postUserMetadataAddRequest.getMetadata().getAccount() == null ||
                        _postUserMetadataAddRequest.getMetadata().getHost() == null ||
                        _postUserMetadataAddRequest.getMetadata().getComplexity() == null ||
                        _postUserMetadataAddRequest.getOptLowercase() == null ||
                        _postUserMetadataAddRequest.getOptUppercase() == null ||
                        _postUserMetadataAddRequest.getOptSymbols() == null ||
                        _postUserMetadataAddRequest.getOptNumbers() == null ||
                        // If all password requirements are false then fail
                        (!_postUserMetadataAddRequest.getOptLowercase() &&
                          !_postUserMetadataAddRequest.getOptUppercase() &&
                          !_postUserMetadataAddRequest.getOptSymbols() &&
                          !_postUserMetadataAddRequest.getOptNumbers()
                        ) ||
                        // If complexity is not between 1-3 inclusiv
                        (_postUserMetadataAddRequest.getMetadata().getComplexity() < 1 ||
                         _postUserMetadataAddRequest.getMetadata().getComplexity() > 3
                        ) ||
                        // If both account and host are empty
                        (_postUserMetadataAddRequest.getMetadata().getAccount().trim().isEmpty() &&
                         _postUserMetadataAddRequest.getMetadata().getHost().trim().isEmpty()
                        ) ||
                        // If password length is invalid (No we wont generate single digit password)
                        (_postUserMetadataAddRequest.getPasswordLength() <= 1 ||
                         _postUserMetadataAddRequest.getPasswordLength() > 32)
                    ) {

                        throw new InvalidValueException("Invalid metadata");
                    }



                    metadata.passwordLength  = _postUserMetadataAddRequest.getPasswordLength();
                    metadata.account         =
                            _postUserMetadataAddRequest.getMetadata().getAccount();
                    metadata.host            = _postUserMetadataAddRequest.getMetadata().getHost();
                    metadata.complexity      =
                            _postUserMetadataAddRequest.getMetadata().getComplexity();
                    metadata.metadataGroupId =
                            Long.parseLong(_postUserMetadataAddRequest.getMetadata().getGroupId());
                    metadata.generatedBefore = false;
                    metadata.lastRenewal     = null;
                    metadata.optLowercase    = _postUserMetadataAddRequest.getOptLowercase();
                    metadata.optNumbers      = _postUserMetadataAddRequest.getOptNumbers();
                    metadata.optSymbols      = _postUserMetadataAddRequest.getOptSymbols();
                    metadata.optUppercase    = _postUserMetadataAddRequest.getOptUppercase();

                    EntityManager em = Database.get_instance().getEm();
                    em.getTransaction().begin();
                    em.persist(metadata);
                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(String.valueOf(metadata.id));

                }
                catch (Exception ex) {
                    //  Info.get_instance().print(ex);
                    ret.error("Invalid metadata");
                }

                return Response.ok().entity(ret).build();
            });
}

    /**
 * Returns metadatas in group starts with startIdx, with count within metadata group specified
 * with metadataGroupId
 *
 * @param _request
 * @return
 */
public static Response get_metadata_bygroupId(PostUserMetadataBygroupRequest _request) {

    return SecurityManager.secure_invoke(_request.getHeader(), false, (session) -> {
        PostUserMetadataBygroupResponse response = new PostUserMetadataBygroupResponse();

        try {
            Long group_id  = Long.parseLong(_request.getGroupId());
            int  start_idx = _request.getStartIdx();
            int  count     = _request.getCount();

            if (start_idx < 0 || count < 0) {
                throw new InvalidValueException("Invalid start index or count value");
            }

            EntityManager em = Database.get_instance().getEm();

            // TODO this is lame to count total records everytime. There should be
            //  another way to implement this.
            Long total_count = (Long) em.createQuery(
                    "SELECT COUNT(m) FROM EMetadata m WHERE m.userId = :userId " +
                    " AND  m.metadataGroupId = :metadataGroupId ")
                                        .setParameter("userId", session.user_id)
                                        .setParameter("metadataGroupId", group_id)
                                        .getSingleResult();

            List<EMetadata> res = em.createQuery(
                    "SELECT m FROM EMetadata m WHERE " + " m.userId = :userId AND m" +
                    ".metadataGroupId = :metadataGroupId " + "ORDER BY m.id", EMetadata.class)
                                    .setParameter("userId", session.user_id)
                                    .setParameter("metadataGroupId", group_id)
                                    .setFirstResult(start_idx)
                                    .setMaxResults(count).getResultList();

            for (EMetadata e_metadata : res) {
                response.addMetadatasItem(
                        new Metadata().account(e_metadata.account).host(e_metadata.host)
                                      .complexity(e_metadata.complexity)
                                      .metadataId(String.valueOf(e_metadata.id))
                                      .generatedBefore(e_metadata.generatedBefore)
                                      .groupId(String.valueOf(e_metadata.metadataGroupId))
                                      .lastRenewal(e_metadata.lastRenewal));
            }

            response.setStartIdx(_request.getStartIdx());
            response.setTotalRecords(total_count.intValue());
        }
        catch (Exception e) {
            Warning.get_instance().print(e);
        }

        return Response.ok().entity(response).build();
    });
}

    /**
 * @param _postUserMetadataHostRequest
 * @return
 * @throws NotFoundException
 */
public static Response get_metadata_hosts(PostUserMetadataHostRequest _postUserMetadataHostRequest)
        throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserMetadataHostRequest.getHeader(), false, (session) -> {
                PostUserMetadataHostResponse response = new PostUserMetadataHostResponse();

                try {

                    if (_postUserMetadataHostRequest.getFilter() == null) {
                        throw new InvalidValueException("Filter cannot be null");
                    }
                    EntityManager em = Database.get_instance().getEm();
                    Query query = em.createQuery(
                            "SELECT DISTINCT m.host FROM EMetadata m WHERE m.userId = :userId " +
                            " AND m.host LIKE :host", EMetadata.class)
                                    .setParameter("userId", session.user_id).setParameter("host",
                                                                                          "%" +
                                                                                          _postUserMetadataHostRequest
                                                                                                  .getFilter() +
                                                                                          "%");
                    List<String> res = query.getResultList();

                    for (String h : res) {
                        response.addHostsItem(new PostUserMetadataHostResponseHosts().host(h));
                    }
                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                }

                return Response.ok().entity(response).build();
            });
}

    /**
 * Removes metadata specified with metadataId
 *
 * @param _postUserMetadataRemoveRequest
 * @return
 * @throws NotFoundException
 */
public static Response remove_metadata(PostUserMetadataRemoveRequest _postUserMetadataRemoveRequest)
        throws NotFoundException {
    return SecurityManager
            .secure_invoke(_postUserMetadataRemoveRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {

                    if (_postUserMetadataRemoveRequest.getMetadataId() != null) {

                        long m_id = Long.parseLong(_postUserMetadataRemoveRequest.getMetadataId());

                        EntityManager em = Database.get_instance().getEm();
                        Query query = em.createQuery(
                                "SELECT m FROM EMetadata m WHERE m.id = :mId " +
                                " AND m.userId = :userId", EMetadata.class)
                                        .setParameter("mId", m_id)
                                        .setParameter("userId", session.user_id);
                        List res = query.getResultList();

                        if (res.size() == 1) {
                            em.getTransaction().begin();
                            em.remove(res.get(0));
                            em.getTransaction().commit();
                            em.close();
                            ret.addAffectedRecordsItem(
                                    _postUserMetadataRemoveRequest.getMetadataId());
                        } else {
                            ret.error("Metadata does not exists.");
                        }
                    }

                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                    ret.info("Invalid data");
                }

                return Response.ok().entity(ret).build();
            });
}

    /**
 * Returns list of metadata filtered by likeliness of criteria within host and account fields
 *
 * @param _postUserMetadataSearchRequest
 * @return
 * @throws NotFoundException
 */
public static Response search_metadata(PostUserMetadataSearchRequest _postUserMetadataSearchRequest)
        throws NotFoundException {

    return SecurityManager
            .secure_invoke(_postUserMetadataSearchRequest.getHeader(), false, (session) -> {
                PostUserMetadataSearchResponse response = new PostUserMetadataSearchResponse();

                try {

                    String criteria = _postUserMetadataSearchRequest.getCriteria();

                    EntityManager em = Database.get_instance().getEm();
                    Query query = em.createQuery(
                            "SELECT m FROM EMetadata m WHERE m.userId = :userId " +
                            " AND (m.host LIKE :criteria OR m.account LIKE :criteria)",
                            EMetadata.class).setParameter("userId", session.user_id)
                                    .setParameter("criteria", "%" + criteria + "%");
                    List<EMetadata> res = query.getResultList();

                    for (EMetadata m : res) {
                        response.addMetadataItem(new Metadata().metadataId(String.valueOf(m.id))
                                                               .groupId(String.valueOf(
                                                                       m.metadataGroupId))
                                                               .host(m.host).account(m.account)
                                                               .lastRenewal(m.lastRenewal)
                                                               .complexity(m.complexity)
                                                               .generatedBefore(m.generatedBefore));
                    }
                }
                catch (Exception e) {
                    Info.get_instance().print(e);
                }

                return Response.ok().entity(response).build();
            });
}
}
