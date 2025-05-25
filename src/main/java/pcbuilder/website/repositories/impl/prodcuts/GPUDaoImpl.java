package pcbuilder.website.repositories.impl.prodcuts;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.GPU;
import pcbuilder.website.repositories.GPUDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

@Repository
public class GPUDaoImpl extends GenericDaoImpl<GPU, Long> implements GPUDao {
}
