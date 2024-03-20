package com.globits.da.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tbl_project")
@XmlRootElement
public class Project extends BaseObject{
	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;

}
