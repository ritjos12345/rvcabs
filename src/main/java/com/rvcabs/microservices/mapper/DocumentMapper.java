package com.rvcabs.microservices.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rvcabs.microservices.dto.DocumentDetailDto;
import com.rvcabs.microservices.entity.DocumentDetailEntity;
import com.rvcabs.microservices.entity.SysDocumentTypeEntity;

public class DocumentMapper {

	public static Set<DocumentDetailEntity> mapListDtoToSet(List<DocumentDetailDto> appRunWrcDtoList) {
		Set<DocumentDetailEntity> appRunWRCSet = null;
		if (null != appRunWrcDtoList && appRunWrcDtoList.size() > 0) {
			List<DocumentDetailEntity> appRunWRCList = mapFromDto(appRunWrcDtoList);
			appRunWRCSet = new LinkedHashSet<DocumentDetailEntity>(appRunWRCList);
		}
		return appRunWRCSet;
	}

	public static List<DocumentDetailEntity> mapFromDto(List<DocumentDetailDto> appRunWrcDtoList) {
		List<DocumentDetailEntity> appRunWRCList = null;
		if (null != appRunWrcDtoList && appRunWrcDtoList.size() > 0) {
			appRunWRCList = new ArrayList<DocumentDetailEntity>(0);
			for (DocumentDetailDto appDataDto : appRunWrcDtoList) {
				appRunWRCList.add(mapFromDto(appDataDto));
			}
		}
		return appRunWRCList;
	}

	public static DocumentDetailEntity mapFromDto(DocumentDetailDto appDataDto) {
		DocumentDetailEntity appData = null;
		if (null != appDataDto) {
			appData = new DocumentDetailEntity();
			appData.setId(appDataDto.getId());

			if (appDataDto.getDocumentField() != null)
				appData.setFieldDetails(convertMapToString(appDataDto.getDocumentField()));
			/*
			 * appData.setUserAccountMember(MemberMapper.mapFromDto(appDataDto
			 * .getUserAccountMember()));
			 */
			appData.setAccountId(appDataDto.getAccountId());
			//appData.setFieldDetails(appDataDto.getFieldDetails());
			appData.setFrontImagePath(appDataDto.getDocumentField().get("Front Image"));
			SysDocumentTypeEntity sysDocumentTypeEntity = new SysDocumentTypeEntity();
			sysDocumentTypeEntity.setDocumentId((appDataDto.getDocumentType().getId()).intValue());
			appData.setDocumentTypeEntity(sysDocumentTypeEntity);
			appData.setBackImagePath(appDataDto.getDocumentField().get("Back Image"));

		}
		return appData;
	}

	public static String convertMapToString(Map<String, String> documentField) {
		return documentField.toString().replace("=", ":").replace("{", "").replace("}", "");

	}

}
