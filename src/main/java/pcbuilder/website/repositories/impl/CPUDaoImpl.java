package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.CPU;
import pcbuilder.website.repositories.CPUDao;

@Repository
public class CPUDaoImpl extends GenericDaoImpl<CPU, Long> implements CPUDao {
}
