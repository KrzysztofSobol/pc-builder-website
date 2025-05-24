package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.orders.OrderItemResponseDto;
import pcbuilder.website.models.dto.orders.OrderResponseDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;

import java.util.stream.Collectors;

@Component("orderResponseMapper")
public class OrderResponseMapperImpl implements Mapper<Order, OrderResponseDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderResponseMapperImpl() {
        modelMapper.createTypeMap(Order.class, OrderResponseDto.class)
                .addMapping(src -> src.getUser().getUserID(), OrderResponseDto::setUserID)
                .addMapping(src -> src.getUser().getUsername(), OrderResponseDto::setUsername)
                .setPostConverter(ctx -> {
                    Order src = ctx.getSource();
                    OrderResponseDto dst = ctx.getDestination();

                    var items = src.getOrderItems().stream()
                            .map(this::convertToItemResponseDto)
                            .collect(Collectors.toList());
                    dst.setItems(items);

                    double total = items.stream()
                            .mapToDouble(OrderItemResponseDto::getSubtotal)
                            .sum();
                    dst.setTotalPrice(total);

                    return dst;
                });
    }

    @Override
    public OrderResponseDto mapTo(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public Order mapFrom(OrderResponseDto dto) {
        return modelMapper.map(dto, Order.class);
    }

    private OrderItemResponseDto convertToItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductID(item.getProduct().getProductID());
        dto.setProductName(item.getProduct().getName());
        dto.setImageUrl(item.getProduct().getImageUrl());
        dto.setUnitPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        double sub = Math.round(item.getProduct().getPrice() * item.getQuantity() * 100.0) / 100.0;
        dto.setSubtotal(sub);
        return dto;
    }
}
