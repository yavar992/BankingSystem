package myWallets.myWallets.event;

import myWallets.myWallets.entity.ATM;
import myWallets.myWallets.entity.CustomerAccountDetails;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ATMEvent extends ApplicationEvent {
    private ATM atm;


    public ATMEvent(Object source) {
        super(source);
    }

    public ATMEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
