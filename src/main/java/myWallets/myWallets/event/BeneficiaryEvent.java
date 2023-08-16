package myWallets.myWallets.event;

import myWallets.myWallets.entity.Beneficiary;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class BeneficiaryEvent extends ApplicationEvent {

    private Beneficiary beneficiary;

    public BeneficiaryEvent(Object source) {
        super(source);
    }

    public BeneficiaryEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
