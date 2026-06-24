package finki.emt.lab_emt.events.HostEvents;

import finki.emt.lab_emt.model.domain.Host;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HostCreatedEvent extends ApplicationEvent {

    private LocalDateTime time;
    public HostCreatedEvent(Host host) {
        super(host);
        this.time = LocalDateTime.now();
    }

    public HostCreatedEvent(Host host, LocalDateTime time) {
        super(host);
        this.time = time;
    }
}
