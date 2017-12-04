package com.ywy.util;

import com.ywy.exception.YWYException;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReaderUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ExcelReaderUtil.class);

	private static POIFSFileSystem fs;
	private static HSSFWorkbook wb;
	private static HSSFSheet sheet;
	private static HSSFRow row;
	private static POIFSFileSystem xfs;
	private static XSSFWorkbook xwb;
	private static XSSFSheet xsheet;
	private static XSSFRow xrow;
	

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param is
	 * @return String 表头内容的数组
	 */
	public static String[] readExcelTitle(InputStream is) {
		try {
			xwb = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		xsheet = xwb.getSheetAt(0);
		xrow = xsheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param is
	 * @param params
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public static List<JSONObject> readExcelContent(InputStream is, String params, int size) {

		if(StringUtil.isEmpty(params)) {
			return null;
		}
		String[] paramInfo = params.split(",");
		
		List<JSONObject> list = new ArrayList<>();
		try {
			 xwb = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = xwb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		xrow = sheet.getRow(0);
		int colNum = xrow.getPhysicalNumberOfCells();
		if(colNum > size) {
			LOG.error("导入列数超限");
		}
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			xrow = sheet.getRow(i);
			int j = 0;
			JSONObject json = new JSONObject();
			while (j < colNum) {
				String value = getCellFormatValue(xrow.getCell((short) j)).trim();
				json.put(paramInfo[j], value);
				j++;
			}
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(json);
		}
		return list;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					DecimalFormat df = new DecimalFormat("###.#########");  
					  
					cellvalue = df.format(cell.getNumericCellValue()); 
					
//					cellvalue = cell.getNumericCellValue() + "   ";
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getStringCellValue();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue.trim();

	}
	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case XSSFCell.CELL_TYPE_NUMERIC:
			case XSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					DecimalFormat df = new DecimalFormat("0");  
					  
					cellvalue = df.format(cell.getNumericCellValue()); 
					
//					cellvalue = cell.getNumericCellValue() + "   ";
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case XSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getStringCellValue();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue.trim();

	}

	public static final String [][] params = {{"0","6"},{"1", "4"},{"2", "9"},{"3", "5"},{"4", "7"},{"5", "0"},{"6", "2"},{"7", "8"},{"8", "3"},{"9", "1"},{"X", "X"}};

	/**
	 * 参数转换
	 * @param param
	 * @return
	 */
	public static String getResult(String param) {
		if(param == null) {
			return "";
		}
		StringBuffer nos = new StringBuffer();
		for (int i = 0; i < param.length(); i++) {
			for (int j = 0; j < params.length; j++) {
				if(param.substring(i, i+1).equals(params[j][1])) {
					nos.append(params[j][0]);
				}
			}
		}
		return nos.toString();
	}

	public static List<String> readeExcel() {
		return null;
	}

}
