package com.sceptive.forgiva.integrator.core;

import com.sceptive.forgiva.integrator.core.db.objects.EStatistics;
import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Debug;
import com.sceptive.forgiva.integrator.logging.Warning;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSFileStore;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatCollector implements Runnable{


    // Collect data every minute
    private final      long                     check_rate = 60;

    private final      ScheduledExecutorService scheduler  =
            Executors.newScheduledThreadPool(1);
    private final      String                   KEY_CPU_USAGE_PERC = "CPU_USAGE_PERC";

    private static StatCollector            instance;

    /*
        Returns instance if not initialized statically
     */
    public static StatCollector get_instance() {
        if (instance == null) {
            try {
                instance = new StatCollector();
            } catch (NotInitializedException ex) {
                return null;
            }
        }

        return instance;
    }

    protected StatCollector() throws NotInitializedException {
        if (!initialize()) {
            throw new NotInitializedException();
        }
    }

    private boolean initialize() {



        scheduler.scheduleAtFixedRate(StatCollector.this,
                                      check_rate, check_rate, TimeUnit.SECONDS);

        return true;
    }


    public double get_current_cpu_usage_percentage() {
        SystemInfo     si        = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();

        long[] prevTicks = processor.getSystemCpuLoadTicks();

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException _e) {
            Warning.get_instance().print(_e);
        }

        return (processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100);

    }

    @Override
    public void run() {

        try {
            Debug.get_instance()
                 .print("Running statistics collector");
            double cpu_usage = get_current_cpu_usage_percentage();
            EntityManager em = Database.get_instance()
                                       .getEm();
            EStatistics estat = new EStatistics();
            estat.action_time = LocalDateTime.now();
            estat.key         = KEY_CPU_USAGE_PERC;
            estat.value       = cpu_usage;
            em.getTransaction()
              .begin();
            em.persist(estat);
            em.getTransaction()
              .commit();
            em.close();
        }catch (Exception _ex)
        {
            Warning.get_instance().print(_ex);
        }

    }
}
