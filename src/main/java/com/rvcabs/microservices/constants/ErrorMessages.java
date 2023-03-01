package com.rvcabs.microservices.constants;

public interface ErrorMessages {

    String INVALID_ACCOUNT_ID = "Invalid AccountId";
    String INVALID_STATUS = "Invalid Status";
    String INVALID_PROGRAM_ID = "Invalid ProgramId";
    String INVALID_BIN_ID = "Invalid Bin Id";
    String INVALID_CUSTOMER_ID = "Invalid CustomerId";
    String INVALID_MOBILE_NUMBER = "Invalid Mobile Number";
    String APPLICATION_ID_IS_EMPTY = "Application Id is empty";
    String APPLICATION_ID_CLIENT_ID_CLIENT_SECRET_MANDATORY ="Application Id,Client Id and Client Secret are mandatory";
    String ROLE_NMAE_REQUIRED="Role Name is Required";
    String AUTHORITIES_REQUIRED="Authorities are required";
    String USER_TYPE_REQUIRED="UserType is required";
    String CREATED_BY_REQUIRED= "Created By is required";
    String UPDATED_BY_REQUIRED= "Updated By is required";
    String CLIENT_ID_CLIENT_SECRET_MANDATORY ="Client Id and Client Secret are mandatory";
    String INVALID_USER ="Invalid User";
    String EXCEPTION_OCCURED ="Exception occured";
    String INVALID_CARD_NUMBER = "Invalid Card Number";
    String BRANCH_NAME_ALREADY_EXISTS = "Branch Name already Exists";
    String NO_BRANCHES_FOUND = "No Branches Found";
    String UNABLE_TO_CREATE_BRANCH = "Unable to create new branch";
    String UNABLE_TO_GET_ALL_BRANCHES_FOR_GIVEN_CUSTOMER ="Unable to get All Branches for a given Customer";
    String INVALID_BRANCH_ID = "Invalid Branch Id";
    String UNABLE_TO_GET_ALL_BRANCHES_FOR_BRANH_ID ="Unable to get Branche Details for a given Branch Id";
    String EXCEPTION_OCCURRED_WHILE_SENDING_BRANCH_DETAILS ="Exception occurred while sending branch details";
    String UNABLE_TO_UPDATE_BRANCH="Unable to update Branch";
    String UNABLE_TO_GET_THE_BRANCH = "Unable to get the Branch";
    String INVALID_BRANCH_DATA = "Invalid Branch Data";
    String UNABLE_TO_DELETE_BRANCH = "Unable to delete Branch";
    String NO_BRANCH_EXIST_WITH_GIVEN_ID = "No Branch exist with given id";


   String UNABLE_TO_GET_PREFENRENCE_VALUES = "Unable to get the Preferences Values";
   String UNABLE_TO_GET_ALL_CUSTOMERS = "Unable to get All Customers";
   String INVALID_FEE_ENTITY_ID ="Invalid FeeEntity Id";
   String INVALID_DATA_PRESENT_IN_THE_REQUEST_FOR_CARD_PROGRAM_ID_FEE_ENTITY_ID_STAKE_HOLDER_ID ="Invalid Data present in the request for Card Program Id/FeeEntity Id/Stake Holder Id";
   String UNABLE_TO_UPDATE_FEE_ENTITY ="Unable to update FeeEntity";
   String UNABLE_TO_GET_FEE_ENTITY_DETAILS = "Unable to get FeeEntity Details";
   String UNABLE_TO_DELETE_FEE_ENTITY = "Unable to delete FeeEntity";
   String ERROR_WHILE_PREPARING_THE_PROGRAM_LIST_IN_PROGRAM_SERVICE = "Error : While Preparing the ProgramList in ProgramService :";
   String TRANSACTION_TYPE_ID_REQUIRED = "Transaction Type Id Required";
   String INVALID_TRANSACTION_TYPE_ID = "Invalid Transaction Type Id";
   String CURRENCY_CODE_REQUIRED = "Currency Code Required";
   String INVALID_CURRENCY_CODE = "Invalid Currency Code";
   String FIXED_PERCENTAGE_FEE_IS_MANDATORY = "Fixed/Percentage fee is mandatory";
   String MULTIPLE_ENTRIES_FOUND_FOR_SAME_CURRENCY_AND_TRANSACTION_TYPE = "Multiple Entries found for same Currency and Transaction Type";
   String FROM_AMOUNT_TO_AMOUNT_NOT_ALLOWED_IN_CURRENCYFEE = "from amount, to amount not allowed in currencyfee";
   String SLAB_AMOUNTS_ARE_REQUIRED = "Slab amounts are required";
   String SLAB_AMOUNTS_SEQUNCE_IS_NOT_VALID = "Slab amounts sequnce is not valid";
   String INVALID_FEETYPE_ID = "Invalid feetypeId";
   String NO_PROGRAM_EXISTS = "No Program Exists";
   String ALL_AUTHORITIES = "All Authorities";
   String ERROR_WHILE_FETCHING_THE_ALL_ROLES_IN_ROLE_SERVICE_IMPL = "Error : While Fetching the All Roles in RoleServiceImpl : ";
   String ROLES_FETCH_SUCCESSFUL = "Roles Fetch Successful";
   String FOR_BOTH_PAYER_AND_PAYEE_THRESHOLD_DETAILS_REQUIRED = "For Both Payer and Payee Threshold details required";
   String INVALID_TRANSACTION_TYPE = "Invalid Transaction Type";
   String TOKEN_PERFORMER_MISMATCH="Token Provided is Not Matching with Action Performer";


}
