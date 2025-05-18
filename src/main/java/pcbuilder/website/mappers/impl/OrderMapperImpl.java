package pcbuilder.website.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.orders.OrderItemResponseDto;
import pcbuilder.website.models.dto.orders.OrderResponseDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;

import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements Mapper<Order, OrderResponseDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderMapperImpl() {
        // custom ModelMapper config
        modelMapper.createTypeMap(Order.class, OrderResponseDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getUser().getUserID(), OrderResponseDto::setUserID);
                    mapper.map(src -> src.getUser().getUsername(), OrderResponseDto::setUsername);
                })
                .setPostConverter(context -> {
                    Order source = context.getSource();
                    OrderResponseDto destination = context.getDestination();

                    destination.setItems(source.getOrderItems().stream()
                            .map(this::convertToItemResponseDto)
                            .collect(Collectors.toList()));

                    // Calculate total price
                    double total = destination.getItems().stream()
                            .mapToDouble(OrderItemResponseDto::getSubtotal)
                            .sum();
                    destination.setTotalPrice(total);

                    return destination;
                });
    }

    private OrderItemResponseDto convertToItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductID(item.getProduct().getProductID());
        dto.setProductName(item.getProduct().getName());
        dto.setImageUrl(item.getProduct().getImageUrl());
        dto.setUnitPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());

        double subtotal = Math.round(item.getProduct().getPrice() * item.getQuantity() * 100.0) / 100.0;
        dto.setSubtotal(subtotal);

        return dto;
    }

    @Override
    public OrderResponseDto mapTo(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public Order mapFrom(OrderResponseDto orderResponseDto) {
        return modelMapper.map(orderResponseDto, Order.class);
    }
}
