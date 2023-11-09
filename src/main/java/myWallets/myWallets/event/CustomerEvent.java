package myWallets.myWallets.event;

import myWallets.myWallets.entity.Customer;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class CustomerEvent extends ApplicationEvent {

    private Customer customer;


    public CustomerEvent(Object source) {
        super(source);
    }

    public CustomerEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
