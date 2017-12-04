package com.ywy.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author panhf
 *
 */
@Component
public class ExcelExportUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);
	public static final int MAX_EXPORT_SIZE = 10000; // 一次最多导出的数据量
	public static final String NO_DATA = "noData"; // 没有数据可以导出
	public static final String TOO_MANY_DATA = "tooManyData"; // 数据太多
	public static final String ERROR = "error"; // 导出出错
	public static final String SUCCESS = "success"; // 导出成功
	private static final String FILE_EX = "FileN";// 导出文件名配置项目名后缀
	private static final String EXPORT_MODE_POI = "POI";

	@Value("${export.templatepath}")
	private String templatePath;

	/**
	 * 导出报表
	 * @param exportTableModel 导出表格模板
	 * @param printClass 导出项目类型
	 * @param datals 数据list
	 * @return
	 */
	public String doExportData(String exportTableModel,Class<? extends Object> printClass,List datals,
									  Object header, String exportParams) {
		//检查数据量
		String resultRes = checkDataCount(datals, 0);
		if (!"".equals(resultRes)) {
			return resultRes;
		}

		ServletOutputStream outStream = null;
		DataInputStream stream = null;
		long startTime = Calendar.getInstance().getTimeInMillis();
		try {
			// 生成报表文件
			String fileNames = createFilePoi(exportTableModel, printClass, datals, header, exportParams);
			return fileNames;
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//			response.setHeader("Content-Disposition", "attachment;filename=" +
//					new String(fileNames[1].getBytes("GB2312"), "ISO-8859-1"));
//			outStream = response.getOutputStream();
//			stream = new DataInputStream(new FileInputStream(fileNames[0]));
//			byte[] buffer = new byte[stream.available()];
//			stream.read(buffer);

//			outStream.write(buffer);
//			outStream.flush();
		} catch (Exception e) {
			logger.error("导出报表出错：" + e);
			e.printStackTrace();
			return ERROR;
		} finally {
			long endTime = Calendar.getInstance().getTimeInMillis();
			logger.info("报表导出耗时：" + (endTime - startTime)/1000 + "秒");
			try {
				if (outStream != null) {
					outStream.close();
					outStream = null;
				}
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("关闭流出错：" + e);
				e.printStackTrace();
				return ERROR;
			}
		}
	}
	/**
	 * 检查数据量
	 * @param datals
	 * @return
	 */
	public static String checkDataCount(List datals, long total) {
		long size = 0;
		if (total != 0) {
			size = total;
		} else if (datals != null) {
			size = datals.size();
		}
		if (size == 0) {
			// 数据不存在
			return NO_DATA;
		} else if (size > MAX_EXPORT_SIZE) {
			// 数据太多
			return TOO_MANY_DATA;
		} else {
		}
		return "";
	}	
	public static HSSFWorkbook createWorkBook(String []fileConfig,  String modelFactName)throws Exception{
		return  new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(fileConfig[0] + modelFactName)));
	}
	

	/**
	 * 
	  * @Title: createFilePoi 
	  * @Description:  
	  * @param @param workbooknew
	  * @param @param printItem
	  * @param @param printClass
	  * @param @param datals
	  * @param @return
	  * @param @throws Exception    设定文件 
	  * @return String[]    返回类型 
	  * @throws
	 */
	public static void createFilePoi(HSSFWorkbook workbooknew, int sheetIndex, String printItem, List datals, int rowStart, short columnStart) throws Exception {

		String[] exportItems = getPrintConfig(printItem);
		// 文件全名包括路径
		try {
			// 创建导出报表文件
			HSSFSheet ws = workbooknew.getSheetAt(sheetIndex);
			//int rowStart = ws.getLastRowNum();
			HSSFFont font = workbooknew.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)10);
			HSSFDataFormat format = workbooknew.createDataFormat();
			// formatRate:0.0000%
			HSSFCellStyle csDoubleRate = workbooknew.createCellStyle();
			csDoubleRate.setFont(font);
			csDoubleRate.setDataFormat(format.getFormat("0.0000%"));
			
			// formatPrice:###,##0.0000
			HSSFCellStyle csDoublePrice = workbooknew.createCellStyle();
			csDoublePrice.setFont(font);
			csDoublePrice.setDataFormat(format.getFormat("#,##0.0000"));
			
			// formatAmt:###,##0.0000
			HSSFCellStyle csDoubleAmt = workbooknew.createCellStyle();
			csDoubleAmt.setFont(font);
			csDoubleAmt.setDataFormat(format.getFormat("#,##0.0000"));

			// formatPrice:###,##0.000000000
			HSSFCellStyle csSplit = workbooknew.createCellStyle();
			csSplit.setFont(font);
			csSplit.setDataFormat(format.getFormat("#,##0.000000000"));

			// default:#,##0.00
			HSSFCellStyle csDoubleDefault = workbooknew.createCellStyle();
			csDoubleDefault.setFont(font);
			csDoubleDefault.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			
			HSSFCellStyle csDefault = workbooknew.createCellStyle();
			csDefault.setFont(font);

			HSSFCell cell = null;
			for (int i = 0; i < datals.size(); i++) {
				HSSFRow row = ws.createRow(i+rowStart);
				int j = 0;
				if ("rowNum".equals(exportItems[0])) {
					// 序号
					cell = row.createCell((short)j);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(i+1);
					j = 1;
				}
				for (; j < exportItems.length; j++) {
					cell = row.createCell((short)(columnStart + j));
					String cellString = "";
					Object result = null;
					if (exportItems[j].matches("formatDate\\((.+)\\)")) {
						//需要formatDate 输出格式yyyy-MM-dd
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						cellString = DateUtil.getDateStr(
								(Date)getProValue(exportItem, datals.get(i)), "yyyy-MM-dd");
						cell.setCellValue(cellString);
					} else if (exportItems[j].matches("formatHour\\((.+)\\)")) {
						//需要formatTime 输出格式yyyy-MM-dd HH:mm:ss
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						cellString = DateUtil.getDateStr(
								(Date)getProValue(exportItem, datals.get(i)), "yyyy-MM-dd HH:mm");
						cell.setCellValue(cellString);
					} else if (exportItems[j].matches("formatTime\\((.+)\\)")) {
						//需要formatTime 输出格式yyyy-MM-dd HH:mm:ss
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						cellString = DateUtil.getDateStr(
								(Date)getProValue(exportItem, datals.get(i)), "yyyy-MM-dd HH:mm:ss");
						cell.setCellValue(cellString);
					} else if(exportItems[j].matches("formatAmt\\((.+)\\)")){
						// 需要formatRate 输出格式0.0000%
						String exportItem = exportItems[j].substring(10, exportItems[j].length()-1);
						//result = Maths.div((Double)getResult(exportItem, datals.get(i).getClass(), datals.get(i), 4), 100);
						result = getProValue(exportItem, datals.get(i));
						setCell(cell, HSSFCell.CELL_TYPE_NUMERIC, csDoubleAmt,   new Double(result.toString()));
					}
					else if (exportItems[j].matches("formatRate\\((.+)\\)")) {
						// 需要formatRate 输出格式0.0000%
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						//result = Maths.div((Double)getResult(exportItem, datals.get(i).getClass(), datals.get(i), 4), 100);
						result = getProValue(exportItem, datals.get(i));
						setCell(cell, HSSFCell.CELL_TYPE_NUMERIC, csDoubleRate, (Double)result);
					} else if (exportItems[j].matches("formatPrice\\((.+)\\)")) {
						// 需要formatPrice 输出格式0.0000
						String exportItem = exportItems[j].substring(12, exportItems[j].length()-1);
						//result = getResult(exportItem, datals.get(i).getClass(), datals.get(i), 4);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						
					} else if (exportItems[j].matches("formatSplit\\((.+)\\)")) {
						// 需要formatSplit 输出格式0.00000000
						String exportItem = exportItems[j].substring(12, exportItems[j].length()-1);
						//result = getResult(exportItem, datals.get(i).getClass(), datals.get(i), 8);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					}
					else {
						// 不需要format
						result = getResult(exportItems[j], datals.get(i).getClass(), datals.get(i), 2);
						cell.setCellStyle(csDefault);
						cell.setCellValue(String.valueOf(result));
					}
				}
			}
		
		} catch (Exception e) {
			logger.error("生成报表失败：" + e.getMessage(), e);
			throw e;
		}
		logger.info("生成报表成功。");
	}
	

	private static void setCell(HSSFCell cell, int valutType, HSSFCellStyle style, Double value){
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
	private static void setCell(HSSFCell cell, int valutType, HSSFCellStyle style, String value){
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
	private static void setCell(HSSFCell cell, int valutType, HSSFCellStyle style, boolean value){
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
	
	
	 public static Object getProValue(String exportItem, Object obj){
		 if(obj instanceof HashMap){
			 	return ((HashMap) obj).get(exportItem);
		 }else{
			 String firstLetter = exportItem.substring(0, 1).toUpperCase();
				String getMethodName = "get" + firstLetter + exportItem.substring(1);
				Method getMethod = null;
				try {
					getMethod = obj.getClass().getMethod(getMethodName, new Class[] {});
					return getMethod.invoke(obj, new Object[]{});
				} catch (Exception e) {
					e.printStackTrace();
				} 
		 }
		return null;
	}
	
	/**
	 * 生成报表文件
	 * @param exportTableModel 导出表格模板
	 * @param printClass 导出项目类型
	 * @return 文件全名包括路径 文件名
	 * @author panhf
	 * @since  2009-11-16 上午11:52:43
	 */
	private String createFilePoi(String exportTableModel,Class<? extends Object> printClass,List datals,
										  Object header, String exportParam) throws Exception {

		String fileConfig = getFileConfig(exportTableModel);
		String[] exportItems = getPrintConfig(exportParam);
		// 文件全名包括路径
		String datePath = DateUtil.getDateStr(new Date(), "yyyyMMdd");
		String fileFullName = templatePath + File.separator + datePath + File.separator+ fileConfig;
		HSSFWorkbook workbooknew = null;
		try {
			// 创建导出报表文件
			workbooknew = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(templatePath + exportTableModel)));
			HSSFSheet ws = workbooknew.getSheetAt(0);
			int rowStart = ws.getLastRowNum()+1;
			HSSFFont font = workbooknew.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)10);
			HSSFDataFormat format = workbooknew.createDataFormat();
			// formatRate:0.0000%
			HSSFCellStyle csDoubleRate = workbooknew.createCellStyle();
			csDoubleRate.setFont(font);
			csDoubleRate.setDataFormat(format.getFormat("0.0000%"));
			
			// formatPrice:###,##0.0000
			HSSFCellStyle csDoublePrice = workbooknew.createCellStyle();
			csDoublePrice.setFont(font);
			csDoublePrice.setDataFormat(format.getFormat("#,##0.0000"));

			// formatPrice:###,##0.000000000
			HSSFCellStyle csSplit = workbooknew.createCellStyle();
			csSplit.setFont(font);
			csSplit.setDataFormat(format.getFormat("#,##0.000000000"));

			// default:#,##0.00
			HSSFCellStyle csDoubleDefault = workbooknew.createCellStyle();
			csDoubleDefault.setFont(font);
			csDoubleDefault.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			
			HSSFCellStyle csDefault = workbooknew.createCellStyle();
			csDefault.setFont(font);

			HSSFCell cell = null;
			// 生成头部信息
			createHeaderExcel(ws, csDefault, header);
			for (int i = 0; i < datals.size(); i++) {
				HSSFRow row = ws.createRow(i+rowStart);
				int j = 0;
				if ("rowNum".equals(exportItems[0])) {
					// 序号
					cell = row.createCell((short)j);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(i+1);
					j = 1;
				}
				for (; j < exportItems.length; j++) {
					cell = row.createCell((short)j);
					String cellString = "";
					Object result = null;
					if (exportItems[j].matches("formatDate\\((.+)\\)")) {
						//需要formatDate 输出格式yyyy-MM-dd
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						String firstLetter = exportItem.substring(0, 1).toUpperCase();
						String getMethodName = "get" + firstLetter + exportItem.substring(1);
						Method getMethod = printClass.getMethod(getMethodName, new Class[] {});
						cellString = DateUtil.getDateStr(
								(Date)getMethod.invoke(datals.get(i), new Object[]{}), "yyyy-MM-dd");
					} else if (exportItems[j].matches("formatTime\\((.+)\\)")) {
						//需要formatTime 输出格式yyyy-MM-dd HH:mm:ss
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						String firstLetter = exportItem.substring(0, 1).toUpperCase();
						String getMethodName = "get" + firstLetter + exportItem.substring(1);
						Method getMethod = printClass.getMethod(getMethodName, new Class[] {});
						cellString = DateUtil.getDateStr(
								(Date)getMethod.invoke(datals.get(i), new Object[]{}), "yyyy-MM-dd HH:mm:ss");
					} else if (exportItems[j].matches("formatHour\\((.+)\\)")) {
						//需要formatTime 输出格式yyyy-MM-dd HH:mm:ss
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						String firstLetter = exportItem.substring(0, 1).toUpperCase();
						String getMethodName = "get" + firstLetter + exportItem.substring(1);
						Method getMethod = printClass.getMethod(getMethodName, new Class[] {});
						cellString = DateUtil.getDateStr(
								(Date)getMethod.invoke(datals.get(i), new Object[]{}), "yyyy-MM-dd HH:mm");
					} else if (exportItems[j].matches("formatRate\\((.+)\\)")) {
						// 需要formatRate 输出格式0.0000%
						String exportItem = exportItems[j].substring(11, exportItems[j].length()-1);
						result = Maths.div((Double)getResult(exportItem, printClass, datals.get(i), 4), 100);
					} else if (exportItems[j].matches("formatPrice\\((.+)\\)")) {
						// 需要formatPrice 输出格式0.0000
						String exportItem = exportItems[j].substring(12, exportItems[j].length()-1);
						result = getResult(exportItem, printClass, datals.get(i), 4);
					} else if (exportItems[j].matches("formatSplit\\((.+)\\)")) {
						// 需要formatSplit 输出格式0.00000000
						String exportItem = exportItems[j].substring(12, exportItems[j].length()-1);
						result = getResult(exportItem, printClass, datals.get(i), 8);
					}
					else {
						// 不需要format
						result = getResult(exportItems[j], printClass, datals.get(i), 2);
					}

					if (result != null) {
						//format cell
						if (result instanceof Double) {
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if (exportItems[j].matches("formatRate\\((.+)\\)")) {
								cell.setCellStyle(csDoubleRate);
							} else if (exportItems[j].matches("formatPrice\\((.+)\\)")) {
								cell.setCellStyle(csDoublePrice);
							} else if (exportItems[j].matches("formatSplit\\((.+)\\)")) {
								cell.setCellStyle(csSplit);
							}else {
								cell.setCellStyle(csDoubleDefault);
							}
							cell.setCellValue((Double)result);
						} else if (result instanceof Integer) {
							cell.setCellStyle(csDefault);
							cell.setCellValue((Integer)result);
						} else {
//							cell.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell.setCellStyle(csDefault);
							cell.setCellValue((String)result);
						}
					} else {
						// 导出数据
//						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(cellString);
					}
				}
			}
			FileOutputStream out = new FileOutputStream(fileFullName);
			workbooknew.write(out);
			out.close();
		} catch (Exception e) {
			logger.error("生成报表失败：" + e.getMessage(), e);
			throw e;
		}
		logger.info("生成报表成功。");
		return fileFullName;
	}

	public String getFileConfig(String exportTableModel) {
		// 模板路径
		String datePath = DateUtil.getDateStr(new Date(), "yyyyMMdd");
		String exportModelPath = templatePath + File.separator + datePath + File.separator;
		// 取配置的文件名
		String fileName = exportTableModel;

		try {
			File file = new File(exportModelPath);
			if(!file.exists()) {
				file.mkdirs();
			}
			// 删除原生成文件
//			String[] temp = file.list();
//			File fileTemp = null;
//			for (String fileNameTemp:temp) {
//				if(fileNameTemp.length() < 6){
//					continue;
//				}
//				if (fileNameTemp.substring(5).equals(fileName)) {
//					fileTemp = new File(exportModelPath+fileNameTemp);
//					fileTemp.delete();
//				}
//			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} 
		fileName = StrUtil.formatString(String.valueOf((new Random()).nextInt(10000)), 5, 3)+fileName;
		return fileName;
	}
	
	

	/**
	 * 设置EXCEL单元格属性
	 * @param type 要求的类型
	 * @param border 是否需要画边框
	 * @return WritableCellFormat对象
	 * @throws WriteException
	 */
//	private static WritableCellFormat getWritableCellFormat(int type, boolean border) throws WriteException {
//		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 9, WritableFont.NO_BOLD);
//		WritableCellFormat format;
//		switch (type) {
//		// 设置format类型
//		case FORMAT_AMOUNT:
//			format = new WritableCellFormat(font, new NumberFormat("###,##0.00"));
//			break;
//		case FORMAT_PRICE:
//			format = new WritableCellFormat(font, new NumberFormat("###,##0.0000"));
//			break;
//		case FORMAT_RATE:
//			format = new WritableCellFormat(font, new NumberFormat("0.0000%"));
//			break;
//		case FORMAT_STRING:
//			format = new WritableCellFormat(font);
//			break;
//		default:
//			format = new WritableCellFormat();
//		}
//		// 设置边框
//		if (border) {
//			format.setBorder(Border.ALL, BorderLineStyle.THIN);
//		}
//		return format;
//	}

	/**
	 * 计算printClass的对象data里string1的值加string2减string3的值
	 * @param thisExportItem 格式：string1+string2-string3...(string可以是一个整数)
	 * @param printClass
	 * @param data
	 * @return
	 * @throws Exception 
	 * @author panhf
	 * @since  2009-11-17 下午03:50:52
	 */
	private static Object getResult(String thisExportItem,Class<? extends Object> printClass,
			Object data, int scale) throws Exception {
		String[] thisExportItems = StrUtil.splitString(thisExportItem, "[+-]");
		double cellValueD = 0;
		int cellValueI = 0;
		boolean douIntFlag = true;//默认为double型相加，只能同型相加
		for (int k = 0; k < thisExportItems.length; k=k+2) {
			if (thisExportItems[k].matches("\\d+")) {
				// 整数相加可以在末尾直接加上一个数字(只能在末尾加)
				if ("+".equals(thisExportItems[k-1])) {
					cellValueI += Integer.valueOf(thisExportItems[k]);
				} else if ("-".equals(thisExportItems[k-1])) {
					cellValueI -= Integer.valueOf(thisExportItems[k]);
				}
				continue;
			}
			String firstLetter = thisExportItems[k].substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + thisExportItems[k].substring(1);
			Method getMethod = printClass.getMethod(getMethodName, new Class[] {});
			if (k==0) {
				// 初期值
				Object temp = getMethod.invoke(data, new Object[]{});
				if (temp instanceof Integer) {
					douIntFlag = false;
					cellValueI = (Integer)temp;
				} else if (temp instanceof Double) {
					cellValueD = (Double)temp;
				} else {
					return (temp==null?"":temp).toString();//其他类型没有相加的情况，直接返回
				}
				continue;
			}
			if ("+".equals(thisExportItems[k-1])) {
				Object temp = getMethod.invoke(data, new Object[]{});
				if (temp instanceof String) {
					// double+string的场合以double（string）格式导出
					// 自动续约交易申请列表导出时专用
					return String.valueOf((new BigDecimal(cellValueD)).setScale(scale, BigDecimal.ROUND_HALF_UP))
						+ "(" + temp.toString() + ")";
				}
				// 相加
				if (douIntFlag) {
					cellValueD = Maths.add(cellValueD, (Double)temp);
				} else {
					cellValueI += (Integer)temp;
				}
			} else if ("-".equals(thisExportItems[k-1])) {
				// 相减
				if (douIntFlag) {
					cellValueD = Maths.sub(cellValueD, (Double)getMethod.invoke(data, new Object[]{}));
				} else {
					cellValueI -= (Integer)getMethod.invoke(data, new Object[]{});
				}
			}
		}
		// 格式化返回的字符串
		if (douIntFlag) {
			return cellValueD;
		} else {
			return cellValueI;
		}
	}

	/**
	 * 获取打印项目
	 * @param printItem
	 * @return 
	 * @author panhf
	 * @since  2009-11-16 上午09:12:06
	 */
	public static String[] getPrintConfig(String printItem) {
		return printItem.split(",");
	}
	
	/** 
     * insert row into the target sheet, the style of cell is the same as startRow 
     * @param wb 
     * @param sheet 
     * @param rows
     */  
	public static void poiInsertRow(HSSFWorkbook wb, HSSFSheet sheet, int currentLoopRow, int rows) {
		int starRow = currentLoopRow - 1;
		sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows);
	}


	/**
	 * 创建头部信息(订单详情)
	 */
	public static void createHeaderExcel(HSSFSheet ws, HSSFCellStyle csDefault, Object object) {
		if(object != null && ws != null) {
			if(object instanceof HashMap) {
				Map<String, Object> map = (HashMap<String, Object>) object;
				
				HSSFRow row0 = ws.getRow(0);
				HSSFCell cell0 = row0.createCell((short) 1);
				cell0.setCellStyle(csDefault);
				cell0.setCellValue(String.valueOf(map.get("merchNo")));
				HSSFCell cell1 = row0.createCell((short) 3);
//				cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell1.setCellStyle(csDefault);
				cell1.setCellValue(String.valueOf(map.get("merchName")));
				
				HSSFRow row1 = ws.getRow(1);
				HSSFCell cell10 = row1.createCell((short) 1);
				cell10.setCellStyle(csDefault);
				cell10.setCellValue(String.valueOf(map.get("balance")));
				HSSFCell cell11 = row1.createCell((short) 3);
				cell11.setCellStyle(csDefault);
				cell11.setCellValue(String.valueOf(map.get("donateBalance")));
				
				HSSFRow row2 = ws.getRow(2);
				HSSFCell cell20 = row2.createCell((short) 1);
				cell20.setCellStyle(csDefault);
				cell20.setCellValue(String.valueOf(map.get("totalAmount")));
			} 
		}
	}
	
}
