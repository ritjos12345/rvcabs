package com.rvcabs.microservices.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1583706197236907170L;
	
	@NotNull(message = "id is mandatory")
	@ApiModelProperty(required = true, allowEmptyValue = false, example ="1")
	private String id;

	private String name;

	private boolean isSysMaster;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSysMaster() {
		return isSysMaster;
	}

	public void setSysMaster(boolean isSysMaster) {
		this.isSysMaster = isSysMaster;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
