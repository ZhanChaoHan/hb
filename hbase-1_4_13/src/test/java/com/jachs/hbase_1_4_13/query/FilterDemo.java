package com.jachs.hbase_1_4_13.query;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import com.jachs.hbase_1_4_13.InitDataDemo;

/***
 * 
 * @author zhanchaohan
 * @see InitDataDemo
 * ------------------------------
   1.RowFilter</br>
   说明：筛选出匹配的所有的行，支持基于行键过滤数据，可以执行精确匹配，子字符串匹配或正则表达式匹配，过滤掉不匹配的数据。</br>
   用法：使用BinaryComparator可以筛选出具有某个行键的行，或者通过改变比较运算符来筛选出符合某一条件的多条数据</br>
   2、QualifierFilter</br>
   说明：该Filter是一种类似RowFilter的比较过滤器，不同之处是它用来匹配列限定符而不是行健</br>
   3、PrefixFilter</br>
   说明：这是RowFilter的一种特例，它基于行健的前缀值进行过滤，它相当于给扫描构造函数Scan(byte[] startRow, byte[] stopRow)，提供了一个停止键，只是你不需要自己计算停止键。</br>
   4、KeyOnlyFilter</br>
   说明：这个Filter只会返回每行的行键+列簇+列，而不返回值，对不需要值的应用场景来说，非常实用，减少了值的传递。</br>
   5、TimestampsFilter</br>
   说明：该过滤器允许针对返回给客户端的时间版本进行更细粒度的控制，使用的时候，可以提供一个返回的时间戳的列表，只有与时间戳匹配的单元才可以返回。当做多行扫描或者是单行检索时，如果需要一个时间区间，可以在Get或Scan对象上使用setTimeRange()方法来实现这一点。</br>
   6、FirstKeyOnlyFilter</br>
   说明：该Filter的作用，是找每一行的第一列数据，找到之后，就会停止扫描。</br>
   7、ColumnPrefixFilter</br>
   说明：该Filter是按照列名的前缀来扫描单元格的，只会返回符合条件的列数据</br>
   8、ValueFilter</br>
   说明：该Filter主要是对值进行过滤，用法和RowFilter类似，只不过侧重点不同而已，针对的是单元值，使用这个过滤器可以过滤掉不符合设定标准的所有单元</br>
   9、ColumnCountGetFilter</br>
   说明：该Filter用来返回每行最多返回多少列，但返回的总数不超过设置的列数</br>
   10、SingleColumnValueFilter</br>
   说明：根据列的值来决定这一行数据是否返回，落脚点在行，而不是列。我们可以设置filter.setFilterIfMissing(true);如果为true，当这一列不存在时，不会返回，如果为false，当这一列不存在时，会返回所有的列信息。</br>
   11、SingleColumnValueExcludeFilter</br>
   说明：该Filter和SingleColumnValueFilter作用类似，唯一的区别在于，返回的数据不包含扫描条件的列。</br>
   12、FilterList</br>
   说明：将多个过滤器组合到一起</br>
 */
public class FilterDemo {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	static final String tableName="scanTable";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	
	private void printScan(ResultScanner resultScanner) {
		resultScanner.forEach(result->{
			String rowkey = Bytes.toString(result.getRow());
            System.out.println("row key :"+rowkey);
            Cell[] cells  = result.rawCells();
            for(Cell cell : cells) {
            	String rowKey=Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
    			String qualifier=Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
    			String tags=Bytes.toString(cell.getTagsArray(), cell.getTagsOffset(), cell.getTagsLength());
    			String value=Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            	
    			System.out.println(rowKey+"\t"+qualifier+"\t"+tags+"\t"+value+"\t");
            }
            System.out.println("--------------------------");
		});
	}
	
	@Test
	public void tect() throws Exception {
		Scan scan=new Scan();
		
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("dhuah"))); //row Key
		
		scan.setFilter(filter);
		
		HTable table = new HTable(conf, tableName);
		
		ResultScanner  resultScanner=table.getScanner(scan);
		
		printScan(resultScanner);
	}
}
