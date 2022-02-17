package guru.sfg.beer.inventory.service.listener;

import guru.springframework.events.NewInventoryEvent;

public interface BeerInventoryEventListener {

    void listen(NewInventoryEvent event);

}
