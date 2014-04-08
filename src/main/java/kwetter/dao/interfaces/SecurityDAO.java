package kwetter.dao.interfaces;

import kwetter.domain.Role;
import kwetter.domain.User;

/**
 * Created by geh on 1-4-14.
 */
public interface SecurityDAO
{
    void addRole(Role role);

    void addUserRole(User user, Role role);

    Role getRole(String name);
}
