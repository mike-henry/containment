package com.spx.session;

import javax.persistence.FlushModeType;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import com.spx.session.model.User;

@Repository
@EntityManagerConfig(entityManagerResolver = AuthorityEntityScanner.class, flushMode = FlushModeType.COMMIT)
public interface UserRepository extends EntityRepository<User, String> {

  @Query("select u from User u where u.name=:name")
  User findByName(@QueryParam("name") String name);


}
