package guru.sfg.beer.inventory.listener;

import guru.sfg.beer.inventory.config.JmsConfig;
import guru.sfg.beer.inventory.domain.BeerInventory;
import guru.sfg.beer.inventory.repositories.BeerInventoryRepository;
import guru.springframework.events.NewInventoryEvent;
import guru.springframework.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerInventoryEventListenerImpl implements BeerInventoryEventListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_Q)
    public void listen(NewInventoryEvent event) {

        BeerDto dto = event.getBeerDto();
        log.debug("inventory request received beer id : "+dto.getId());

        List<BeerInventory> inventory = beerInventoryRepository.findAllByBeerId(dto.getId());

        if (inventory.isEmpty()) {
            beerInventoryRepository.save(
                    populateInventoryItemFromBeerDto(inventory,dto));
        }
        log.debug("\ninventory updated for beer id : "+dto.getId() +" quntity to add "+ dto.getQuantityOnHand());

    }

    private BeerInventory populateInventoryItemFromBeerDto(List<BeerInventory> inventory, BeerDto dto) {

        if (!inventory.isEmpty()) {
            inventory.get(0).setQuantityOnHand(dto.getQuantityOnHand());
            inventory.get(0).setUpc(dto.getUpc());
            return inventory.get(0);
        }

        return BeerInventory.builder()
                .beerId(dto.getId())
                .quantityOnHand(dto.getQuantityOnHand())
                .upc(dto.getUpc())
                .build();
        }
}
