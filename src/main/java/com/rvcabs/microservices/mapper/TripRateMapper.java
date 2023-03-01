package com.rvcabs.microservices.mapper;

public class TripRateMapper {

	
	/*public static Set<TripRatesDto> mapListDtoToSet(List<TripRatesDto> appRunWrcDtoList) {
		Set<TripRatesDto> appRunWRCSet = null;
		if (null != appRunWrcDtoList && appRunWrcDtoList.size() > 0) {
			List<TripRatesDto> appRunWRCList = mapFromDto(appRunWrcDtoList);
			appRunWRCSet = new LinkedHashSet<TripRatesDto>(appRunWRCList);
		}
		return appRunWRCSet;
	}

	public static List<TripRatesDto> mapFromDto(List<TripRatesDto> appRunWrcDtoList) {
		List<TripRatesDto> appRunWRCList = null;
		if (null != appRunWrcDtoList && appRunWrcDtoList.size() > 0) {
			appRunWRCList = new ArrayList<TripRatesDto>(0);
			for (TripRatesDto appDataDto : appRunWrcDtoList) {
				appRunWRCList.add(mapFromDto(appDataDto));
			}
		}
		return appRunWRCList;
	}

	
	public static TripRatesDto mapFromDto(TripRatesDto appDataDto) {
		TripRatesDto appData = null;
		if (null != appDataDto) {
			appData = new TripRatesDto();
			appData.setId(appDataDto.getId());
			
			 * appData.setUserAccountMember(MemberMapper.mapFromDto(appDataDto
			 * .getUserAccountMember()));
			 
			appData.setAccountId(appDataDto.getAccountId());
			appData.setFieldDetails(appDataDto.getFieldDetails());
			appData.setFrontImagePath(appDataDto.getFrontImagePath());
			SysDocumentTypeEntity sysDocumentTypeEntity = new SysDocumentTypeEntity();
			sysDocumentTypeEntity.setDocumentId((appDataDto.getDocumentType().getId()).intValue());
			appData.setDocumentTypeEntity(sysDocumentTypeEntity);
			appData.setBackImagePath(appDataDto.getBackImagePath());

		}
		return appData;
	}
}*/
}
