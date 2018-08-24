package rewards.internal.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * TODO-04: We have removed the JdbcAccountRepository. This replaces it.
 * <p>
 * 1. Modify this class to implement AccountRepository interface <br/>
 * 2. Annotate this class using a Spring stereotype annotation that indicates it is a
 * repository. <br/>
 * 3. Inject an EntityManager object via @PersistenceContext <br/>
 * 4. Define the method that will look up an Account given a
 * creditCardNumber string using a JPA query<br/>
 * </p>
 */
@Repository
public class JpaAccountRepository implements AccountRepository {

  @PersistenceContext
  private EntityManager em; 
  
  @Override
  public Account findByCreditCard(String creditCardNumber) {
    String jpql = "SELECT a FROM Account a where a.creditCardNumber = :creditCardNumber";
    Account account = em.createQuery(jpql, Account.class)
        .setParameter("creditCardNumber", creditCardNumber).getSingleResult();
    return account;
  }

}
