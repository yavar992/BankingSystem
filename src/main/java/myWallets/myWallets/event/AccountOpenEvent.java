package myWallets.myWallets.event;

import myWallets.myWallets.entity.CustomerAccountDetails;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class AccountOpenEvent extends ApplicationEvent {

    CustomerAccountDetails customerAccountDetails;

    public AccountOpenEvent(Object source) {
        super(source);
    }

    public AccountOpenEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
