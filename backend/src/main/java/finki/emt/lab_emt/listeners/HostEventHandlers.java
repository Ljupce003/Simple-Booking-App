package finki.emt.lab_emt.listeners;

import finki.emt.lab_emt.events.HostEvents.HostCreatedEvent;
import finki.emt.lab_emt.events.HostEvents.HostDeletedEvent;
import finki.emt.lab_emt.events.HostEvents.HostUpdatedEvent;
import finki.emt.lab_emt.service.domain.HostService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HostEventHandlers {

    private final HostService hostService;

    public HostEventHandlers(HostService hostService) {
        this.hostService = hostService;
    }

    @EventListener
    public void onHostCreated(HostCreatedEvent hostCreatedEvent){
        this.hostService.updateMaterializedView();
    }

    @EventListener
    public void onHostUpdated(HostUpdatedEvent hostUpdatedEvent){
        this.hostService.updateMaterializedView();
    }

    @EventListener
    public void onHostDeleted(HostDeletedEvent hostDeletedEvent){
        this.hostService.updateMaterializedView();
    }
}
