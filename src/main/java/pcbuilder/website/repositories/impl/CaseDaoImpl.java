package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.products.Case;
import pcbuilder.website.repositories.CaseDao;

@Repository
public class CaseDaoImpl extends GenericDaoImpl<Case, Long> implements CaseDao {
}
