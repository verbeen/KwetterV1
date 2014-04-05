package kwetter.dao;

import kwetter.dao.interfaces.SecurityDAO;
import kwetter.domain.Role;
import kwetter.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by geh on 1-4-14.
 */
public class SecurityDAOImplJPA implements SecurityDAO
{
    @PersistenceContext(unitName = "kwetterdb")
    private EntityManager em;

    @Override
    public void addRole(Role role)
    {
        em.persist(role);
    }

    @Override
    public void addUserRole(User user, Role role)
    {
        user.addRole(role);
        role.addUser(user);
        em.merge(user);
        em.merge(role);
    }
}
