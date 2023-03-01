package com.rvcabs.microservices.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	

	public static Date getCurrentDate() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	public static Date getDateFromStringWithoutTime(String strDate, String format) throws ParseException {

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		date = formatter.parse(strDate);
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.setTime(date);
		calendarObj.set(Calendar.AM_PM, 2);
		calendarObj.set(Calendar.HOUR, 0);
		calendarObj.set(Calendar.MINUTE, 0);
		calendarObj.set(Calendar.SECOND, 0);
		calendarObj.set(Calendar.MILLISECOND, 0);
		calendarObj.add(Calendar.DATE, -1);
		return calendarObj.getTime();
		
	}
	
	public static Date getDateFromStringWithTime(String strDate, String format) throws ParseException {

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		date = formatter.parse(strDate);
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.setTime(date);
/*		calendarObj.set(Calendar.AM_PM, 2);
		calendarObj.set(Calendar.HOUR, 0);
		calendarObj.set(Calendar.MINUTE, 0);
		calendarObj.set(Calendar.SECOND, 0);
		calendarObj.set(Calendar.MILLISECOND, 0);
		calendarObj.add(Calendar.DATE, -1);*/
		return calendarObj.getTime();
		
	}
	
	public static String convertDateToString(Date dateValue, String format) {
		String dateValueStr="";
		DateFormat df = new SimpleDateFormat(format);
		dateValueStr = df.format(dateValue);
		return dateValueStr;
	}
	
	public static void main(String[] args) throws Exception {
		
/*		LocalDate todaydate = LocalDate.now();
		LocalDate startingMonthDate = todaydate.withDayOfMonth(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		System.out.println(todaydate.getMonthValue());
		LocalDate lastThreeMonths=todaydate.minusMonths(0);

        String formatDateTime = lastThreeMonths.format(formatter);
		System.out.println(formatDateTime);
		Double balance=10.0005;
		System.out.println(balance.toString());*/
/*		BigDecimal amount = new BigDecimal(102020.05);

		amount.setScale(2, BigDecimal.ROUND_UP);
		System.out.println(amount.toString());
		 System.out.println(String.format ("%.2f", amount.floatValue()));
		LocalDate today = LocalDate.now();
		LocalDate previousWeek = today.minusWeeks(1).with(DayOfWeek.MONDAY);
		LocalDate previousWeekEndDay = previousWeek.plusDays(7);
		System.out.println(previousWeek);
		System.out.println(today.withDayOfMonth(1).minusMonths(1).plusMonths(1));*/
		
		System.out.println(getDateFromStringWithTime("10/31/2019 11:59:59 PM","MM/dd/yyyy hh:mm:ss aa"));
		
	}
	
	public static String converStringDateFromOneFormatToOtherFormat(String dateValue, String fromPattern, String toPattern) {
		String outputDateStr="";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromPattern);
			Date date = simpleDateFormat.parse(dateValue);
			outputDateStr= convertDateToString(date, toPattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputDateStr;
	}
	
	public static LocalDate getPreviousDateByPassingDayOfMonth(int day) {
		
		LocalDate todaydate = LocalDate.now();
		LocalDate previousDate=todaydate.withDayOfMonth(day);
		return previousDate;
	}
	
	public static LocalDate getPreviousDateByPassingNumberOfMonthsaAndDay(int month,int day) {
		
		LocalDate todaydate = LocalDate.now();
		LocalDate dateOfDay=todaydate.withDayOfMonth(day);
		LocalDate previousDate=dateOfDay.minusMonths(month);
		return previousDate;
	}
	
	public static String getStringValueFromLocalDate(LocalDate localDate, String pattern) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = localDate.format(formatter);
		return formattedDate;
	}
	
	public static LocalDate getTodaysLocalDate() {
		
		LocalDate todaydate = LocalDate.now();
		return todaydate;
	}
	
	
	
	public static Date getYesterdayDate() {

		Calendar calendarObj = Calendar.getInstance();
		calendarObj.set(Calendar.AM_PM, 2);
		calendarObj.set(Calendar.HOUR, 0);
		calendarObj.set(Calendar.MINUTE, 0);
		calendarObj.set(Calendar.SECOND, 0);
		calendarObj.set(Calendar.MILLISECOND, 0);
		calendarObj.add(Calendar.DATE, -2);

		return calendarObj.getTime();
	}

}
