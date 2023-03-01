package com.rvcabs.microservices.mapper;

import com.rvcabs.microservices.dto.CarDetailDto;
import com.rvcabs.microservices.dto.MasterDataDto;
import com.rvcabs.microservices.entity.CarDetailsEntity;
import com.rvcabs.microservices.entity.SysCarCategoryEntity;
import com.rvcabs.microservices.entity.SysCarColorEntity;
import com.rvcabs.microservices.entity.SysCarIntColorEntity;
import com.rvcabs.microservices.entity.SysCarMasterEntity;
import com.rvcabs.microservices.entity.SysCarSubTypeEntity;

public class CarDetailMapper {
	public static CarDetailDto mapToDto(CarDetailsEntity carDetailsEntity) {
		// List<CarDetailDto> carDetailDtos = new ArrayList<CarDetailDto>();
		// if (null != masterDataDtos && masterDataDtos.size() > 0) {
		// for (CarDetail driverContactNum : masterDataDtos) {
		CarDetailDto carDetailDto = new CarDetailDto();
		carDetailDto.setId(carDetailsEntity.getId());
		carDetailDto.setCarImage(carDetailsEntity.getCarImage());

		MasterDataDto carcolormasterDataDto = new MasterDataDto();
		carcolormasterDataDto.setId((carDetailsEntity.getSysCarColor().getId()).longValue());
		carcolormasterDataDto.setName(carDetailsEntity.getSysCarColor().getName());
		carDetailDto.setCarColor(carcolormasterDataDto);

		MasterDataDto carintcolormasterDataDto = new MasterDataDto();
		carintcolormasterDataDto.setId(carDetailsEntity.getSysCarIntColor().getId().longValue());
		carintcolormasterDataDto.setName(carDetailsEntity.getSysCarIntColor().getName());
		carDetailDto.setCarInterrior(carintcolormasterDataDto);

		MasterDataDto masterDataDto = new MasterDataDto();
		masterDataDto.setId(carDetailsEntity.getSysCarMaster().getId().longValue());
		masterDataDto.setName(carDetailsEntity.getSysCarMaster().getName());
		carDetailDto.setType(masterDataDto);

		MasterDataDto carsubtypemasterDataDto = new MasterDataDto();
		carsubtypemasterDataDto.setId(carDetailsEntity.getSysCarSubType().getId().longValue());
		carsubtypemasterDataDto.setName(carDetailsEntity.getSysCarSubType().getName());
		carDetailDto.setSubType(carsubtypemasterDataDto);
		
		MasterDataDto carCategoryMasterDataDto = new MasterDataDto();
		carCategoryMasterDataDto.setId(carDetailsEntity.getSysCarCategoryEntity().getId().longValue());
		carCategoryMasterDataDto.setName(carDetailsEntity.getSysCarCategoryEntity().getName());
		carDetailDto.setSubType(carCategoryMasterDataDto);


		// driverContactNumList.add(dataDto);
		// }
		// }
		return carDetailDto;
	}

	public static CarDetailsEntity mapFromDto(CarDetailDto carDetailDto) {
		// List<CarDetailDto> carDetailDtos = new ArrayList<CarDetailDto>();
		// if (null != masterDataDtos && masterDataDtos.size() > 0) {
		// for (CarDetail driverContactNum : masterDataDtos) {
		CarDetailsEntity carDetailEntity = new CarDetailsEntity();
		carDetailEntity.setId(carDetailDto.getId());
		carDetailEntity.setCarImage(carDetailDto.getCarImage());
		carDetailEntity.setAccountId(carDetailDto.getAccountId());

		SysCarColorEntity sysCarColorEntity = new SysCarColorEntity();
		sysCarColorEntity.setId((carDetailDto.getCarColor().getId()).intValue());
		carDetailEntity.setSysCarColor(sysCarColorEntity);

		SysCarIntColorEntity  sysCarIntColorEntity = new SysCarIntColorEntity();
		sysCarIntColorEntity.setId((carDetailDto.getCarInterrior().getId()).intValue());
		carDetailEntity.setSysCarIntColor(sysCarIntColorEntity);
		
		SysCarMasterEntity  sysCarMasterEntity = new SysCarMasterEntity();
		sysCarMasterEntity.setId((carDetailDto.getType().getId()).intValue());
		carDetailEntity.setSysCarMaster(sysCarMasterEntity);

		SysCarSubTypeEntity  sysCarSubTypeEntity = new SysCarSubTypeEntity();
		sysCarSubTypeEntity.setId((carDetailDto.getSubType().getId()).intValue());
		carDetailEntity.setSysCarSubType(sysCarSubTypeEntity);
		
		SysCarCategoryEntity sysCarCategoryEntity = new SysCarCategoryEntity();
		sysCarCategoryEntity.setId((carDetailDto.getCarCategory().getId()).intValue());
		carDetailEntity.setSysCarCategoryEntity(sysCarCategoryEntity);


		// driverContactNumList.add(dataDto);
		// }
		// }
		return carDetailEntity;
	}

}
