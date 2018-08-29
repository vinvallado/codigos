package br.mil.fab.prefeitura.api.schedules;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
public class ParcelaPrefeituraSchedule {
	    @Autowired
	    private ThreadPoolTaskScheduler taskScheduler;

	    @Autowired
	    private PeriodicTrigger periodicTrigger;

	    @PostConstruct
	    public void init() {
	            taskScheduler.schedule(new RunnableTask(), periodicTrigger);
	    }

	    class RunnableTask implements Runnable {

	        @Override
	        public void run() {
	        	Calendar cal = Calendar.getInstance();
	            cal.setTime(new Date());
	            if(cal.get(Calendar.DAY_OF_MONTH) == 20) {
	        	   System.out.println("é hoje");
	           }else {
	        	   System.out.println("não é hoje");
	           }
	        }
	    }
	}


