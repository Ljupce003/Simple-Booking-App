package finki.emt.lab_emt.events.HostEvents;

import finki.emt.lab_emt.model.domain.Host;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;


@Getter
public class HostUpdatedEvent extends ApplicationEvent {

    private LocalDateTime time;
    public HostUpdatedEvent(Host host) {
        super(host);
        this.time = LocalDateTime.now();
    }

    public HostUpdatedEvent(Host host, LocalDateTime time) {
        super(host);
        this.time = time;
    }

}
