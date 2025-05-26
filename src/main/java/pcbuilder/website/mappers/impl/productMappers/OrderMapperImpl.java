package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.orders.OrderDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;
import pcbuilder.website.models.entities.Recipient;
import pcbuilder.website.models.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements Mapper<Order, OrderDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    public OrderMapperImpl() {
        modelMapper.createTypeMap(Order.class, OrderDto.class)
                .addMapping(src -> src.getUser().getUserID(), OrderDto::setUserID);
    }

    @Override
    public OrderDto mapTo(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Order mapFrom(OrderDto dto) {
        Order order = new Order();
        User user = new User();
        user.setUserID(dto.getUserID());
        order.setUser(user);

        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setShipmentMethod(dto.getShipmentMethod());
        order.setOrderDate(LocalDateTime.now());

        if (dto.getRecipient() != null) {
            Recipient recipient = modelMapper.map(dto.getRecipient(), Recipient.class);
            order.setRecipient(recipient);
        }

        if (dto.getProducts() != null) {
            List<OrderItem> items = dto.getProducts().stream()
                    .map(itemDto -> {
                        var orderItem = new pcbuilder.website.models.entities.OrderItem();
                        var product = new pcbuilder.website.models.entities.Product();
                        product.setProductID(itemDto.getProductID());
                        orderItem.setProduct(product);
                        orderItem.setQuantity(itemDto.getQuantity());
                        orderItem.setOrder(order);
                        return orderItem;
                    })
                    .collect(Collectors.toList());
            order.setOrderItems(items);
        }

        return order;
    }
}
