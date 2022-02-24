package guru.sfg.beer.inventory.service;

import guru.springframework.web.model.BeerOrderDto;

public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto beerOrderDto);

   // void deallocateOrder(BeerOrderDto beerOrderDto);
}