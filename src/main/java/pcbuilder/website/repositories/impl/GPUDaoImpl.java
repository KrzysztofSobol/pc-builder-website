package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.repositories.GPUDao;

@Repository
public class GPUDaoImpl extends GenericDaoImpl<GPU, Long> implements GPUDao {
}
