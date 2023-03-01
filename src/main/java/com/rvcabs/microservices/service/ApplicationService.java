package com.rvcabs.microservices.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
//import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

//import org.apache.axis2.databinding.types.xsd.DateTime;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.constants.ErrorMessages;
import com.rvcabs.microservices.dto.ApplicationSignInResponseDto;
import com.rvcabs.microservices.dto.ApplicationUserDto;
import com.rvcabs.microservices.dto.CarDetailDto;
import com.rvcabs.microservices.dto.DocumentDetailDto;
import com.rvcabs.microservices.dto.DriverInfoDto;
import com.rvcabs.microservices.dto.DriverTripDetailDto;
import com.rvcabs.microservices.dto.FileDto;
import com.rvcabs.microservices.dto.ForgotPasswordRequest;
import com.rvcabs.microservices.dto.MasterDataDto;
import com.rvcabs.microservices.dto.MasterDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.dto.TokenDto;
import com.rvcabs.microservices.entity.ApplicationUserEntity;
import com.rvcabs.microservices.entity.CarDetailsEntity;
import com.rvcabs.microservices.entity.DocumentDetailEntity;
import com.rvcabs.microservices.entity.DriverTripDetailEntity;
import com.rvcabs.microservices.exception.BadRequestException;
import com.rvcabs.microservices.exception.InternalServerException;
import com.rvcabs.microservices.exception.ResourceNotFoundException;
import com.rvcabs.microservices.exception.UnProcessibleException;
import com.rvcabs.microservices.mapper.CarDetailMapper;
import com.rvcabs.microservices.mapper.DocumentMapper;
import com.rvcabs.microservices.model.JwtUser;
import com.rvcabs.microservices.repository.ApplicationUserRepository;
import com.rvcabs.microservices.repository.CarDetailsRepository;
import com.rvcabs.microservices.repository.DriverDocumentRepository;
import com.rvcabs.microservices.repository.DriverTripDetailRepository;
import com.rvcabs.microservices.request.ApplicationUserRequest;
import com.rvcabs.microservices.request.ChangePasswordRequest;
import com.rvcabs.microservices.request.SignInRequest;
import com.rvcabs.microservices.security.JwtGenerator;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Transactional
@Service
public class ApplicationService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	static final String INVALID_APPLICATION_ID = "Invalid Application Id";
	static final String UNABLE_TO_RETRIVE_APP_DETAILS = "Unable to get application details by application Id";
	static final String UNABLE_TO_RETRIVE_USER_DETAILS = "Unable to get application user details by customer/Account Id";

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private CarDetailsRepository carDetailRepository;

	@Autowired
	private DriverDocumentRepository documentRepository;

	@Autowired
	private DriverTripDetailRepository driverTripDetailRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private NotificationService notify;
	
	private DriverTripDetailDto driverTripDetailDto;

	private ApplicationUserEntity applicationUser;

	private String clearPassword;

	static ModelMapper modelMapper = new ModelMapper();

	private MultiValueMap<String, String> headers = null;

	private Map<String, Object> responceMap;

	private StatusDto statusDto;

	private SecureRandom rand;

	static {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	@Bean
	public Pbkdf2PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Transactional(readOnly = false)
	public ResponseEntity<?> addNew(ApplicationUserRequest request) {
		headers = Utilities.getDefaultHeader();
		responceMap = new HashMap<String, Object>();
		String accountId;
		String password;
		try {
			accountId = applicationUserRepository.findAccountIdByUserName(request.getMobileNumber());
		} catch (Exception ex) {
			logger.error("Unable to check if user name exists", ex);
			throw new InternalServerException();
		}
		if (accountId != null) {
			headers.add(Constants.STATUS, HttpStatus.CONFLICT.toString());
			headers.add(Constants.MESSAGE, "Given User Name already exists");
			responceMap.put(Constants.STATUS, HttpStatus.CONFLICT.toString());
			responceMap.put(Constants.MESSAGE, "Given User Name already exists");
			responceMap.put("AccountId", accountId);
			return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);

		}
		try {
			applicationUser = modelMapper.map(request, ApplicationUserEntity.class);
			// Treating Mobile Number as username here
			applicationUser.setUserName(request.getMobileNumber());

			if (applicationUser.getPassword() == null) {
				int noOfCAPSAlpha = 3;
				int noOfDigits = 2;
				int noOfSplChars = 2;
				int minLen = 8;
				int maxLen = 15;

				char[] passwordChar = Utilities.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
				clearPassword = String.valueOf(passwordChar).trim();
				password = passwordEncoder().encode(clearPassword);
				applicationUser.setPassword(password);
			} else {
				clearPassword = applicationUser.getPassword();
				password = passwordEncoder().encode(applicationUser.getPassword());
				applicationUser.setPassword(password);
			}
			applicationUser.setLastPasswordUpdated(ZonedDateTime.now());

			applicationUser = applicationUserRepository.save(applicationUser);

			// Saving CarDetails If Account Created Successfully
			if (applicationUser.getUserType().equalsIgnoreCase("DRIVER") && applicationUser.getAccountId() != null
					&& !applicationUser.getAccountId().equals("")) {
				request.getCarDetailDto().setAccountId(applicationUser.getAccountId());
				CarDetailsEntity carDetailsEntity = CarDetailMapper.mapFromDto(request.getCarDetailDto());
				carDetailRepository.save(carDetailsEntity);

				request.getDocumentDetailDtos().forEach(f -> f.setAccountId(applicationUser.getAccountId()));
				Iterable<DocumentDetailEntity> iterable = DocumentMapper.mapFromDto(request.getDocumentDetailDtos());
				documentRepository.saveAll(iterable);
			}
			// Creating new entry in DriverTripDetail Table
			if (applicationUser.getUserType().equalsIgnoreCase("DRIVER") && applicationUser.getAccountId() != null
					&& !applicationUser.getAccountId().equals("")) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);
				driverTripDetailDto = new DriverTripDetailDto();
				driverTripDetailDto.setIsOngoingTrip(false);
				driverTripDetailDto.setAccountId(applicationUser.getAccountId());
				driverTripDetailDto.setMobileNumber(applicationUser.getMobileNumber());
				driverTripDetailDto.setDriverName(applicationUser.getFirstName() + " " + applicationUser.getLastName());
				driverTripDetailDto.setNextTripStartTime(calendar.getTime());
				driverTripDetailDto.setOnGoingTripStartTime(calendar.getTime());
				driverTripDetailDto.setDeviceToken("jkjkjkjk");
				driverTripDetailDto.setActive(true);
				driverTripDetailDto.setDriverLat("22");
				driverTripDetailDto.setDriverLng("44");
				driverTripDetailDto.setTotalDistance(0.0f);
				driverTripDetailDto.setTotalRating(0.0f);
				Date dt = new Date();
				driverTripDetailDto.setNextTripStartTime(dt);
				driverTripDetailDto.setOnGoingTripStartTime(dt);
				//				driverTripDetailRepository.save(GenericMapper.mapper.map(driverTripDetailDto, DriverTripDetailEntity.class));
				driverTripDetailRepository.save(modelMapper.map(driverTripDetailDto, DriverTripDetailEntity.class));//failing here
			}

		} catch (BadRequestException be) {
			throw be;
		} catch (ResourceNotFoundException re) {
			logger.error("Unable to send Email Confirmation Link Push to Application User", re);
			throw new InternalServerException("Something Went Wrong,Please try again after sometime");
		} catch (Exception ex) {
			logger.error("Unable to add new Application User", ex);
			throw new InternalServerException();
		}

		try {

			notify.sendSms(applicationUser.getMobileNumber(),
					"Hello" + " " + applicationUser.getFirstName() + " " + applicationUser.getLastName()
							+ "UserId and Password for RVCab login is UI:" + applicationUser.getUserName() + " "
							+ "PWD:" + clearPassword);
		} catch (Exception exception) {
		}

		if (applicationUser.getAccountId() == null || applicationUser.getAccountId().isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Registration Fail");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Registration is successfull");
			responceMap.put(Constants.STATUS, HttpStatus.OK.toString());
			responceMap.put(Constants.MESSAGE, "Given User Name already exists");
			responceMap.put("AccountId", accountId);
		}
		return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);

		// return applicationUser.getAccountId();
	}

	@Transactional(readOnly = false)
	public ApplicationSignInResponseDto SignIn(SignInRequest request) {
		headers = Utilities.getDefaultHeader();
		ApplicationSignInResponseDto result = new ApplicationSignInResponseDto();
		TokenDto token = null;
		ApplicationUserEntity existingUser = applicationUserRepository
				.findByUserNameAndActiveTrue(request.getUserName().trim());
		if (null == existingUser) {
			throw new ResourceNotFoundException("User Not Exist");
		}
		ApplicationUserDto applicationUser = toDto(existingUser);
		if (!passwordEncoder().matches(request.getPassword(), applicationUser.getPassword())) {
			throw new UnProcessibleException("Invalid Credentials");
		}
		/*
		 * if (!Constants.STATUS_ACTIVE.equals(applicationUser.getStatus())) { throw new
		 * UnProcessibleException("User is Not Active"); }
		 */

		try {

			// Updating DeviceToken in ApplicationUser Table
			driverTripDetailRepository.updateDeviceToken(request.getDeviceToken(), applicationUser.getAccountId());
			/*
			 * CustomerEntity customerEntity =
			 * customerRepository.getByCustomerIdAndStatus(existingUser.getCustomerId(),
			 * Status.ACTIVE.toString());
			 */
			JwtGenerator generator = new JwtGenerator();
			// List<RoleAuthoriesEntity> rolesList =
			// roleAuthoritiesRepository.findByRoleId(applicationUser.getRoleId());

			List<GrantedAuthority> grantedAuthoritiesBasedOnRoles = new ArrayList<>();
			// rolesList.forEach(ent -> grantedAuthoritiesBasedOnRoles.add(new
			// SimpleGrantedAuthority(ent.getAuthCode())));

			JwtUser jwtUser = new JwtUser(applicationUser.getUserName(), applicationUser.getAccountId());
			ResponseEntity<?> tokenResonse = generator.generate(jwtUser);
			token = (TokenDto) tokenResonse.getBody();
			token.setAccountId(applicationUser.getAccountId());
			/*
			 * if (null == applicationUser.getLastPasswordUpdated()) {
			 * token.setPerformChangePassword(true); }
			 */
			/*
			 * token.setClientId(customerEntity.getClientId());
			 * token.setClientSecret(customerEntity.getClientSecret());
			 */
			// token.setLastSuccessfulLogin(applicationUser.getLastLoggedIn());
			token.setUserType(applicationUser.getUserType());
			// token.setCustomerId(customerEntity.getCustomerId());
			/*
			 * if (!rolesList.isEmpty()) { result =
			 * roleService.retriveAndPrepareAuthoritiesAfterSignInBasedOnRoleId(
			 * applicationUser.getRoleId(),applicationUser.getUserType()); }
			 */

			applicationUserRepository.updateLastLoggedIn(ZonedDateTime.now(), applicationUser.getAccountId());
		} catch (Exception e) {
			logger.error("Unable to retrieve token details", e);
			throw new InternalServerException(e.getMessage());
		}
		result.setTokenDto(token);

		return result;
	}

	@Transactional(readOnly = true)
	public ApplicationUserDto findById(String accountId) {
		Optional<ApplicationUserEntity> entity = null;
		try {
			entity = applicationUserRepository.findById(accountId);
		} catch (Exception ex) {
			logger.error("Unable to get application user by user name", ex);
			throw new InternalServerException();
		}
		if (entity.isPresent()) {
			return toDto(entity.get());
		} else {
			throw new ResourceNotFoundException("Invalid Account Id");
		}
	}

	public String getClearTextUsingPrivateKey(PrivateKey privateKey, String encryptedText) {
		String clearText = "";
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			// clearText = getclearText(privateKey, encryptedText, clearText, cipher);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clearText;

	}

	public String getClearTextUsingPublicKey(PublicKey publicKey, String encryptedText) {
		String clearText = "";
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			clearText = getclearTextString(publicKey, encryptedText, clearText, cipher);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clearText;
	}

	private String getclearTextString(PublicKey publicKey, String encryptedText, String clearText, Cipher cipher) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] encodedUser = DatatypeConverter.parseBase64Binary(encryptedText);
			clearText = new String(cipher.doFinal(encodedUser), Constants.UTF_8);
		} catch (Exception e) {
			logger.error(ErrorMessages.EXCEPTION_OCCURED, e);
		}
		return clearText;
	}

	public String getEncryptedTextUsingPublicKey(PublicKey publicKey, String clearText) {
		String encryptedText = "";
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			encryptedText = getEncryptedText(publicKey, clearText, encryptedText, cipher);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedText;
	}

	private String getEncryptedText(PublicKey publicKey, String clearText, String encryptedText, Cipher cipher) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedText = Base64.encodeBase64String(cipher.doFinal(clearText.getBytes(Constants.UTF_8)));
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return encryptedText;
	}

	public String getEncryptedTextUsingPrivateKey(PrivateKey privateKey, String clearText) {
		String encryptedText = "";
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			encryptedText = getEncryptedText(privateKey, clearText, encryptedText, cipher);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedText;
	}

	private String getEncryptedText(PrivateKey privateKey, String clearText, String encryptedText, Cipher cipher) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			encryptedText = Base64.encodeBase64String(cipher.doFinal(clearText.getBytes(Constants.UTF_8)));
		} catch (Exception e) {
			logger.error("Exception occured", e);
		}
		return encryptedText;
	}

	public ResponseEntity<?> getDriverDetailByDriverId(String driverAccountId) {
		headers = Utilities.getDefaultHeader();
		CarDetailsEntity carDetailsEntity = null;
		DriverInfoDto driverInfoDto = new DriverInfoDto();
		try {
			String query = "select e.accountId,e.firstname,e.lastname,e.mobileNumber,e.emailId,e.secMobileNumber from applicationuser e where e.accountId=:accountId";
			Query qry = entityManager.createNativeQuery(query).setParameter("accountId", driverAccountId);
			Object[] object = (Object[]) qry.getSingleResult();
			if (object != null) {
				driverInfoDto.setAccountId((String) object[0]);
				driverInfoDto.setFirstName((String) object[1]);
				driverInfoDto.setLastName((String) object[2]);
				driverInfoDto.setMobileNumber((String) object[3]);
				driverInfoDto.setEmailId((String) object[4]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
		}

		if (driverInfoDto.getAccountId() != null) {
			carDetailsEntity = carDetailRepository.findByAccountId(driverAccountId);
			driverInfoDto.setCarDetailDto(GenericMapper.mapper.map(carDetailsEntity, CarDetailDto.class));

			try {
				String query = "SELECT id,carmodel FROM syscarmaster where id=:id";
				Query qry = entityManager.createNativeQuery(query).setParameter("id",
						carDetailsEntity.getSysCarMaster().getId());
				Object[] object = (Object[]) qry.getSingleResult();
				if (object != null) {
					MasterDataDto type = new MasterDataDto();
					type.setId(((Integer) object[0]).longValue());
					type.setName((String) object[1]);
					driverInfoDto.getCarDetailDto().setType(type);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
			}

			try {
				String query = "SELECT id,carsubtypename FROM syscarsubtype where id=:id";
				Query qry = entityManager.createNativeQuery(query).setParameter("id",
						carDetailsEntity.getSysCarMaster().getId());
				Object[] object = (Object[]) qry.getSingleResult();
				if (object != null) {
					MasterDataDto subtype = new MasterDataDto();
					subtype.setId(((Integer) object[0]).longValue());
					subtype.setName((String) object[1]);
					driverInfoDto.getCarDetailDto().setSubType(subtype);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
			}

			try {
				String query = "SELECT id,name FROM syscarcategory where id=:id";
				Query qry = entityManager.createNativeQuery(query).setParameter("id",
						carDetailsEntity.getSysCarCategoryEntity().getId());
				Object[] object = (Object[]) qry.getSingleResult();
				if (object != null) {
					MasterDataDto category = new MasterDataDto();
					category.setId(((Integer) object[0]).longValue());
					category.setName((String) object[1]);
					driverInfoDto.getCarDetailDto().setCarCategory(category);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
			}

		}
		if (driverInfoDto == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Driver detail are not found ");
			return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Driver are found successfully");
		}
		return new ResponseEntity<>(driverInfoDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> driverSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		CarDetailsEntity carDetailsEntity = null;
		List<Object[]> list;
		List<DriverInfoDto> masterList = new ArrayList<DriverInfoDto>();
		if (searchTerm != null && !searchTerm.equals("")) {
			String query = "select e.accountId,e.firstname,e.lastname,e.mobileNumber,e.emailId,e.secMobileNumber from applicationuser e where e.active=1 and (e.userType='DRIVER' and e.firstName like '%"
					+ searchTerm + "%' or e.lastName like '%" + searchTerm + "%' or e.accountId like '%" + searchTerm
					+ "%')";
			Query qry = entityManager.createNativeQuery(query);
			list = qry.getResultList();
			if (list != null) {
				for (Object[] obj : list) {
					DriverInfoDto driverInfoDto = new DriverInfoDto();
					driverInfoDto.setAccountId((String) obj[0]);
					driverInfoDto.setFirstName((String) obj[1] + " " + (String) obj[2]);
					driverInfoDto.setMobileNumber((String) obj[3]);
					driverInfoDto.setEmailId((String) obj[4]);
					masterList.add(driverInfoDto);
				}
			}
		} else {
			String query = "select e.accountId,e.firstname,e.lastname,e.mobileNumber,e.emailId,e.secMobileNumber from applicationuser e where e.active=1 and e.userType='DRIVER'";
			Query qry = entityManager.createNativeQuery(query);
			list = qry.getResultList();
			if (list != null) {
				for (Object[] obj : list) {
					DriverInfoDto driverInfoDto = new DriverInfoDto();
					driverInfoDto.setAccountId((String) obj[0]);
					driverInfoDto.setFirstName((String) obj[1] + " " + (String) obj[2]);
					driverInfoDto.setMobileNumber((String) obj[3]);
					driverInfoDto.setEmailId((String) obj[4]);
					masterList.add(driverInfoDto);
				}
			}
		}

		for (DriverInfoDto driverInfoDto : masterList) {
			if (driverInfoDto.getAccountId() != null) {
				carDetailsEntity = carDetailRepository.findByAccountId(driverInfoDto.getAccountId());
				driverInfoDto.setCarDetailDto(modelMapper.map(carDetailsEntity, CarDetailDto.class));

				try {
					String query = "SELECT id,carmodel FROM syscarmaster where id=:id";
					Query qry = entityManager.createNativeQuery(query).setParameter("id",
							carDetailsEntity.getSysCarMaster().getId());
					Object[] object = (Object[]) qry.getSingleResult();
					if (object != null) {
						MasterDataDto type = new MasterDataDto();
						type.setId(((Integer) object[0]).longValue());
						type.setName((String) object[1]);
						driverInfoDto.getCarDetailDto().setType(type);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
				}

				try {
					String query = "SELECT id,carsubtypename FROM syscarsubtype where id=:id";
					Query qry = entityManager.createNativeQuery(query).setParameter("id",
							carDetailsEntity.getSysCarMaster().getId());
					Object[] object = (Object[]) qry.getSingleResult();
					if (object != null) {
						MasterDataDto subtype = new MasterDataDto();
						subtype.setId(((Integer) object[0]).longValue());
						subtype.setName((String) object[1]);
						driverInfoDto.getCarDetailDto().setSubType(subtype);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
				}

				try {
					String query = "SELECT id,name FROM syscarcategory where id=:id";
					Query qry = entityManager.createNativeQuery(query).setParameter("id",
							carDetailsEntity.getSysCarCategoryEntity().getId());
					Object[] object = (Object[]) qry.getSingleResult();
					if (object != null) {
						MasterDataDto category = new MasterDataDto();
						category.setId(((Integer) object[0]).longValue());
						category.setName((String) object[1]);
						driverInfoDto.getCarDetailDto().setCarCategory(category);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("ApplicationService:getDriverDetailByDriver:" + e.getMessage());
				}

			}
		}

		if (masterList == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Driver are not found ");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Driver are found successfully");

		}
		return new ResponseEntity<>(masterList, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> customerSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();

		List<Object[]> list;
		List<ApplicationUserRequest> userList = new ArrayList<ApplicationUserRequest>();
		if (searchTerm != null && !searchTerm.equals("")) {
			String query = "select e.accountId,e.firstname,e.lastname,e.mobileNumber,e.emailId,e.secMobileNumber,e.employID from applicationuser e where e.userType='CUSTOMER' and e.firstName like '%"
					+ searchTerm + "%' or e.lastName like '%" + searchTerm + "%'";
			Query qry = entityManager.createNativeQuery(query);
			list = qry.getResultList();
			if (list != null) {
				for (Object[] obj : list) {
					ApplicationUserRequest applicationUserRequest = new ApplicationUserRequest();
					applicationUserRequest.setAccountId((String) obj[0]);
					applicationUserRequest.setMobileNumber((String) obj[3]);
					applicationUserRequest.setFirstName((String) obj[1]);
					applicationUserRequest.setLastName((String) obj[2]);
					applicationUserRequest.setSecMobileNumber((String) obj[5]);
					applicationUserRequest.setEmailId((String) obj[4]);
					applicationUserRequest.setEmployID((String) obj[6]);
					applicationUserRequest.setUserType("CUSTOMER");
					userList.add(applicationUserRequest);
				}
			}
		} else {
			String query = "select e.accountId,e.firstname,e.lastname,e.mobileNumber,e.emailId,e.secMobileNumber,e.employID from applicationuser e where e.userType='CUSTOMER'";
			Query qry = entityManager.createNativeQuery(query);
			list = qry.getResultList();
			if (list != null) {
				for (Object[] obj : list) {
					ApplicationUserRequest applicationUserRequest = new ApplicationUserRequest();
					applicationUserRequest.setAccountId((String) obj[0]);
					applicationUserRequest.setMobileNumber((String) obj[3]);
					applicationUserRequest.setFirstName((String) obj[1]);
					applicationUserRequest.setLastName((String) obj[2]);
					applicationUserRequest.setSecMobileNumber((String) obj[5]);
					applicationUserRequest.setEmailId((String) obj[4]);
					applicationUserRequest.setEmployID((String) obj[6]);
					applicationUserRequest.setUserType("CUSTOMER");
					userList.add(applicationUserRequest);
				}
			}
		}
		if (userList == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Costomers are not found ");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Costomers are found successfully");

		}
		return new ResponseEntity<>(userList, headers, HttpStatus.OK);
	}

	public ApplicationUserDto toDto(ApplicationUserEntity entity) {
		ApplicationUserDto appliationUser = modelMapper.map(entity, ApplicationUserDto.class);
		return appliationUser;
	}

	/*
	 * public void sendEmailConfirmationLinkToUser(Object object) throws
	 * ResourceNotFoundException { ResponseEntity<?> result = null; try { URI uri =
	 * URI.create(emailNotificationURL); RestTemplate restTemplate = new
	 * RestTemplate(); HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<Object> entity
	 * = new HttpEntity<Object>(object, headers); result =
	 * restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class); } catch
	 * (RestClientException e) { logger.error("Email Confirmation sending Failed",
	 * e); throw new ResourceNotFoundException(); }
	 * logger.info("Status Code==>> {}", result.getStatusCode()); }
	 */

	@Transactional
	public ResponseEntity<?> forgotPasswordOTP(String userName) {
		headers = Utilities.getDefaultHeader();
		MultiValueMap<String, String> headers = Utilities.getDefaultHeader();
		ApplicationUserEntity userEntity = applicationUserRepository.findByUserNameAndActiveTrue(userName);
		rand = new SecureRandom();
		if (null == userEntity) {
			throw new UnProcessibleException("Invalid User Name");
		} else {
			try {
				int otp = 0;
				int flag = 0;
				statusDto = new StatusDto();
				otp = rand.nextInt(1000) + 1000;
				// userMobileNumber = tripDetailRepository.userMobileNumberByTrip(tripId);
				notify.sendSms(userEntity.getMobileNumber(), "Hello, Please enter OTP:" + otp + " ");
				statusDto.setStatus(String.valueOf(otp));
				statusDto.setInfo(userEntity.getAccountId());

				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Temporary OTP sent to your Email Address and MobileNumber");

				return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("Unable to update password", e);
				throw new InternalServerException();
			}
		}
	}

	@Transactional
	public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
		headers = Utilities.getDefaultHeader();
		MultiValueMap<String, String> headers = Utilities.getDefaultHeader();
		Optional<ApplicationUserEntity> optionalUser;
		int update;
		try {
			optionalUser = applicationUserRepository.findById(request.getAccountId());
			if (!optionalUser.isPresent()) {
				throw new UnProcessibleException("Invalid User");
			} else {
				ApplicationUserEntity userEntity = optionalUser.get();

				if (!passwordEncoder().matches(request.getOldPassword(), userEntity.getPassword())) {
					throw new UnProcessibleException("Old Password is incorrect");
				}
				String encodedPassword = passwordEncoder().encode(request.getNewPassword());
				ZonedDateTime now = ZonedDateTime.now();
				update = applicationUserRepository.updatePassword(encodedPassword, request.getAccountId(), now);
			}

		} catch (Exception e) {
			logger.error("Unable to change password", e);
			throw new InternalServerException(e.getMessage());
		}

		try {
			if (update == 1) {
				StatusDto statusDto = new StatusDto();
				notify.sendSms(optionalUser.get().getMobileNumber(), "Hello ,your password updated successfully");
				MasterDto masterDto = selectDeviceToken(request.getAccountId());
				notify.notifyDriver("Hello ,your password updated successfully", "Change Password", masterDto.getId(),
						null);
				statusDto.setStatus("true");
			}
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(null, headers, HttpStatus.OK);

	}

	@Transactional
	public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) {
		headers = Utilities.getDefaultHeader();
		MultiValueMap<String, String> headers = Utilities.getDefaultHeader();
		Optional<ApplicationUserEntity> optionalUser;
		int update = 0;
		try {
			optionalUser = applicationUserRepository.findById(request.getAccountId());
			if (request.getAccountId() != null) {
				String encodedPassword = passwordEncoder().encode(request.getNewPassword());
				ZonedDateTime now = ZonedDateTime.now();
				update = applicationUserRepository.updatePassword(encodedPassword, request.getAccountId(), now);
			}
		} catch (Exception e) {
			logger.error("Unable to change password", e);
			throw new InternalServerException(e.getMessage());
		}

		try {
			if (update == 1) {
				StatusDto statusDto = new StatusDto();
				notify.sendSms(optionalUser.get().getMobileNumber(), "Hello ,your password updated successfully");
				MasterDto masterDto = selectDeviceToken(request.getAccountId());
				notify.notifyDriver("Hello ,your password updated successfully", "Change Password", masterDto.getId(),
						null);
				statusDto.setStatus("true");
			}
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(null, headers, HttpStatus.OK);

	}

	public MasterDto selectDeviceToken(String accountId) {
		String query = "select e.deviceToken,e.driverName from drivertripdetail e where e.accountId='" + accountId
				+ "'";
		Query qry = entityManager.createNativeQuery(query);
		Object[] obj = (Object[]) qry.getSingleResult();
		MasterDto masterDto = new MasterDto();
		masterDto.setId((String) obj[0]);
		masterDto.setName((String) obj[1]);
		return masterDto;

	}

	public ResponseEntity<?> readDoc(String accountId, String documentId) {
		headers = Utilities.getDefaultHeader();
		String[] keyValue;
		DocumentDetailDto documentDetailDto = null;
		DocumentDetailEntity documentDetailEntity = documentRepository.findByAccountIdAndDocumentTypeId(accountId,
				documentId);

		if (documentDetailEntity == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Document  not found ");
		} else {
			documentDetailDto = GenericMapper.mapper.map(documentDetailEntity, DocumentDetailDto.class);
			keyValue = documentDetailDto.getFieldDetails().split(",");
			Map<String, String> map = new HashedMap();
			for (int i = 0; i < keyValue.length; i++) {
				String[] valueOfKey = keyValue[i].split(":");
				map.put(valueOfKey[0], valueOfKey.length > 1 ? valueOfKey[1] : "");
			}
			documentDetailDto.setDocumentField(map);

			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Document found successfully");

		}
		return new ResponseEntity<>(documentDetailDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> readCarDetails(String accountId) {
		headers = Utilities.getDefaultHeader();

		CarDetailDto carDetailDto = null;
		CarDetailsEntity carDetailsEntity = carDetailRepository.findByAccountId(accountId);
		if (carDetailsEntity == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Document  not found ");
		} else {
			carDetailDto = CarDetailMapper.mapToDto(carDetailsEntity);
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car details found successfully");

		}
		return new ResponseEntity<>(carDetailDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> saveDoc(DocumentDetailDto documentDetailDto) {
		headers = Utilities.getDefaultHeader();

		documentRepository.save(DocumentMapper.mapFromDto(documentDetailDto));
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Document Updated successfully");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> saveCarDeatils(CarDetailDto carDetailDto) {
		headers = Utilities.getDefaultHeader();

		CarDetailsEntity carDetailsEntity = carDetailRepository.save(CarDetailMapper.mapFromDto(carDetailDto));
		carDetailDto = CarDetailMapper.mapToDto(carDetailsEntity);
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Car Detail updated successfully");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> updateProfile(String accountId, String firstName, String lastName, String emailId,
			String mobileNumber, boolean active) {
		headers = Utilities.getDefaultHeader();
		responceMap = new HashMap<String, Object>();
		// Check mobileNumber is userd or not
		try {
			if (!mobileNumber.equalsIgnoreCase("null")) {
				ApplicationUserEntity existingUser = applicationUserRepository
						.findByUserNameAndActiveTrue(mobileNumber.trim());
				if (null != existingUser) {
					headers.add(Constants.STATUS, HttpStatus.CONFLICT.toString());
					headers.add(Constants.MESSAGE, "Mobile number is already exist");
					responceMap.put(Constants.MESSAGE, "Mobile number is already exist");
					responceMap.put(Constants.STATUS, HttpStatus.CONFLICT.toString());
					return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);

				}
			}
		} catch (Exception exception) {
			logger.error("Error While updating profile");
		}
		ApplicationUserEntity applicationUserEntity = applicationUserRepository.findByAccountId(accountId);
		applicationUserEntity.setFirstName(firstName);
		applicationUserEntity.setLastName(lastName);
		applicationUserEntity.setEmailId(emailId);
		if (!mobileNumber.equalsIgnoreCase("null")) {
			applicationUserEntity.setMobileNumber(mobileNumber);
			applicationUserEntity.setUserName(mobileNumber);
		}
		try {
			DriverTripDetailEntity driverTripDetailEntity = driverTripDetailRepository.findByAccountId(accountId);
			if (!mobileNumber.equalsIgnoreCase("null")) {
				driverTripDetailEntity.setMobileNumber(mobileNumber);
			}	
			driverTripDetailEntity.setActive(active);
			driverTripDetailRepository.save(driverTripDetailEntity);
		} catch (Exception exception) {
			logger.error("Error while updating driver trip details");
		}
		applicationUserEntity.setActive(active);
		applicationUserRepository.save(applicationUserEntity);
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Profile  updated successfully");
		responceMap.put(Constants.MESSAGE, "Profile  updated successfully");
		responceMap.put(Constants.STATUS, HttpStatus.OK.toString());
		return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);

	}

	/*
	 * Uploading profile pic byte array and returning path
	 */
	@Transactional
	public ResponseEntity<?> uploadPrfilePic(FileDto fileDto) {
		headers = Utilities.getDefaultHeader();
		String fileName = System.getProperty("user.dir");
		String profilePicUrl = null;
		String filePath = fileName + File.separator + fileDto.getDescription();
		byte[] bytes = fileDto.getContent();
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(file + File.separator + fileDto.getFileName() + "." + fileDto.getContentType());
			os.write(bytes);
			os.close();
			profilePicUrl = filePath + File.separator + fileDto.getFileName() + "." + fileDto.getContentType();
		} catch (Exception e) {
			logger.error("FileUplo");
		}
		if (profilePicUrl != null) {
			int update = applicationUserRepository.updateProfile(profilePicUrl, fileDto.getAccountId());
			if (update == 1) {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Profile Pic Updated successfully");
				statusDto = new StatusDto();
				statusDto.setStatus("true");
				return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
			}
		}
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Profile Pic not Updated successfully");
		statusDto = new StatusDto();
		statusDto.setStatus("false");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUserEntity existingUser = applicationUserRepository.findByUserNameAndActiveTrue(username.trim());
		if (existingUser != null) {
			TokenDto tokenDto = new TokenDto(existingUser.getUserName(), existingUser.getPassword(), new ArrayList<>());
			tokenDto.setAccountId(existingUser.getAccountId());
			tokenDto.setUserType(existingUser.getUserType());
			return tokenDto;
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
