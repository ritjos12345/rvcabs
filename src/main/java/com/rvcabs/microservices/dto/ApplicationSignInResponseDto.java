package com.rvcabs.microservices.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSignInResponseDto {
	//List<ApplicationAuthGroupAndChildrenDto> tabs;
	TokenDto tokenDto;

	public TokenDto getTokenDto() {
		return tokenDto;
	}

	public void setTokenDto(TokenDto tokenDto) {
		this.tokenDto = tokenDto;
	}
}