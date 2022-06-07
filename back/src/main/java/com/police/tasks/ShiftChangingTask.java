package com.police.tasks;

import com.police.services.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShiftChangingTask {

    private OfficerService officerService;

    @Autowired
    public ShiftChangingTask(OfficerService os){
        this.officerService = os;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void shiftSwapDay(){
        officerService.prepareDayShift();
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void shiftSwapNight(){
        officerService.prepareNightShift();
    }

}
