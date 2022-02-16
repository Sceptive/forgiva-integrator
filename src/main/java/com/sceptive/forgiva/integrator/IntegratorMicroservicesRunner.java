package com.sceptive.forgiva.integrator;


import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.logging.Info;
import org.wso2.carbon.config.ConfigProviderFactory;
import org.wso2.msf4j.MicroservicesRunner;
import org.wso2.msf4j.internal.DataHolder;

import org.wso2.transport.http.netty.contract.config.TransportsConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.util.Iterator;

public class IntegratorMicroservicesRunner  {

    private static MicroservicesRunner runner = null;

    public static MicroservicesRunner get_runner() {

        if (runner == null) {
            String configuration_yaml =
                    String.format(
                            "  transportProperties:                             \n" +
                                    "   -                                               \n" +
                                    "    name: \"latency.metrics.enabled\"              \n" +
                                    "    value: true                                    \n" +
                                    "   -                                               \n" +
                                    "    name: \"server.bootstrap.boss.group.size\"     \n" +
                                    "    value: %d                                      \n" +
                                    "   -                                               \n" +
                                    "    name: \"server.bootstrap.worker.group.size\"   \n" +
                                    "    value: %d                                      \n" +
                                    "  listenerConfigurations:                          \n" +
                                    "  -                                                \n" +
                                    "    id: \"jaxrs-http\"                             \n" +
                                    "    host: \"%s\"                                   \n" +
                                    "    port: %d                                       \n" +
                                    "    scheme: https                                  \n" +
                                    "    serverHeader: forgiva-integrator-http-transport\n" +
                                    "    sslConfig:                                     \n" +
                                    "      keyStore: \"%s\"                             \n" +
                                    "      keyStorePass: %s                             \n" +
                                    "      sslProtocol: TLS                             \n" +
                                    "      certPass: %s                                 \n" +
                                    "  senderConfigurations:                            \n" +
                                    "   -                                               \n" +
                                    "    id: \"netty-gw\"                               \n",
                            Runtime.getRuntime()
                                    .availableProcessors(),
                            Runtime.getRuntime().availableProcessors() * 2,
                            Configuration.runtime.https_host,
                            Configuration.runtime.https_port.intValue(),
                            Configuration.runtime.https_ssl_keystore_file,
                            Configuration.runtime.https_ssl_keystore_pass,
                            Configuration.runtime.https_ssl_cert_pass
                    );

            // Info.get_instance().print("%s",configuration_yaml);

            TransportsConfiguration transportsConfiguration;
            Yaml yaml = new Yaml();
            yaml.setBeanAccess(BeanAccess.FIELD);
            transportsConfiguration = yaml.loadAs(configuration_yaml, TransportsConfiguration.class);
            runner = new MicroservicesRunner(transportsConfiguration);
        }

        return runner;

    }
}
