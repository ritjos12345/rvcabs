package com.rvcabs.microservices.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

public class Constants {

	public static final String MM_DD_YYYY_HH_MM_SS_AMPM_WITH_SLASH="MM/dd/yyyy hh:mm:ss aa";
	
	public static final String MM_DD_YYYY_WITH_SLASH="MM/dd/yyyy";
	
	public static final String YYYY_MM_DD_WITH_HYPHEN="yyyy-MM-dd";
	
	public static final String CLIENT_ID="clientId";
	public static final String CLIENT_SECRET="clientSecret";
	public static final String APPLICATION_ID="applicationId";
	
	public static final String CARD_STATUS_ACTIVATE="ACTIVATE";
	public static final String CARD_STATUS_CLOSE="CLOSE";
	public static final String CARD_STATUS_SUSPEND="SUSPEND";
	public static final String CARD_STATUS_UNSUSPEND="UNSUSPEND";
	public static final String CARD_STATUS_LOSTSTOLEN="LOSTSTOLEN";
	public static final String CARD_STATUS_MARK_PFRAUD="MARK_PFRAUD";
	public static final String CARD_STATUS_UNMARK_PFRAUD="UNMARK_PFRAUD";
	public static final String CARD_STATUS_MARK_FRAUD="MARK_FRAUD";
	public static final String CARD_STATUS_ENABLE="ENABLE";
	
	
	public static final String ADJUSTMENT_CREDIT="CREDIT";
	public static final String ADJUSTMENT_DEBIT="DEBIT";
	
	public static final String PAYMENT_PROCESSOR_FIS="FIS";
	public static final String PAYMENT_PROCESSOR_ICHOOSE="ICHOOSE";
	
	public static final String TRANSACTION_INITIATED="TI";
	public static final String TRANSACTION_FAILED="TF";
	public static final String TRANSACTION_SUCCESS="TS";

	public static final String TRANSACTION_TYPE_CREDIT="CR";
	public static final String TRANSACTION_TYPE_DEBIT="DR";
	


	public static final String CARD_STATUS_ISSUED_INACTIVE="A";
	
	public static final String CARD_STATUS_ALL_TXNS_ALLOWEDED="B";
	
	public static final String CARD_STATUS_LOST_CARD="C";
	
	public static final String CARD_STATUS_STOLEN_CARD="D";
	
	public static final String CARD_STATUS_NO_WITHDRAWALS="E";
	
	public static final String CARD_STATUS_CLOSED="F";
	
	public static final String CARD_STATUS_LOST_NOT_CAPTURED="G";
	
	public static final String CARD_STATUS_STOLEN_NOT_CAPTURED="H";
	
	public static final String CARD_STATUS_IN_ACTIVE_OR_BLOCKED="I";
	
	public static final String CARD_STATUS_RE_ISSUE="R";
	
	public static final String CARD_STATUS_FRAUD_BLOCK="S";
	
	public static final String TRANSACTION_TYPE_ADMINISTRATIVE="A";
	
	public static final String TRANSACTION_TYPE_FINANCIAL="F";
	
	public static final String TRANSACTION_TYPE_NETWORK="N";
	
	public static final String DEVICE_TYPE_ATM="A";
	
	public static final String DEVICE_TYPE_POS="P";
	
	public static final String DEVICE_TYPE_WEB="W";
	
	public static final String DEVICE_TYPE_IVR="I";
	
	public static final String DEVICE_TYPE_API="H";
	
	public static final String DEVICE_TYPE_OVER_THE_COUNTER="O";
	
	public static final String DEVICE_TYPES_SELF_SERVICE_TERMINAL="X";
	
	public static final String CARD_BALANCE_THRESHOLD_ALERT_TYPE="BT";
	
	public static final String DEBIT_TRANSACTION_POSTED_ALERT_TYPE="DP";
	
	public static final String CREDIT_TRANSACTION_POSTED_ALERT_TYPE="CP";
	
	public static final String BILL_PAYMENT_ALERT_TYPE="BP";
	
	public static final String MERCHANT_CATEGORY_CODE_ALERT_TYPE="MCC";
	
	public static final String WRONG_PIN_ATTEMPT_ALERT_TYPE="PDCL";
	
	public static final String PROMO_CODE_ALERT_TYPE="PROMO";
	
	public static final String NON_SUFFICIENT_FUNDS_ALERT_TYPE="NSF";
	
	public static final String CARD_ACTIVATION_ALERT_TYPE="CA";
	
	public static final String ACCOUNT_CLOSE_ALERT_TYPE="AC";
	
	public static final String UPDATE_PROFILE_ALERT_TYPE="UP";
	
	public static final String PASSWORD_CHANGE_ALERT_TYPE="PC";

	public static final String INTERNATIONAL_TXN_ALERT_TYPE="ITA";
	
	public static final String DECLINED_PRE_AUTH_ALERT_TYPE="DPA";
	
	public static final String MMDDYYYY_WITH_SLASH="MM/dd/yyyy";
	
	public static final String THRERSHOLD_TYPE_GREATER_THAN_OR_EQUAL_TO="G";
	
	public static final String THRERSHOLD_TYPE_LESS_THAN_OR_EQUAL_TO="L";
	
	public static final String CONTACT_US_CARD_ISSUES="Card Issues And Questions";
	
	public static final String CONTACT_US_OTHERS="Others";
	
    public static final String YES = "Y";
    
    public static final String NO = "N";

    public static final String STATUS="status";

    public static final String MESSAGE="message";

    public static final String SUPER_ADMIN="SUPER_ADMIN";
    
    public static final String LOGIN_WITH_USER_NAME="USER_NAME";
    
    public static final String LOGIN_WITH_EMAIL_ADDRESS="EMAIL_ADDRESS";
    
    public static final String LOGIN_WITH_MOBILE_NUMBER="MOBILE_NUMBER";
    
    public static final String LOGIN_WITH_CONSUMER_ID="CONSUMER_ID";
    
    public static final String LOGIN_PREFERENCE="LOGIN_PREFERENCE";
    
    public static final String STATUS_ACTIVE="Y";

	public static final String STATUS_SUSPEND="S";

	public static final String STATUS_PENDING="P";

	public static final String STATUS_RESUME="Y";

	public static final String STATUS_DELETED="N";

	public static final String STATUS_ACTIVE_DESCRIPTION="Active";

	public static final String STATUS_SUSPEND_DESCRIPTION="Suspend";

	public static final String STATUS_PENDING_DESCRIPTION="Pending";

	public static final String STATUS_RESUME_DESCRIPTION="Resume";

	public static final String STATUS_DELETE_DESCRIPTION="In Active";
    
    public static final String ALL="ALL";
    
    public static final String TOP_CURRENCIES="TOP_CURRENCIES";
    
    public static final String DEFAULT_CURRENCY="DEFAULT_CURRENCY";
    
    public static final String DEFAULT_RESIDENCE_COUNTRY="DEFAULT_RESIDENCE_COUNTRY";
    
    public static final String EMAIL_FROM="EMAIL_FROM";
    
    public static final String EMAIL_FROM_NAME="EMAIL_FROM_NAME";

    public static final String EMAIL_COPY_RIGHTS_FROM="EMAIL_COPY_RIGHTS_FROM";
    
    public static final String PRIVACY_POLICY_URL="PRIVACY_POLICY_URL";
    
    public static final String TERMS_AND_CONDITIONS_URL="TERMS_AND_CONDITIONS_URL";

    public static final String LOGO_URL="LOGO_URL";
    
    public static final String REGARDS_FROM="REGARDS_FROM";
    
    public static final String USER_REGISTRATION="USER_REGISTRATION";
    
    public static final String FORGOT_PASSWORD="FORGOT_PASSWORD";
   
    public static final String CHANGE_PASSWORD="CHANGE_PASSWORD";
    
    public static final String MY_ACCOUNT_URL="MY_ACCOUNT_URL";
    
    public static final String CONTACT_SUPPORT_URL="CONTACT_SUPPORT_URL";

    public static final String ALL_CUSTOMERS="ALL";

	public static final String APP_NAME="APP_NAME";
	public static final String SUPPORT_EMAIL_ID="SUPPORT_EMAIL_ID";
	public static final String APP_PORTAL="APP_PORTAL";

    public static final String WALLET_API_CURRENCY_UPDATE_API="/consumer/public/programupdate/notify";
    public static final String WALLET_API_POOL_ACCOUNT_CREATE_API="/consumer/public/programpool/notify";

	public static final String WALLET_POGRAM_CACHE_UPDATE="/consumer/public/program/cacheupdate";

	public static final String DAILY = "1";
	public static final String WEEKLY = "2";
	public static final String MONTHLY = "3";
	public static final String ALL_PROGRAM_WALLETS_CREATED="/consumer/public/allWalletsCreated";
	public static final String MASTER_CURRENCIES_LIST="/public/currency/listall";
	public static final String MASTER_COUNTRY_LIST="/countries/listall";
	public static final String MASTER_STATE_LIST="/countries/listAllStates";


	public static final String OXYGEN_GLOBAL_BOOSTER_APP="OXYGEN_GLOBAL_BOOSTER_APP";
	public static final String OXYGEN_GLOBAL_KAFKA_SERVER="OXYGEN_GLOBAL_KAFKA_SERVER";
	public static final String EMAIL_NOTIFICATION_CONFIRMATION="EMAIL_NOTIFICATION_CONFIRMATION";
	public static final String EMAIL_NOTIFICATION_SERVER="EMAIL_NOTIFICATION_SERVER";
	public static final String UTF_8 = "UTF-8";
	public static final String UPDATED_ON = "updatedOn";
	public static final String WALLET_APP = "WALLET_APP";
	public static final String WALLET_PROGRAM_CACHE_UPDATE = "WALLET_PROGRAM_CACHE_UPDATE";
	public static final String WALLET_BALANCE_MODIFICATION = "WALLET_BALANCE_MODIFICATION";
	public static final String PAYER= "PAYER";
	public static final String PAYEE= "PAYEE";
	public static final StringBuffer OWN_BIN_ID=new StringBuffer("123456");
	public static final List<String> EXISTING_OPEN_NETWORKS= Lists.newArrayList("AMEX","DISCOVER","MASTER","RUPAY","VISA");
	public static final StringBuffer SALT=new StringBuffer("0000000000000000");
	public static final String HMAC_PARAM="hmac-value";

	public static final Map<String,String> AUDIT_MAP= new HashMap<>(100);


	static {
		AUDIT_MAP.put("/customer/private/listAll","getCustomerList");
		AUDIT_MAP.put("/customer/private/getcustomerbyaccountId","getCustomerList");
		AUDIT_MAP.put("/public/signIn","Login");
		AUDIT_MAP.put("/roles/private/userType/listAll","Get UserTypes");
		AUDIT_MAP.put("/batch/private/uploadEmbossFile","Upload Emboss File");
		AUDIT_MAP.put("/program/private/getEmbossDetails","Get Emboss Details Based On Docket Number");
		AUDIT_MAP.put("/roles/private/roles/listAllUserCreated","Get All Roles List");
		AUDIT_MAP.put("/roles/private/userType/listAllByLoggedUserType","Get UserType List");
		AUDIT_MAP.put("/roles/private/authorities/listAll","Get Authorities List");
		AUDIT_MAP.put("/roles/private/create/role","Create Role");
		AUDIT_MAP.put("/roles/private/authorities/getByRoleIdAndUserType","Get All Authorities By Using RoleId And UserType");
		AUDIT_MAP.put("/roles/private/update/role","Modify Existing Role");
		AUDIT_MAP.put("/roles/private/roles/listAll","List All Roles");
		AUDIT_MAP.put("/program/private/getActivePrograms","Get All Active Programs");
		AUDIT_MAP.put("/customer/private/create","Create New Customer");
		AUDIT_MAP.put("/customer/private/getByCustomerIdAndStatus","Get Customer Based On CustomerId And Status");
		AUDIT_MAP.put("/customer/private/update","Modify Customer");
		AUDIT_MAP.put("/bin/public/getBinByCustomerId","Get BinDetails Based On BinId And CustomerId");
		AUDIT_MAP.put("/bin/private/create","Create New Bin");
		AUDIT_MAP.put("/bin/private/update","Modify Existing BIN");
		AUDIT_MAP.put("/bin/private/delete","Delete Existing BIN");
		AUDIT_MAP.put("/program/private/getprogram","Get Program Details By ProgramId");
		AUDIT_MAP.put("/program/private/getAllPrograms","Get All Program Details List");
		AUDIT_MAP.put("/program/private/getProgramByCustomerId","Get All Program Details Based On CustomerId");
		AUDIT_MAP.put("/program/private/updateProgramStatus","Activate/Suspend the Program Status Based On ProgramId ");
		AUDIT_MAP.put("/program/private/create","Create New Program");
		AUDIT_MAP.put("/program/private/update","Update Program");
		AUDIT_MAP.put("/program/private/createProgramForFee","Create Program With Fee Details");
		AUDIT_MAP.put("/program/private/updateProgramForFee","Modify Program With Fee Details");
		AUDIT_MAP.put("/program/private/delete","Delete Program");
		AUDIT_MAP.put("/threshold/private/create","Create Limit");
		AUDIT_MAP.put("/threshold/private/update","Modify Limit");
		AUDIT_MAP.put("/program/private/listAllProgramCategories","Get All Program Categories");
		AUDIT_MAP.put("/program/private/listAllAssociations","Get All Card Associations Types (VISA/MASTERCARD..etc) ");
		AUDIT_MAP.put("/program/private/listAllProgramSubTypes","Get All Program SubTypes ");
		AUDIT_MAP.put("/program/private/listAllTransactionTypes","Get All Transactions List");
		AUDIT_MAP.put("/program/private/listAllProductTypesBasedOnProgramCategoryId","Get All Product Types ");
		AUDIT_MAP.put("/private/getLimitUserTypes","Get UserTypes For Limits");
		AUDIT_MAP.put("/program/private/activateprogramchecks","Get Program PreConditions Status");
		AUDIT_MAP.put("/program/private/activateprogram","Activate Program");
		AUDIT_MAP.put("/program/private/getProgramVersionsByProgramId","Get Program Versions");
		AUDIT_MAP.put("/fee/getFeeVersionsByProgramId","Get Fee Versions");
		AUDIT_MAP.put("/program/private/getProgramById","Get Program By Id");
		AUDIT_MAP.put("/preferences/private/list","Get Preferences List");
		AUDIT_MAP.put("/preferences/private/updatepreferences","Update Preferences List");
		AUDIT_MAP.put("/fee/create","Create Fee");
		AUDIT_MAP.put("/fee/update","Modify Fee");
		AUDIT_MAP.put("/fee/delete","Remove Fee");
		AUDIT_MAP.put("/feetype/private/feetype/all","Fee Types List");
		AUDIT_MAP.put("/program/private/getKycTypeListByProgramId","Get Kyc Type List");
		AUDIT_MAP.put("/private/signup","Create User");
		AUDIT_MAP.put("/private/list/listAllApplicationUsersByAccountId","Get All Users By AccountId");
		AUDIT_MAP.put("/private/list/listAllApplicationUsersByCustomerId","Get All Users By CustomerId");
		AUDIT_MAP.put("/private/user/viewApplicationUserDetailsByAccountId","View All Users By AccountId");
		AUDIT_MAP.put("/private/user/updateApplicationUser","Modify User");
		AUDIT_MAP.put("/private/user/deleteApplicationUser","Delete User");
		AUDIT_MAP.put("/customer/branch/public/listAllBranchesByAccountId","Get All Branches By AccountId");
		AUDIT_MAP.put("/customer/branch/public/listAll","Get All Branches List");
		AUDIT_MAP.put("/customer/branch/private/create","Create Branch");
		AUDIT_MAP.put("/customer/branch/private/delete","Delete Branch");
		AUDIT_MAP.put("/customer/branch/private/update","Modify Branch");
		AUDIT_MAP.put("/customer/branch/private/read","Read Branch");
		AUDIT_MAP.put("/customer/branch/public/listAllEntityTypes","Get All Entity Types List");
	};

	public static final Set<String> AUDIT_KEYS=AUDIT_MAP.keySet();
}
