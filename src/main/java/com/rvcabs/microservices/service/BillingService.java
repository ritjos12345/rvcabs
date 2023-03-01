package com.rvcabs.microservices.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.BillingDto;
import com.rvcabs.microservices.dto.FillterDto;
import com.rvcabs.microservices.dto.InvoiceDto;
import com.rvcabs.microservices.dto.MasterDataDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.entity.BillingEntity;
import com.rvcabs.microservices.entity.CompanyDetailsEntity;
import com.rvcabs.microservices.entity.InvoiceEntity;
import com.rvcabs.microservices.entity.SysCarCategoryEntity;
import com.rvcabs.microservices.entity.TripRatesEntity;
import com.rvcabs.microservices.repository.BillingRepository;
import com.rvcabs.microservices.repository.BookingTripRepository;
import com.rvcabs.microservices.repository.CarCategoriesRepository;
import com.rvcabs.microservices.repository.CompanyDetailsRepository;
import com.rvcabs.microservices.repository.InvoiceRepository;
import com.rvcabs.microservices.security.TripRatesRepository;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Transactional
@Service
public class BillingService {

	public static Map<Integer, Map<Integer, TripRatesEntity>> localTripRates = new HashMap<Integer, Map<Integer, TripRatesEntity>>();

	public static Map<Integer, TripRatesEntity> airportTripRates = new HashMap<Integer, TripRatesEntity>();

	public static Map<Integer, TripRatesEntity> outDoorTripRates = new HashMap<Integer, TripRatesEntity>();

	@Autowired
	private TripRatesRepository tripRatesRepository;

	@Autowired
	private BookingTripRepository bookingTripRepository;

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private CarCategoriesRepository carCategoriesRepository;

	@Autowired
	private CompanyDetailsRepository companyDetailsRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private Calendar cal = Calendar.getInstance();

	private long timeStampOfEndNA;

	private long timeStampOfStartNA;

	private MultiValueMap<String, String> headers = null;

	private List<String> companyIds;

	public String convertHiddenHrToTotalHr(Float hidenHr) {
		String totalHrString = String.valueOf((Math.round(hidenHr * 100.0) / 100.0));
		String[] totalHrArray = totalHrString.split(".");
		String hr = totalHrString.substring(0, totalHrString.indexOf('.'));
		Integer min = Math.round(Float.valueOf(totalHrString.substring(totalHrString.indexOf('.') + 1)) * 60 / 100);
		return hr + ":" + min;

	}

	public ResponseEntity<?> adjustInvoice(String invoiceId, int adjKm, int adjustHr) {
		BillingDto billingDto;
		TripRatesEntity ratesEntity = null;
		BillingEntity billingEntity = billingRepository.findById(invoiceId).get();
		billingDto = GenericMapper.mapper.map(billingEntity, BillingDto.class);
		billingDto.setAdjustHr(adjustHr);
		billingDto.setAdjustKm(adjKm);
		StatusDto statusDto = new StatusDto();

		try { // Setting rates as per trip type
			if (billingDto.getTypeOfDuty().equalsIgnoreCase("AIRPORT")) {
				// Adding adjustHr and recalculating final invoice
				billingDto
						.setHidenHr((float) (billingDto.getHidenHr() + Math.round((adjustHr / 60.0) * 100.0) / 100.0));
				billingDto.setTotalHr(convertHiddenHrToTotalHr(billingDto.getHidenHr()));
				if (billingDto.getHidenHr() > 4.0) {
					billingDto.setTotalHrAmt(
							((int) (Math.round(billingDto.getPerHrRate() * billingDto.getHidenHr() * 100) / 100)));
					billingDto.setTotalTripBill(billingDto.getTotalHrAmt()
							+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));

				} else {
					billingDto.setTotalTripBill(billingDto.getBasic()
							+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));
				}

			}
		} catch (Exception exception) {
		}
		try {
			if (billingDto.getTypeOfDuty().equalsIgnoreCase("OUTDOOR")) {
				billingDto.setTotalKm(billingDto.getTotalKm() + adjKm);
				billingDto.setTotalKmAmt((ratesEntity.getRates() * Float.valueOf(billingDto.getTotalKm())));
				billingDto.setTotalTripBill(billingDto.getTotalKmAmt()
						+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));
			}
		} catch (Exception exception) {
		}
		try {
			if (billingDto.getTypeOfDuty().equalsIgnoreCase("LOCAL")) {
				// Adding adjustHr and recalculating final invoice
				billingDto
						.setHidenHr((float) (billingDto.getHidenHr() + Math.round((adjustHr / 60.0) * 100.0) / 100.0));
				billingDto.setTotalHr(convertHiddenHrToTotalHr(billingDto.getHidenHr()));

				if (billingDto.getHidenHr() > 4.0) {
					billingDto.setTotalHrAmt(
							((int) (Math.round(billingDto.getPerHrRate() * billingDto.getHidenHr() * 100) / 100)));
				} else {
					billingDto.setTotalHrAmt(((int) (Math.round(billingDto.getPerHrRate() * 4.0 * 100) / 100)));
				}
				billingDto.setTotalTripBill(billingDto.getTotalHrAmt()
						+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));
			}

			/*
			 * int count = nightAllowanceCal((Date) object[10], (Date) object[11]);
			 * billingDto.setNaDa( Float.valueOf(billingDto.getNaDa()) + (count *
			 * ratesEntity.getNightallowance()));
			 */
			billingDto.setTotalTripBill(billingDto.getNaDa() + billingDto.getTotalTripBill());
			billingEntity = GenericMapper.mapper.map(billingDto, BillingEntity.class);
			billingEntity = billingRepository.saveAndFlush(billingEntity);
		} catch (Exception exception) {
		}

		headers = Utilities.getDefaultHeader();

		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Invoice adjusted ");

		statusDto = new StatusDto();
		statusDto.setStatus(HttpStatus.OK.toString());
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public void fetchingRateFromDB() {
		List<SysCarCategoryEntity> sysCarCat = carCategoriesRepository.findAllByActive(true);
		for (SysCarCategoryEntity categoryEntity : sysCarCat) {
			localTripRates.put(categoryEntity.getId(), new HashMap<Integer, TripRatesEntity>());
			airportTripRates.put(categoryEntity.getId(), new TripRatesEntity());
			outDoorTripRates.put(categoryEntity.getId(), new TripRatesEntity());
		}
		List<TripRatesEntity> tripRateList = new ArrayList<TripRatesEntity>();
		Iterable<TripRatesEntity> iterable = tripRatesRepository.findAll();
		iterable.iterator().forEachRemaining(tripRateList::add);
		for (TripRatesEntity tripRatesDto : tripRateList) {
			if (tripRatesDto.getTriptype() == 1) {
				airportTripRates.replace(tripRatesDto.getCartype(), tripRatesDto);
			}
			if (tripRatesDto.getTriptype() == 2) {
				outDoorTripRates.replace(tripRatesDto.getCartype(), tripRatesDto);
			}
			if (tripRatesDto.getTriptype() == 3) {
				int i = Integer.parseInt(tripRatesDto.getBasic().split(",")[0]);
				int j = Integer.parseInt(tripRatesDto.getBasic().split(",")[1]);
				Map<Integer, TripRatesEntity> outDoorTripRatesByHr = new HashMap<Integer, TripRatesEntity>();
				for (int k = i; k <= j; k++) {
					outDoorTripRatesByHr.put(k, tripRatesDto);
				}
				outDoorTripRatesByHr.putAll(localTripRates.get(tripRatesDto.getCartype()));
				localTripRates.put(tripRatesDto.getCartype(), outDoorTripRatesByHr);
			}
		}
	}

	public void createFinalBill() {
		List<BillingEntity> billingDtos = new ArrayList<BillingEntity>();
		TripRatesEntity ratesEntity = null;
		Calendar calendar = Calendar.getInstance();
		List<Object[]> obj = null;
		BillingDto billingDto = null;
		String query;
		Query qry;
		try {
			query = "SELECT     book.id,    book.travelId,    sysbookreq.name,"
					+ "    carcat.name as catname,    syscar.carmodel,    comdetail.companyname,"
					+ "    book.costCenter,     comdetail.gst,     appuser.employID,"
					+ "     concat(appuser.firstName,\" \",appuser.lastName),     tripdetail.tripStartTime,"
					+ "     tripdetail.tripEndTime, tripdetail.id as tripId,TIMESTAMPDIFF(MINUTE,tripdetail.tripStartTime,tripdetail.tripEndTime),book.carSegmentId,tripdetail.tripTotalDistance,DATEDIFF(tripdetail.tripEndTime,tripdetail.tripStartTime), cardet.regNumber    FROM    bookingdetail book,"
					+ "    sysbookingrequesttype sysbookreq,    syscarmaster syscar,"
					+ "    syscarcategory carcat,    companydetails comdetail,cardetail cardet,"
					+ "    applicationuser appuser,    tripdetail tripdetail    WHERE"
					+ "    book.tripStatus=5     AND book.requestTypeId = sysbookreq.id"
					+ "        AND book.accountId = appuser.accountId        AND book.companyId = comdetail.id"
					+ "        AND book.carId = syscar.id        AND book.carSegmentId = carcat.id"
					+ "        AND book.id = tripdetail.bookingId and cardet.accountId=book.driverAccountId  group by book.id";
			qry = entityManager.createNativeQuery(query);
			obj = (List<Object[]>) qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (obj != null) {
			if (!obj.isEmpty()) {
				for (Object[] object : obj) {
					try {
						billingDto = new BillingDto();
						BillingEntity billingEntity = new BillingEntity();
						billingDto.setBookingId((String) object[0]);
						billingDto.setTravelId((String) object[1]);
						billingDto.setTypeOfDuty((String) object[2]);
						billingDto.setVehicleSeg((String) object[3]);
						billingDto.setVehicleType((String) object[4]);
						billingDto.setNameOfCompany((String) object[5]);
						billingDto.setCostCentre((String) object[6]);
						billingDto.setGstNo((String) object[7]);
						billingDto.setEmpNo((String) object[8]);
						billingDto.setEmpName((String) object[9]);
						billingDto.setPickUpTime((Date) object[10]);
						billingDto.setFromDate((Date) object[10]);
						billingDto.setPickDropTime((Date) object[11]);
						billingDto.setToDate((Date) object[11]);
						billingDto.setHidenHr(((BigInteger) object[13]).floatValue() / 60);
						billingDto.setTotalKm(String.valueOf(object[15] == null ? 0 : object[15]));
						billingDto.setCarNo((String) object[17]);
						billingDto.setDutySlipDate(new Date());
						billingDto.setNaDa(0f);
						billingDto.setTotalTripBill((float) 0);
						int hr = (int) (billingDto.getHidenHr().floatValue());

						float minute = billingDto.getHidenHr() - hr;
						int min = (int) (minute * 60);
						billingDto.setTotalHr(hr + ":" + min);

						// Selecting starting reading end reading

						query = "SELECT  count FROM  tripfiles tripf,tripcoordinate tripcor WHERE tripcor.tripId =:tripId"
								+ " AND tripcor.id = tripf.tripCoordinateId and tripf.bookingType in ('STARTREADING','ENDREADING')";
						qry = entityManager.createNativeQuery(query).setParameter("tripId", object[12]);
						List<Object> obj1 = qry.getResultList();

						billingDto.setOpeningKM((obj1 != null && !obj1.isEmpty()) ? (String) obj1.get(0) : "0");
						billingDto.setClosingKM((obj1 != null && obj1.size() > 1) ? (String) obj1.get(0) : "0");
					} catch (Exception e) {

					}

					try {
						// Selecting toll and parking,
						query = "SELECT  sum(count) FROM  tripfiles tripf,tripcoordinate tripcor WHERE tripcor.tripId = :tripId"
								+ "AND tripcor.id = tripf.tripCoordinateId and tripf.bookingType in ('TOLL','PARKING')";
						qry = entityManager.createNativeQuery(query).setParameter("tripId", object[12]);
						Object obj2 = qry.getSingleResult();
						billingDto.setTolllPark((float) obj2);
					} catch (Exception e) {

					}

					try { // Setting rates as per trip type
						if (billingDto.getTypeOfDuty().equalsIgnoreCase("AIRPORT")) {

							if (billingDto.getHidenHr() > 4.0) {
								ratesEntity = localTripRates.get(object[14])
										.get((int) (Math.ceil(billingDto.getHidenHr())));
								billingDto.setPerHrRate(ratesEntity.getRates());
								billingDto.setTotalHrAmt(
										((int) (Math.round(billingDto.getPerHrRate() * billingDto.getHidenHr() * 100)
												/ 100)));
								billingDto.setTotalTripBill(billingDto.getTotalHrAmt()
										+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));

							} else {
								ratesEntity = airportTripRates.get(object[14]);
								billingDto.setBasic(airportTripRates.get(object[14]).getRates());
								billingDto.setTotalTripBill(billingDto.getBasic()
										+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));

							}

						}
					} catch (Exception exception) {
					}
					try {
						if (billingDto.getTypeOfDuty().equalsIgnoreCase("OUTDOOR")) {
							ratesEntity = outDoorTripRates.get(object[14]);
							billingDto.setPerKmRate(String.valueOf(ratesEntity.getRates()));
							billingDto.setTotalKm(String.valueOf(object[15]));
							billingDto.setTotalKmAmt((ratesEntity.getRates() * (Float) object[15]));
							billingDto.setNaDa(
									(((BigInteger) object[16]).intValue() + 1) * ratesEntity.getDayallowance());
							billingDto.setTotalTripBill(billingDto.getTotalKmAmt()
									+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));
						}
					} catch (Exception exception) {
					}
					try {
						if (billingDto.getTypeOfDuty().equalsIgnoreCase("LOCAL")) {
							ratesEntity = localTripRates.get(object[14])
									.get((int) (Math.ceil(billingDto.getHidenHr())));
							billingDto.setPerHrRate(ratesEntity.getRates());

							if (billingDto.getHidenHr() > 4.0) {
								billingDto.setTotalHrAmt(
										((int) (Math.round(billingDto.getPerHrRate() * billingDto.getHidenHr() * 100)
												/ 100)));
							} else {
								billingDto.setTotalHrAmt(
										((int) (Math.round(billingDto.getPerHrRate() * 4.0 * 100) / 100)));
							}
							billingDto.setTotalTripBill(billingDto.getTotalHrAmt()
									+ (billingDto.getTolllPark() == null ? 0 : billingDto.getTolllPark()));
						}

						int count = nightAllowanceCal((Date) object[10], (Date) object[11]);
						billingDto.setNaDa(
								Float.valueOf(billingDto.getNaDa()) + (count * ratesEntity.getNightallowance()));
						billingDto.setTotalTripBill(billingDto.getNaDa() + billingDto.getTotalTripBill());
						BillingEntity billingEntity = GenericMapper.mapper.map(billingDto, BillingEntity.class);
						billingRepository.saveAndFlush(billingEntity);
					} catch (Exception exception) {
					}
				}
			}
		}
		try {
			query = "update bookingdetail set tripStatus=8 where tripStatus=5 ";
			qry = entityManager.createNativeQuery(query);
			int status = qry.executeUpdate();
		} catch (Exception exception) {
		}

	}

	// Calculating count ,how many times trip has started or ended or crossed 11pm
	// to 5 am period.
	public int nightAllowanceCal(Date tripStart, Date tripEnd) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = 0;

		// Setting date with trip start date with time 5 am , creating 1st set of date
		String date = dateFormat.format(tripStart).split(" ")[0] + " " + "05:00:00";

		try {
			cal.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}

		// Setting time stamp of start ie 5 am trip start day
		timeStampOfStartNA = cal.getTimeInMillis();

		// calculating previous day 11 pm
		cal.add(Calendar.HOUR, -6);
		timeStampOfEndNA = cal.getTimeInMillis();

		// Checking trip start or end in 1st set ie trip start day 5 am and previous day
		// 11pm
		if (tripStart.getTime() < timeStampOfStartNA && tripStart.getTime() > timeStampOfEndNA) {
			count = 1 + count;
			increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);

		} else if (tripEnd.getTime() < timeStampOfStartNA && tripEnd.getTime() > timeStampOfEndNA) {
			count = 1 + count;
			increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
		} else {
			increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
		}

		while (timeStampOfEndNA < tripEnd.getTime()) {
			if (tripStart.getTime() < timeStampOfStartNA && tripStart.getTime() > timeStampOfEndNA) {
				count = 1 + count;
				increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
			} else if (tripEnd.getTime() < timeStampOfStartNA && tripEnd.getTime() > timeStampOfEndNA) {
				count = 1 + count;
				increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
			} else if (tripEnd.getTime() > timeStampOfStartNA && tripEnd.getTime() > timeStampOfEndNA) {
				count = 1 + count;
				increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
			} else {
				increaseCountAndDay(timeStampOfEndNA, timeStampOfStartNA);
			}

		}
		return count;
	}

	public ResponseEntity<?> generateInvoice(List<String> billingList) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mmm-yyyy");
		InvoiceDto invoiceDto = new InvoiceDto();
		Calendar calendar = Calendar.getInstance();
		headers = Utilities.getDefaultHeader();
		companyIds = new ArrayList<String>();
		float totalAmount = 0;
		List<BillingEntity> billingEntities = billingRepository.findBillingDetailsByIds(billingList);
		for (BillingEntity billingEntity : billingEntities) {
			// Checking CompanyIds are same or not, all company should be same
			if (companyIds.size() == 0) {
				companyIds.add(billingEntity.getNameOfCompany());
			}
			if (companyIds.contains(billingEntity.getNameOfCompany())) {
				totalAmount = totalAmount
						+ (billingEntity.getTotalTripBill() == null ? 0 : billingEntity.getTotalTripBill());
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
			} else {
				totalAmount = 0;
				headers.add(Constants.STATUS, HttpStatus.NOT_ACCEPTABLE.toString());
				headers.add(Constants.MESSAGE, "All data are not belong to same cmopany");
				return new ResponseEntity<>(null, headers, HttpStatus.NOT_ACCEPTABLE);
			}
			if (totalAmount != 0) {
				InvoiceEntity invoiceEntity = new InvoiceEntity();
				invoiceEntity = GenericMapper.mapper.map(new InvoiceDto(), InvoiceEntity.class);
				invoiceEntity.setBillWitoutGST(totalAmount);
				invoiceEntity.setDurationOfInvoce(null);
				CompanyDetailsEntity companyDetailsEntity = companyDetailsRepository
						.findByCompanyName(companyIds.get(0));
				invoiceEntity.setToCompanyName(companyDetailsEntity.getCompanyName());
				invoiceEntity.setToCompanyAddress(companyDetailsEntity.getAddress());
				invoiceEntity.setToGstNo(companyDetailsEntity.getGstnumber());
				invoiceEntity.setToCompanyState(companyDetailsEntity.getState());
				invoiceEntity.setToStateCode(companyDetailsEntity.getGstnumber().substring(0, 2));
				invoiceEntity.setBillingIds(billingList.toString());
				invoiceEntity.setInvoiceDate(dateFormat.format(new Date()));
				invoiceEntity
						.setInvoiceNo("RV/" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.YEAR) + 1));
				/*
				 * invoiceEntity.setT invoiceEntity.set invoiceEntity.set
				 */
				if (invoiceEntity.getToStateCode().equalsIgnoreCase(invoiceEntity.getFromStateCode())) {
					invoiceEntity.setcGST("2.50%");
					invoiceEntity.setcGSTAmt((float) (0.025 * totalAmount));
					invoiceEntity.setsGSTUTGS("2.50%");
					invoiceEntity.setsGSTUTGSAmt((float) (0.025 * totalAmount));
				} else {
					invoiceEntity.setiGST("5.0%");
					invoiceEntity.setiGSTAmt((float) (0.05 * totalAmount));
				}
				invoiceEntity.setBillWitGST(((float) (0.05 * totalAmount)) + totalAmount);
				invoiceEntity = invoiceRepository.save(invoiceEntity);
				invoiceDto = GenericMapper.mapper.map(invoiceEntity, InvoiceDto.class);
			}
		}
		return new ResponseEntity<>(invoiceDto, headers, HttpStatus.OK);
		// billingRepository.
	}

	// Increasing day by 1
	public void increaseCountAndDay(long timeStampEnd, long timeStampStart) {
		cal.setTimeInMillis(timeStampEnd);
		cal.add(Calendar.HOUR, 24);
		timeStampOfEndNA = cal.getTimeInMillis();

		cal.setTimeInMillis(timeStampStart);
		cal.add(Calendar.HOUR, 24);
		timeStampOfStartNA = cal.getTimeInMillis();
	}

	public ResponseEntity<?> report(FillterDto fillterDto) {
		List<BillingDto> billingList = new ArrayList<BillingDto>();
		BillingDto billingDto;
		headers = Utilities.getDefaultHeader();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String query = "SELECT * FROM travelbill where date(createdOn) between :startDate and :endDate";

		if (fillterDto.getDutyTypes() != null && !fillterDto.getDutyTypes().isEmpty()) {
			query = query + "  and typeOfDuty in :dutyTyps";
		}
		if (fillterDto.getCompanyNames() != null && !fillterDto.getCompanyNames().isEmpty()) {
			query = query + "  and nameOfCompany in :nameOfCompany";
		}
		if (fillterDto.getVechicleSeg() != null && !fillterDto.getVechicleSeg().isEmpty()) {
			query = query + "  and vehicleSeg in :vehicleSeg";
		}

		if (fillterDto.getVechicleType() != null && !fillterDto.getVechicleType().isEmpty()) {
			query = query + "  and vehicleType in :vehicleType";
		}

		if (fillterDto.getGstNum() != null && !fillterDto.getGstNum().isEmpty()) {
			query = query + "  and gstNo in :gstNo";
		}

		if (fillterDto.getEmployeeIds() != null && !fillterDto.getEmployeeIds().isEmpty()) {
			query = query + "  and empNo in :empNo";
		}

		if (fillterDto.getTravelIds() != null && !fillterDto.getTravelIds().isEmpty()) {
			query = query + "  and travelId in :travelId";
		}
		if (fillterDto.getCostCenter() != null && !fillterDto.getCostCenter().isEmpty()) {
			query = query + "  and costCentre in :costCentre";
		}
		Query qry = entityManager.createNativeQuery(query);

		if (fillterDto.getStartDate() == null) {
			qry.setParameter("startDate", dateFormat.format(new Date()));
			qry.setParameter("endDate", dateFormat.format(new Date()));
		} else {
			qry.setParameter("startDate", fillterDto.getStartDate());
			qry.setParameter("endDate", fillterDto.getEndDate());
		}
		if (fillterDto.getDutyTypes() != null && !fillterDto.getDutyTypes().isEmpty()) {
			qry.setParameter("dutyTyps", fillterDto.getDutyTypes());
		}
		if (fillterDto.getCompanyNames() != null && !fillterDto.getCompanyNames().isEmpty()) {
			qry.setParameter("nameOfCompany", fillterDto.getCompanyNames());
		}
		if (fillterDto.getVechicleSeg() != null && !fillterDto.getVechicleSeg().isEmpty()) {
			qry.setParameter("vehicleSeg", fillterDto.getVechicleSeg());
		}
		if (fillterDto.getVechicleType() != null && !fillterDto.getVechicleType().isEmpty()) {
			qry.setParameter("vehicleType", fillterDto.getVechicleType());
		}

		if (fillterDto.getGstNum() != null && !fillterDto.getGstNum().isEmpty()) {
			qry.setParameter("gstNo", fillterDto.getGstNum());
		}

		if (fillterDto.getEmployeeIds() != null && !fillterDto.getEmployeeIds().isEmpty()) {
			qry.setParameter("empNo", fillterDto.getEmployeeIds());
		}

		if (fillterDto.getTravelIds() != null && !fillterDto.getTravelIds().isEmpty()) {
			qry.setParameter("travelId", fillterDto.getTravelIds());
		}

		if (fillterDto.getCostCenter() != null && !fillterDto.getCostCenter().isEmpty()) {
			qry.setParameter("costCentre", fillterDto.getCostCenter());
		}
		List<Object[]> list = qry.getResultList();

		if (list == null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Partner Found For SearchTerm");
		} else {
			for (Object[] billingEntity : list) {
				billingDto = new BillingDto();
				// billingDto =
				// GenericMapper.mapper.map(BillingEntity.class.cast(billingEntity),
				// BillingDto.class);

				billingDto.setBasic(Float.valueOf(billingEntity[5] == null ? "0" : (String) billingEntity[5]));
				billingDto.setCarNo((String) billingEntity[6]);
				billingDto.setClosingKM((String) billingEntity[7]);
				billingDto.setCostCentre((String) billingEntity[8]);
				billingDto.setDutySlip(billingEntity[9] == null ? 0 : ((BigInteger) billingEntity[9]).longValue());
				billingDto.setDutySlipDate((Date) billingEntity[10]);
				billingDto.setEmpName((String) billingEntity[11]);
				billingDto.setEmpNo((String) billingEntity[12]);
				billingDto.setFromDate((Date) billingEntity[13]);
				billingDto.setGstNo((String) billingEntity[14]);
				billingDto.setHidenHr(Float.valueOf((String) billingEntity[15]));
				billingDto.setId((String) billingEntity[0]);
				billingDto.setNaDa((Float) (billingEntity[33] == null ? Float.valueOf("0") : billingEntity[33]));
				billingDto.setNameOfCompany((String) billingEntity[16]);
				billingDto.setOpeningKM(billingEntity[17] == null ? "0" : (String) billingEntity[17]);
				billingDto.setPerHrRate((Float) billingEntity[18]);
				billingDto.setPerKmRate(billingEntity[19] == null ? "0" : (String) billingEntity[19]);
				billingDto.setPickDropTime((Date) billingEntity[20]);
				billingDto.setPickUpTime((Date) billingEntity[21]);
				billingDto.setToDate((Date) billingEntity[22]);
				billingDto.setTolllPark(Float.valueOf(billingEntity[23] == null ? "0" : (String) billingEntity[23]));
				billingDto.setTotalHr(billingEntity[24] == null ? "0" : (String) billingEntity[24]);
				billingDto.setTotalHrAmt(Integer.valueOf(billingEntity[25] == null ? "0" : (String) billingEntity[25]));
				billingDto.setTotalKm(billingEntity[26] == null ? "0" : (String) billingEntity[26]);
				billingDto.setTotalKmAmt(Float.valueOf(billingEntity[27] == null ? "0" : (String) billingEntity[27]));
				billingDto
						.setTotalTripBill(Float.valueOf(billingEntity[28] == null ? "0" : (String) billingEntity[28]));
				billingDto.setTravelId((String) (billingEntity[29] == null ? 0 : billingEntity[29]));
				billingDto.setTypeOfDuty((String) billingEntity[30]);
				billingDto.setVehicleSeg((String) billingEntity[31]);
				billingDto.setVehicleType((String) billingEntity[32]);
				billingList.add(billingDto);

			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Partner Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(billingList, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> billList(int pageIndex, int pageSize, FillterDto fillterDto) {
		List<BillingDto> billingList = new ArrayList<BillingDto>();
		BillingDto billingDto;
		Pageable sortedBydutySlipDateDesc = PageRequest.of(pageIndex, pageSize, Sort.by("createdOn").descending());
		Page<BillingEntity> pageData = null;
		headers = Utilities.getDefaultHeader();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String query = "SELECT * FROM travelbill where date(createdOn) between :startDate and :endDate";

		if (fillterDto.getDutyTypes() != null && !fillterDto.getDutyTypes().isEmpty()) {
			query = query + "  and typeOfDuty in :dutyTyps";
		}
		if (fillterDto.getCompanyNames() != null && !fillterDto.getCompanyNames().isEmpty()) {
			query = query + "  and nameOfCompany in :nameOfCompany";
		}
		if (fillterDto.getVechicleSeg() != null && !fillterDto.getVechicleSeg().isEmpty()) {
			query = query + "  and vehicleSeg in :vehicleSeg";
		}

		if (fillterDto.getVechicleType() != null && !fillterDto.getVechicleType().isEmpty()) {
			query = query + "  and vehicleType in :vehicleType";
		}

		if (fillterDto.getGstNum() != null && !fillterDto.getGstNum().isEmpty()) {
			query = query + "  and gstNo in :gstNo";
		}

		if (fillterDto.getEmployeeIds() != null && !fillterDto.getEmployeeIds().isEmpty()) {
			query = query + "  and empNo in :empNo";
		}

		if (fillterDto.getTravelIds() != null && !fillterDto.getTravelIds().isEmpty()) {
			query = query + "  and travelId in :travelId";
		}
		if (fillterDto.getCostCenter() != null && !fillterDto.getCostCenter().isEmpty()) {
			query = query + "  and costCentre in :costCentre";
		}

		query = query + " order by createdOn Desc ";

		Query qry = entityManager.createNativeQuery(query).setFirstResult(pageIndex * pageSize).setMaxResults(pageSize);

		if (fillterDto.getStartDate() == null) {

			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
			qry.setParameter("startDate", dateFormat.format(calendar.getTime()));
			qry.setParameter("endDate", dateFormat.format(new Date()));
		} else {
			qry.setParameter("startDate", fillterDto.getStartDate());
			qry.setParameter("endDate", fillterDto.getEndDate());
		}
		if (fillterDto.getDutyTypes() != null && !fillterDto.getDutyTypes().isEmpty()) {
			qry.setParameter("dutyTyps", fillterDto.getDutyTypes());
		}
		if (fillterDto.getCompanyNames() != null && !fillterDto.getCompanyNames().isEmpty()) {
			qry.setParameter("nameOfCompany", fillterDto.getCompanyNames());
		}
		if (fillterDto.getVechicleSeg() != null && !fillterDto.getVechicleSeg().isEmpty()) {
			qry.setParameter("vehicleSeg", fillterDto.getVechicleSeg());
		}
		if (fillterDto.getVechicleType() != null && !fillterDto.getVechicleType().isEmpty()) {
			qry.setParameter("vehicleType", fillterDto.getVechicleType());
		}

		if (fillterDto.getGstNum() != null && !fillterDto.getGstNum().isEmpty()) {
			qry.setParameter("gstNo", fillterDto.getGstNum());
		}

		if (fillterDto.getEmployeeIds() != null && !fillterDto.getEmployeeIds().isEmpty()) {
			qry.setParameter("empNo", fillterDto.getEmployeeIds());
		}

		if (fillterDto.getTravelIds() != null && !fillterDto.getTravelIds().isEmpty()) {
			qry.setParameter("travelId", fillterDto.getTravelIds());
		}

		if (fillterDto.getCostCenter() != null && !fillterDto.getCostCenter().isEmpty()) {
			qry.setParameter("costCentre", fillterDto.getCostCenter());
		}
		List<Object[]> list = qry.getResultList();

		if (list == null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Partner Found For SearchTerm");
			return new ResponseEntity<>(billingList, headers, HttpStatus.NO_CONTENT);
		} else {
			for (Object[] billingEntity : list) {
				billingDto = new BillingDto();
				// billingDto =
				// GenericMapper.mapper.map(BillingEntity.class.cast(billingEntity),
				// BillingDto.class);
				try {
					billingDto.setBasic(Float.valueOf(billingEntity[5] == null ? "0" : (String) billingEntity[5]));
					billingDto.setCarNo(billingEntity[6] == null ? "0" : (String) billingEntity[6]);
					billingDto.setClosingKM((String) billingEntity[7]);
					billingDto.setCostCentre(billingEntity[8] == null ? "0" : (String) billingEntity[8]);
					billingDto.setDutySlip(billingEntity[9] == null ? 0 : ((BigInteger) billingEntity[9]).longValue());
					billingDto.setDutySlipDate((Date) billingEntity[10]);
					billingDto.setEmpName((String) billingEntity[11]);
					billingDto.setEmpNo((String) billingEntity[12]);
					billingDto.setFromDate((Date) billingEntity[13]);
					billingDto.setGstNo((String) billingEntity[14]);
					billingDto.setHidenHr(Float.valueOf((String) billingEntity[15]));
					billingDto.setId((String) billingEntity[0]);
					billingDto.setNaDa((float) (billingEntity[33] == null ? 0 : billingEntity[33]));
					billingDto.setNameOfCompany((String) billingEntity[16]);
					billingDto.setOpeningKM(billingEntity[17] == null ? "0" : (String) billingEntity[17]);
					billingDto.setPerHrRate((billingEntity[18] == null ? Float.valueOf(0) : (float) billingEntity[18]));
					billingDto.setPerKmRate(billingEntity[19] == null ? "0" : (String) billingEntity[19]);
					billingDto.setPickDropTime((Date) billingEntity[20]);
					billingDto.setPickUpTime((Date) billingEntity[21]);
					billingDto.setToDate((Date) billingEntity[22]);
					billingDto.setTolllPark(
							Float.valueOf((String) (billingEntity[23] == null ? "0" : billingEntity[23])));
					billingDto.setTotalHr(billingEntity[24] == null ? "0" : (String) billingEntity[24]);
					billingDto.setTotalHrAmt(
							Integer.valueOf(billingEntity[25] == null ? "0" : (String) billingEntity[25]));
					billingDto.setTotalKm(billingEntity[26] == null ? "0" : (String) billingEntity[26]);
					billingDto
							.setTotalKmAmt(Float.valueOf(billingEntity[27] == null ? "0" : (String) billingEntity[27]));
					billingDto.setTotalTripBill(
							Float.valueOf(billingEntity[28] == null ? "0" : (String) billingEntity[28]));
					billingDto.setTravelId((String) billingEntity[29]);
					billingDto.setTypeOfDuty((String) billingEntity[30]);
					billingDto.setVehicleSeg((String) billingEntity[31]);
					billingDto.setVehicleType((String) billingEntity[32]);

					billingList.add(billingDto);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Partner Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(billingList, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> billListByDutyType(int pageIndex, String dutyType) {
		List<BillingDto> billingList = new ArrayList<BillingDto>();
		BillingDto billingDto;
		Pageable sortedBydutySlipDateDesc = PageRequest.of(pageIndex, 10, Sort.by("createdOn").descending());
		Page<BillingEntity> pageData = billingRepository.findAllByTypeOfDuty(dutyType, sortedBydutySlipDateDesc);
		headers = Utilities.getDefaultHeader();

		if (pageData == null && pageData.getContent().isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Partner Found For SearchTerm");
		} else {
			for (BillingEntity billingEntity : pageData.getContent()) {
				billingDto = new BillingDto();
				billingDto = GenericMapper.mapper.map(billingEntity, BillingDto.class);
				billingList.add(billingDto);
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Partner Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(billingList, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> costCenterSearch(int pageIndex, int pageSize, String searchTerm) {
		Pageable page = PageRequest.of(pageIndex, pageSize);

		headers = Utilities.getDefaultHeader();
		List<String> costCenterList = billingRepository.findCostCenter(searchTerm, page);
		List<MasterDataDto> list = new ArrayList<MasterDataDto>();
		if (costCenterList != null && !costCenterList.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Brand Found Successfully For SearchTerm");

			for (String string : costCenterList) {
				MasterDataDto masterDataDto = new MasterDataDto();
				masterDataDto.setName(string);
				list.add(masterDataDto);
			}
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Brand  Found For SearchTerm");

		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> costTravelIdSearch(int pageIndex, int pageSize, String searchTerm) {
		Pageable page = PageRequest.of(pageIndex, pageSize);
		List<MasterDataDto> list = new ArrayList<MasterDataDto>();
		headers = Utilities.getDefaultHeader();
		List<String> travelIdList = billingRepository.findAllByTravelIdContaining(searchTerm, page);

		if (travelIdList != null && !travelIdList.isEmpty()) {

			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "TravelId Found Successfully For SearchTerm");

			for (String string : travelIdList) {
				MasterDataDto masterDataDto = new MasterDataDto();
				masterDataDto.setName(string);
				list.add(masterDataDto);
			}
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No TravelId Found For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> costEmployeIdSearch(int pageIndex, int pageSize, String searchTerm) {
		Pageable page = PageRequest.of(pageIndex, pageSize);

		headers = Utilities.getDefaultHeader();
		List<String> employeIdList = billingRepository.findAllByEmpNoContaining(searchTerm, page);
		List<MasterDataDto> list = new ArrayList<MasterDataDto>();

		if (employeIdList != null && !employeIdList.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "EmployId Found Successfully For SearchTerm");
			for (String string : employeIdList) {
				MasterDataDto masterDataDto = new MasterDataDto();
				masterDataDto.setName(string);
				list.add(masterDataDto);
			}
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, " EmployId not Found For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

}
