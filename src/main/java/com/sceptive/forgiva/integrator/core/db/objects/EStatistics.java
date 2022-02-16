package com.sceptive.forgiva.integrator.core.db.objects;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fg_statistics",
       indexes =  {
               @Index(name = "md_at_index", columnList = "action_time"),
               @Index(name = "md_at_key_index", columnList = "key,action_time"),
               @Index(name = "md_key_index", columnList = "key")
       }
)
public class EStatistics {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public long id;

    @Column(name = "action_time")
    public LocalDateTime action_time;

    @Column(name = "key")
    public String key;

    @Column(name = "value")
    public double value;

}
