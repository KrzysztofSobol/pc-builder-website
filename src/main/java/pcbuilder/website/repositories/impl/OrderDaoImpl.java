package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.repositories.OrderDao;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order, Long> implements OrderDao {
}
