package rewards.internal;

import common.money.MonetaryAmount;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

/**
 * Rewards an Account for Dining at a Restaurant.
 * 
 * The sole Reward Network implementation. This object is an application-layer service responsible for coordinating with
 * the domain-layer to carry out the process of rewarding benefits to accounts for dining.
 * 
 * Said in other words, this class implements the "reward account for dining" use case.
 */
public class RewardNetworkImpl implements RewardNetwork {

	private AccountRepository accountRepository;

	private RestaurantRepository restaurantRepository;

	private RewardRepository rewardRepository;

	/**
	 * Creates a new reward network.
	 * @param accountRepository the repository for loading accounts to reward
	 * @param restaurantRepository the repository for loading restaurants that determine how much to reward
	 * @param rewardRepository the repository for recording a record of successful reward transactions
	 */
	public RewardNetworkImpl(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
			RewardRepository rewardRepository) {
		this.accountRepository = accountRepository;
		this.restaurantRepository = restaurantRepository;
		this.rewardRepository = rewardRepository;
	}

	public RewardConfirmation rewardAccountFor(Dining dining) {
	  System.out.println("reward account, dining="+dining);
	  
	  // xTODO-01: Reward an account per the sequence diagram
	  String creditCardNumber = dining.getCreditCardNumber();
	  String merchantNumber = dining.getMerchantNumber();
	  System.out.println("credit card number="+creditCardNumber);
	  System.out.println("merchant number="+merchantNumber);
	  
	  Account account = accountRepository.findByCreditCard(creditCardNumber);
	  Restaurant restaurant = restaurantRepository.findByMerchantNumber(merchantNumber);
	  
	  MonetaryAmount rewardAmount = restaurant.calculateBenefitFor(account, dining);
	  System.out.println("rewardAmount="+rewardAmount);
	  
	  AccountContribution contribution = account.makeContribution(rewardAmount);
	  System.out.println("contribution=" + contribution);
	  accountRepository.updateBeneficiaries(account);
	  
		// xTODO-02: Return the corresponding reward confirmation
	  RewardConfirmation confirmation = rewardRepository.confirmReward(contribution, dining);
	  System.out.println("reward confirmation="+confirmation);
	  return confirmation;
	}
}