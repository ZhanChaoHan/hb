package com.jachs.mapper;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.jachs.entity.People;
import com.jachs.entity.Student;
import com.navercorp.pinpoint.common.hbase.RowMapper;

public class PeopleMapper implements RowMapper<People>{

	@Override
	public People mapRow(Result result, int rowNum) throws Exception {
		People peo=new People();
		for (Cell cell : result.rawCells()) {
			
//			byte[] aList=cell.getQualifierArray();
//			byte[] rList=cell.getRowArray();
//			byte[] tList=cell.getTagsArray();
//			byte[] vList=cell.getValueArray();
//			
//			
//			System.out.println(new String(aList));
//			System.out.println(new String(rList));
//			System.out.println(new String(tList));
//			System.out.println(new String(vList));
//			
//			System.out.println("----------------");
			
			String rowKey=Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
			String qualifier=Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
			String tags=Bytes.toString(cell.getTagsArray(), cell.getTagsOffset(), cell.getTagsLength());
			String value=Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
			
			System.out.println(rowKey);
			System.out.println(qualifier);
			System.out.println(tags);
			System.out.println(value);
			System.out.println("-----------------------------------");
			
			
			if(qualifier.equals("info:name")) {
				peo.setName(value);
			}
			if(qualifier.equals("info:age")) {
				peo.setAge(value);
			}
		}
		
		return peo;
	}

}
