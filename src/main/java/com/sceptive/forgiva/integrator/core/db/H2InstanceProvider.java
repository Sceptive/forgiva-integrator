package com.sceptive.forgiva.integrator.core.db;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class H2InstanceProvider {

    static HashMap<String, JdbcConnectionPool> index = new HashMap<>(0);

    public static JdbcConnectionPool get(String _tag) {

        if (index.containsKey(_tag)) {
            return index.get(_tag);
        } else {

            String bf_data_path         = Configuration.data_path +
                                          File.separator + _tag;
            File   bf_data_file         = new File(bf_data_path);

            JdbcConnectionPool pool = JdbcConnectionPool.create(
                    "jdbc:h2:"+bf_data_file.getAbsolutePath(), "sa", "sa");

            index.put(_tag, pool);

            return pool;
        }

    }

    public static boolean instance_has_table(JdbcConnectionPool _pool, String _table)  {

        boolean ret = false;

        try (Connection conn = _pool.getConnection();
            ResultSet rs    = conn.getMetaData().getTables(conn.getCatalog(), null, _table, null);) {
            ret = rs.next();
        } catch (SQLException _ex) {
            Warning.get_instance().print(_ex);
        }

        return ret;

    }

}
