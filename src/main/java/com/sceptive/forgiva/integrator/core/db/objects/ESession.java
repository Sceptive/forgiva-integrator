package com.sceptive.forgiva.integrator.core.db.objects;

import com.sceptive.forgiva.integrator.core.crypto.Asymmetric;
import com.sceptive.forgiva.integrator.core.crypto.AsymmetricKeyPair;
import com.sceptive.forgiva.integrator.core.db.SessionIdSequence;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fg_session",
        indexes =  {
                @Index(name = "se_user_id_index", columnList = "user_id")
        })
public class ESession {


    private static SessionIdSequence sequence;

    public static ESession new_instance() {
        if (sequence == null) {
            sequence = new SessionIdSequence();
        }

        ESession ret                 = new ESession();
        ret.id                      = sequence.generate();
        ret.init_ts                 = LocalDateTime.now();
        ret.administrator_session   = false;
        ret.twofa_expected          = false;

        AsymmetricKeyPair ackp =  Asymmetric.generate_asymmetric_key_pairs();

        ret.public_key      = Asymmetric.get_public_key_as_hex(ackp);
        ret.private_key     = Asymmetric.get_private_key_as_hex(ackp);

        return ret;
    }


    @Id
    @Column(name = "id")
    public String id;

    @Column(name = "init_ts")
    public LocalDateTime init_ts;

    @Column(name = "authenticated")
    public Boolean authenticated;

    @Column(name = "auth_ts")
    public LocalDateTime auth_ts;

    @Column(name = "administrator_session")
    public Boolean administrator_session;

    @Column(name = "user_id")
    public long user_id;

    @Column(name = "session_pubk")
    public String public_key;

    @Column(name = "session_privk")
    public String private_key;

    @Column(name = "client_pubk")
    public String client_public_key;

    @Column(name = "twofa_expected")
    public Boolean twofa_expected;


}
