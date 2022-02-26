package com.sceptive.forgiva.integrator.core.bootstrap;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.core.Constants;
import com.sceptive.forgiva.integrator.core.Database;
import com.sceptive.forgiva.integrator.core.crypto.Common;
import com.sceptive.forgiva.integrator.core.crypto.Crypto;
import com.sceptive.forgiva.integrator.core.crypto.FHashGenerator;
import com.sceptive.forgiva.integrator.core.crypto.parameters.Parameters;
import com.sceptive.forgiva.integrator.core.db.objects.EUser;
import com.sceptive.forgiva.integrator.core.db.objects.EUserGroup;
import com.sceptive.forgiva.integrator.exceptions.InvalidValueException;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.impl.CommonOperations;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class BSFinal extends IBootStrap {


    /**
     * If necessary initializes template data for easily login and launch
     */
    private void initialize_template_data()
            throws  InvalidValueException,
                    NotInitializedException,
                    FileNotFoundException {

        // If there is no user group. Create one.
        EntityManager em = Database.get_instance().getEm();

        Long get_usergroup_counts =
                (Long) em.createQuery("SELECT COUNT(u) FROM EUserGroup u").getSingleResult();

        if (get_usergroup_counts == 0) {
            EUserGroup default_user_group = new EUserGroup();
            default_user_group.userGroupName      = Constants.DEFAULT_USERGROUP_NAME;
            default_user_group.groupDescription   = Constants.DEFAULT_USERGROUP_DESCRIPTION;
            default_user_group.id                 = 0;

            em.getTransaction().begin();
            em.persist(default_user_group);
            em.getTransaction().commit();

            EUser default_user = new EUser();
            default_user.externalUser = false;
            default_user.groupId      = default_user_group.id;
            default_user.username     = "forgiva_"+ Common.random_letters(4);
            default_user.fullname     = "Forgiva Enterprise Default User";

            String gen_password = Common.generate_password(16);



            String e_password =
                    Common.encodeHex(Crypto.digest_or_kdf(Configuration.runtime.security_default_hashing,
                            gen_password.getBytes(),
                            Common.decodeHex(Configuration.runtime.security_default_hashing_salt),
                            null));

            //   String gen_password_hash = FHashGenerator.generate(gen_password);
            default_user.password =
                    Common.encodeHex(FHashGenerator.hash_for_model(Configuration.runtime.security_pw_hashing_model,
                            Common.decodeHex(e_password),
                            Common.decodeHex(Configuration.runtime.security_pw_hashing_salt)));

            em.getTransaction().begin();
            em.persist(default_user);
            em.getTransaction().commit();

            CommonOperations.create_default_metadatagroup_for_user(default_user.id);


            new PrintStream(new FileOutputStream(Configuration.upwd_path))
                .printf("%s : %s",default_user.username, gen_password);


            Info.get_instance().print("Created default account and save credentials at %s . " +
                            "Please change after login. ",
                    Configuration.upwd_path
            );



            em.close();
        }
    }


    private void check_default_administrator_account()
            throws  InvalidValueException,
                    NotInitializedException,
                    FileNotFoundException {

        if (Configuration.runtime.administrator_password
                .equalsIgnoreCase("d2d80fd2c2de02e888123521533b904845a087944f79fc697aeb8828163114229a1" +
                                               "e78db5e73eee7996bfb68fa81b98920386cfcc30315c8db4a92dbf3282ae8")
        ) {

            String gen_password = Common.generate_password(16);

            String new_hash     = FHashGenerator.generate(gen_password);

            Configuration.runtime.administrator_password = new_hash;
            Configuration.runtime.administrator_username = "admin_"+Common.random_letters(4);


            new PrintStream(new FileOutputStream(Configuration.apwd_path))
                    .printf("%s : %s",Configuration.runtime.administrator_username, gen_password);


            Info.get_instance().print("It is detected that default password is set. Admin access " +
                    "credentials are changed and saved to  %s  . "
                    , Configuration.apwd_path
            );

        }
    }

    @Override
    public boolean bootstrap(Parameters _launch_options) {

        try {

            initialize_template_data();

            check_default_administrator_account();

            Info.get_instance().print("Final checks are done.");

            return true;

        } catch (InvalidValueException | NotInitializedException  | FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
