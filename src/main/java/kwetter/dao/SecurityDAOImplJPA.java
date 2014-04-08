package kwetter.dao;

import kwetter.dao.interfaces.SecurityDAO;
import kwetter.domain.Role;
import kwetter.domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by geh on 1-4-14.
 */
@Stateless
public class SecurityDAOImplJPA implements SecurityDAO
{
    @PersistenceContext(unitName = "kwetterdb")
    private EntityManager em;

    @Override
    public void addRole(Role role)
    {
        em.persist(role);
        em.getEntityManagerFactory().getCache().evictAll();
    }

    @Override
    public void addUserRole(User user, Role role)
    {
        if(!user.getRoles().contains(role) && !role.getUsers().contains(user))
        {
            user.addRole(role);
            role.addUser(user);
            em.merge(user);
            em.merge(role);
            em.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public Role getRole(String name)
    {
        return em.find(Role.class, name);
    }
}
