package guru.sfg.beer.inventory.listener;

import guru.springframework.events.NewInventoryEvent;

public interface BeerInventoryEventListener {

    void listen(NewInventoryEvent event);

}
