package rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * A system test that demonstrates how propagation settings affect transactional execution.
 */

/* TODO-04: Review the testPropagation() method below.  It runs rewardAccountFor() within an 
 * existing transaction and performs a manual rollback.  The assertions will succeed only if
 * a database commit actually occurs.  Run this test, initially it will fail - the data has 
 * been rolled back.  Proceed to the next step.
 */

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={SystemTestConfig.class})
public class RewardNetworkPropagationTests {

	/**
	 * The object being tested.
	 */
	@Autowired
	private RewardNetwork rewardNetwork;

	/**
	 * A template to use for test verification
	 */
	private JdbcTemplate template;

	/**
	 * Manages transaction manually
	 */
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	public void initJdbcTemplate(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Test
	public void testPropagation() {
		// Open a transaction for testing
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");
		System.out.println("calling rewardAccountFor");
		rewardNetwork.rewardAccountFor(dining);

		// Rollback the transaction test transaction
		System.out.println("Rolling back transaction");
		transactionManager.rollback(status);

		String sql = "select SAVINGS from T_ACCOUNT_BENEFICIARY where NAME = ?";
		assertEquals(Double.valueOf(4.00), template.queryForObject(sql, Double.class, "Annabelle"));
		assertEquals(Double.valueOf(4.00), template.queryForObject(sql, Double.class, "Corgan"));
	}
}