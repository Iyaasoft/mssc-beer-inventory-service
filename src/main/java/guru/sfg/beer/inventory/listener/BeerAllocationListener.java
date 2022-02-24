package guru.sfg.beer.inventory.listener;

import guru.springframework.statemachine.action.event.AllocateOrderEvent;

public interface BeerAllocationListener {
    void listen(AllocateOrderEvent allocateOrderEvent);
}
