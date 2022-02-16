package guru.sfg.beer.inventory.service.bootstrap;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 2019-06-07.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerInvntoryBootstrap implements CommandLineRunner {
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("2d2af9b0-3e1a-46e1-8f01-d7293f3e985e");
    public static final UUID BEER_2_UUID = UUID.fromString("857670c4-898b-418a-ac8c-519fb86fd1ad");
    public static final UUID BEER_3_UUID = UUID.fromString("8cbcdaa1-f66a-4dbd-a98f-03b889d4cb4e");

//    Heiniken ALLBEERSFOUND 5456319d-7b6f-41e0-a6e3-2365f3a6d196
//    Guiness ALLBEERSFOUND f404a711-c998-4b85-99b9-f1cdf52cda37
//    Stella Artoi ALLBEERSFOUND 10de133a-0dea-42d7-a1bf-ea4d77bcf8f2

//    public static final UUID BEER_1_UUID = UUID.fromString("5456319d-7b6f-41e0-a6e3-2365f3a6d196");
//    public static final UUID BEER_2_UUID = UUID.fromString("f404a711-c998-4b85-99b9-f1cdf52cda37");
//    public static final UUID BEER_3_UUID = UUID.fromString("10de133a-0dea-42d7-a1bf-ea4d77bcf8f2");

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if(beerInventoryRepository.count() == 0){
            loadInitialInv();
        }
    }

    private void loadInitialInv() {
        beerInventoryRepository.save(BeerInventory
                .builder()
                .beerId(BEER_1_UUID)
                .upc(BEER_1_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.save(BeerInventory
                .builder()
                .beerId(BEER_2_UUID)
                .upc(BEER_2_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.saveAndFlush(BeerInventory
                .builder()
                .beerId(BEER_3_UUID)
                .upc(BEER_3_UPC)
                .quantityOnHand(50)
                .build());

        log.debug("Loaded Inventory. Record count: " + beerInventoryRepository.count());
    }
}
