package com.sceptive.forgiva.integrator.services.impl.userservices;

import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.db.objects.EMetadataGroup;
import com.sceptive.forgiva.integrator.core.db.objects.ESession;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.logging.Warning;
import com.sceptive.forgiva.integrator.services.SecurityManager;
import com.sceptive.forgiva.integrator.services.gen.api.NotFoundException;
import com.sceptive.forgiva.integrator.services.gen.model.*;
import com.sceptive.forgiva.integrator.services.impl.CommonOperations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

public class WSMetadataGroup {
    /**
 * Adds metadata group defined in request to the database
 *
 * @param _postUserMetadataGroupAddRequest
 * @return
 */
public static Response add_metadatagroup(
        PostUserMetadataGroupAddRequest _postUserMetadataGroupAddRequest) {

    return SecurityManager
            .secure_invoke(_postUserMetadataGroupAddRequest.getHeader(), false, (session) -> {
                OperationResult ret = new OperationResult();

                try {
                    EMetadataGroup metadataGroup = new EMetadataGroup();
                    metadataGroup.userId = session.user_id;
                    if (_postUserMetadataGroupAddRequest.getGroup() == null ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName() == null ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName().isEmpty() ||
                        _postUserMetadataGroupAddRequest.getGroup().getGroupName().trim().equalsIgnoreCase("")
                        ) {
                        throw new InvalidValueException("Empty group");
                    }
                    metadataGroup.groupName        =
                            _postUserMetadataGroupAddRequest.getGroup().getGroupName();
                    metadataGroup.groupDescription =
                            _postUserMetadataGroupAddRequest.getGroup().getGroupDescription();
                    if (_postUserMetadataGroupAddRequest.getGroup().getParentGroupId() != null) {
                        metadataGroup.parentGroupId = Long.valueOf(
                                _postUserMetadataGroupAddRequest.getGroup().getParentGroupId());
                    }

                    EntityManager em = Database.get_instance().getEm();
                    em.getTransaction().begin();
                    em.persist(metadataGroup);
                    em.getTransaction().commit();
                    em.close();
                    ret.addAffectedRecordsItem(String.valueOf(metadataGroup.id));


                }
                catch (Exception ex) {
                   // Info.get_instance().print(ex);
                    ret.error("Invalid data");
                }

                return Response.ok().entity(ret).build();
            });

    // return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
}

    private static int remove_metadatagroup_internal(Long _metadataGroupId, ESession _session) {

        int ret = 0;

        EntityManager em = Database.get_instance().getEm();
        Query sub_groups_query = em.createQuery("SELECT m FROM EMetadataGroup m WHERE " +
                                                " m.parentGroupId = :mgId AND m.userId = :userId",
                                                EMetadataGroup.class)
                                   .setParameter("mgId", _metadataGroupId)
                                   .setParameter("userId", _session.user_id);
        List<EMetadataGroup> sub_groups = sub_groups_query.getResultList();

        for (EMetadataGroup sub_group : sub_groups) {
            ret += remove_metadatagroup_internal(sub_group.id, _session);
        }

        Query query = em.createQuery(
                "DELETE FROM EMetadataGroup m WHERE m.id = :mId " + " AND m.userId = :userId")
                        .setParameter("mId", _metadataGroupId).setParameter("userId", _session.user_id);
        em.getTransaction().begin();
        int rowCount = query.executeUpdate();
        em.getTransaction().commit();
        // If any metadatagroup is deleted then remove the metadatas related to it
        if (rowCount > 0) {

            query = em.createQuery("DELETE FROM EMetadata m WHERE m.metadataGroupId = :mgId " +
                                   " AND m.userId = :userId").setParameter("mgId", _metadataGroupId)
                      .setParameter("userId", _session.user_id);
            em.getTransaction().begin();
            rowCount = query.executeUpdate();
            em.getTransaction().commit();

            ret += rowCount;
            //            info_resp = String.format("%d metadata removed",rowCount);

        }
        em.close();
        return ret;
    }

    /**
     * @param _postUserMetadataGroupRemoveRequest
     * @return
     * @throws NotFoundException
     */
    public static Response remove_metadatagroup(
            PostUserMetadataGroupRemoveRequest _postUserMetadataGroupRemoveRequest)
            throws NotFoundException {

        return SecurityManager
                .secure_invoke(_postUserMetadataGroupRemoveRequest.getHeader(), false, (session) -> {
                    String info_resp  = new String();
                    String error_resp = null;

                    if (_postUserMetadataGroupRemoveRequest.getMetadataGroupId() != null) {

                        try {

                            Long metadataGroupId = Long.parseLong(
                                    _postUserMetadataGroupRemoveRequest.getMetadataGroupId());

                            int count = remove_metadatagroup_internal(metadataGroupId, session);
                            info_resp = String.format("%d metadata removed.", count);

                            // In case user removes all metadata groups then we should create default
                            // one
                            CommonOperations.create_default_metadatagroup_for_user(session.user_id);

                        }
                        catch (Exception e) {

                            error_resp = "Invalid data";
                            Warning.get_instance().print(e);

                            // TODO There should be incident log integration
                        }
                    }

                    return Response.ok().entity(new OperationResult().error(error_resp).info(info_resp))
                                   .build();
                });
    }

    /**
     * @param _postUserMetadataGroupsRequest
     * @return
     * @throws NotFoundException
     */
    public static Response get_metadata_groups(
            PostUserMetadataGroupsRequest _postUserMetadataGroupsRequest) throws NotFoundException {
        return SecurityManager
                .secure_invoke(_postUserMetadataGroupsRequest.getHeader(), false, (session) -> {
                    PostUserMetadataGroupsResponse response = new PostUserMetadataGroupsResponse();

                    try {
                        EntityManager em = Database.get_instance().getEm();
                        Query query = em.createQuery(
                                "SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.parentGroupId = 0",
                                EMetadataGroup.class).setParameter("userId", session.user_id);
                        List<EMetadataGroup> res = query.getResultList();

                        for (EMetadataGroup m : res) {

                            response.addGroupsItem(new MetadataGroup().groupId(String.valueOf(m.id))
                                                                      .groupDescription(
                                                                              m.groupDescription)
                                                                      .groupName(m.groupName)
                                                                      .parentGroupId(String.valueOf(
                                                                              m.parentGroupId)));
                            Query _subGroupsQuery =
                                    em.createQuery("SELECT m FROM EMetadataGroup m WHERE m.userId = :userId AND m.parentGroupId = :pId",
                                                   EMetadataGroup.class)
                                      .setParameter("userId", session.user_id)
                                      .setParameter("pId",m.id);
                            List<EMetadataGroup> res_sg = _subGroupsQuery.getResultList();
                            for (EMetadataGroup m_sub : res_sg) {
                                response.addGroupsItem(new MetadataGroup().groupId(String.valueOf(m_sub.id))
                                                                          .groupDescription(
                                                                                  m_sub.groupDescription)
                                                                          .groupName(m_sub.groupName)
                                                                          .parentGroupId(String.valueOf(
                                                                                  m_sub.parentGroupId)));
                            }

                        }
                    }
                    catch (Exception e) {
                        Info.get_instance().print(e);
                    }

                    return Response.ok().entity(response).build();
                });
    }
}
