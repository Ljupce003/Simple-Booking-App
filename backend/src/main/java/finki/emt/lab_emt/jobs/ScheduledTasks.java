package finki.emt.lab_emt.jobs;

import finki.emt.lab_emt.service.domain.SmestuvanjeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final SmestuvanjeService smestuvanjeService;

    public ScheduledTasks(SmestuvanjeService smestuvanjeService) {
        this.smestuvanjeService = smestuvanjeService;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void updateMaterializedView(){
        this.smestuvanjeService.updateView();
    }
}
