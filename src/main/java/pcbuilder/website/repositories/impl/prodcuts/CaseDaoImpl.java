package pcbuilder.website.repositories.impl.prodcuts;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;
import pcbuilder.website.repositories.impl.GenericDaoImpl;

@Repository
public class CaseDaoImpl extends GenericDaoImpl<Case, Long> implements CaseDao {
}
