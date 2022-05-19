package com.jachs.mapper;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;

import com.jachs.entity.Student;
import com.navercorp.pinpoint.common.hbase.RowMapper;

public class StudentMapper implements RowMapper<Student>{

	@Override
	public Student mapRow(Result result, int rowNum) throws Exception {
		for (Cell cell : result.rawCells()) {
			System.out.println(new String(cell.getFamilyArray()));
		}
		
		return null;
	}

}
