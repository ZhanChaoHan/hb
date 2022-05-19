package com.jachs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhanchaohan
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
	private String name;
	private String age;
	
	
	@Override
	public String toString() {
		return "People [name=" + name + ", age=" + age + "]";
	}
	
}
