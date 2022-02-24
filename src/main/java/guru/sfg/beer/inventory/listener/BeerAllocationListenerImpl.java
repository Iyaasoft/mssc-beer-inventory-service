package guru.sfg.beer.inventory.listener;

import guru.sfg.beer.inventory.config.JmsConfig;
import guru.sfg.beer.inventory.service.AllocationService;
import guru.springframework.services.messages.AllocateOrderResult;
import guru.springframework.statemachine.action.event.AllocateOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerAllocationListenerImpl implements BeerAllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @Override
    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER)
    public void listen(@Payload AllocateOrderEvent allocateOrderEvent) {
        boolean allocated = false;
        boolean allocationError = false;
        try {
            allocated = allocationService.allocateOrder(allocateOrderEvent.getBeerOrderDto());
        } catch (Exception ex) {
            log.debug("Something went wron when allocating inventory to order : "
                    + allocateOrderEvent.getBeerOrderDto().getId());
            allocationError = true;
        }
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT, AllocateOrderResult.builder()
                .beerOrder(allocateOrderEvent.getBeerOrderDto())
                .allocated(allocated)
                .allocationError(allocationError)
                .build());
    }
}
