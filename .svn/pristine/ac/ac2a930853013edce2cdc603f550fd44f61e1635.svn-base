package kr.nomad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.icu.impl.InvalidFormatException;

public class XlsxWriter {
	
	
	private HSSFWorkbook workbook = null;
	private String       filePath = null;

	/**
	 * 엑셀 파일 제어 인스턴스를 반환
	 * <br><br>
	 * @param _fileType              파일구분(TXT, HWP, EXCEL, MSWORD)
	 * @param _filePath              파일경로
	 * @throws FileNotFoundException 실제 파일이 만들수 없을 경우 예외 발생
	 * @return                       엑셀 파일 제어 인스턴스를 반환
	 ******************************************************************************************************/
	public XlsxWriter(String _filePath) {
			
		filePath = _filePath;
		workbook = new HSSFWorkbook();		
	}
	
	/**
	 * LIST 안에 LIST 를 엑셀 파일로 작성
	 * <br> * 주의사항 : 목록 작성 시 List 안에 필요한 모든 row 목록을 List 로 만들어 작성해야 함
	 * <br><br>
	 * @param _title    엑셀에 작성 할 제목 정보, null 이라면 제목은 작성하지 않음
	 * @param _contents 엑셀에 작성 할 정보
	 ******************************************************************************************************/
	public void writeFile(List _title, List _contents) {

		HSSFSheet sheet =  workbook.createSheet();
		
		HSSFCellStyle cellStyle = getCellStyle(workbook.createCellStyle());
		
		// 제목 줄
		if( null != _title ){
			HSSFRow titleRow = getRowStyle(sheet.createRow(0));
			
			for(int i=0; i<_title.size(); i++){
				
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i)+5000);
				
				HSSFCell cell = titleRow.createCell(i);
				
				cell.setCellStyle(cellStyle);
	            cell.setCellValue((String)_title.get(i));
	            
			}
		}
		
		// 컨텐츠 줄
        for(int i=0; i<_contents.size(); i++){

        	List tempResult = (List)_contents.get(i);
        	
        	HSSFRow contentsRow = getRowStyle(sheet.createRow(i + (null == _title ? 0 : 1)));

        	contentsRow.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints())); 
        	contentsRow.setHeight((short)300);

        	for (int k=0; k<tempResult.size(); k++){
        		
        		HSSFCell cell  = contentsRow.createCell(k);
        		Object   value = tempResult.get(k);
        		
        		cellStyle.setWrapText(true);
        		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        		
        		cell.setCellStyle(cellStyle);
        		
	            if( null != value ){
	            	cell.setCellValue(String.valueOf(value));
	            }
	            else{
	            	cell.setCellValue("");
	            }
	        }
        }
        
        
        FileOutputStream output   = null;
        
        try{
        	output = new FileOutputStream(filePath);
	        workbook.write(output);
        }
        catch(IOException e1){
        	
        }
        finally{
        	if ( null != output ){
	        	try { output.close(); } catch (IOException e2) { }
	        } 
        }
	}
	
	/**
	 * CELL STYLE 정의
	 * <br><br>
	 * @param  _cellStyle HSSFCellStyle 인스턴스
	 * @return CELL STYLE 이 정의 된 HSSFCellStyle 반환
	 ******************************************************************************************************/
	private HSSFCellStyle getCellStyle(HSSFCellStyle _cellStyle){
		        
		// 상하좌우 BORDER LINE 정책
		_cellStyle.setBorderLeft       (HSSFCellStyle.BORDER_THIN);
		_cellStyle.setBorderRight      (HSSFCellStyle.BORDER_THIN);
		_cellStyle.setBorderTop        (HSSFCellStyle.BORDER_THIN);
		_cellStyle.setBorderBottom     (HSSFCellStyle.BORDER_THIN);

        // 상하좌우 BORDER COLOR 정책
		_cellStyle.setLeftBorderColor  (HSSFColor.BLACK.index    );
		_cellStyle.setRightBorderColor (HSSFColor.BLACK.index    );
		_cellStyle.setTopBorderColor   (HSSFColor.BLACK.index    );
		_cellStyle.setBottomBorderColor(HSSFColor.BLACK.index    );
		
		// 자동 줄 바꿈
		_cellStyle.setWrapText(true);
        
        
        return _cellStyle;
	}
	
	/**
	 * ROW STYLE 정의
	 * <br><br>
	 * @param  _rowSyle HSSFRow 인스턴스
	 * @return ROW STYLE 이 정의 된 HSSFRow 반환
	 ******************************************************************************************************/
	private HSSFRow getRowStyle(HSSFRow _rowSyle){
		
		// HEIGHT 정책
		_rowSyle.setHeight((short)350);
        
        
        return _rowSyle;
	}
	
	public List excelImportXlsx() throws InvalidFormatException, FileNotFoundException, IOException{
		
		String[][] cellValue = null;
		List contents = new ArrayList();
		
		FileInputStream fi = new FileInputStream(new File(filePath));
		try {
			
			
			XSSFWorkbook wb = new XSSFWorkbook(fi);
	
			Sheet sheet = wb.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
	        cellValue = new String[rows][]; 
			
	        	
			//행별로 데이터 가져오기
	        for(int rowIdx=0; rowIdx<rows; rowIdx++){
	            //시트에서 행 가져오기
	            Row row = sheet.getRow(rowIdx);
	
	            if(row != null){
	                cellValue[rowIdx] = new String[row.getLastCellNum()];
	                List dataList = new ArrayList();
	                //각행의 셀 데이타 가져오기
	                //for(int cellIdx = row.getFirstCellNum(); cellIdx <= row.getFirstCellNum()+1; cellIdx++){
	                for(int cellIdx = 0; cellIdx <= 1; cellIdx++){
	                    //셀 데이타 추출
	                    Cell cell = row.getCell(cellIdx);
	                    if(cell==null) {
	                    	dataList.add("");
	                	} else {
	                    	int type = cell.getCellType();
	                    	if (type == 0) {
	                    		int n = (int)cell.getNumericCellValue();
	                    		String num = Integer.toString(n);
	                    		if(num.length()==8) {
	                    			num = "010"+num;
	                    		} else if (num.length()==9 || num.length()==10) {
	                    			num = "0"+num;
	                    		}
	                        	dataList.add(num);
	                    	} else {
	                    		String num = cell.getStringCellValue().replaceAll("[-]", "");
	                        	dataList.add(num);
	                    	}
	                    }
	                }
	                contents.add(dataList);
	            }
	        }
        
		} finally {
			// TODO: handle exception
			
			fi.close();
		}

        return contents; 
	}
	
	public List excelImportXls() throws InvalidFormatException, FileNotFoundException, IOException{
		
		String[][] cellValue = null;
		
		try {
			workbook = (HSSFWorkbook) WorkbookFactory.create(new FileInputStream(new File(filePath)));
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);
		
		int rows = sheet.getPhysicalNumberOfRows();
		
        cellValue = new String[rows][]; 
		
        List contents = new ArrayList();

		//행별로 데이터 가져오기
        for(int rowIdx=0; rowIdx<rows; rowIdx++){
            //시트에서 행 가져오기
            Row row = sheet.getRow(rowIdx);

            if(row != null){
                cellValue[rowIdx] = new String[row.getLastCellNum()];
                List dataList = new ArrayList();
                //각행의 셀 데이타 가져오기
                //for(int cellIdx = row.getFirstCellNum(); cellIdx <= row.getFirstCellNum()+1; cellIdx++){
                for(int cellIdx = 0; cellIdx <= 1; cellIdx++){
                    //셀 데이타 추출
                    Cell cell = row.getCell(cellIdx);
                    if(cell==null) {
                       	dataList.add("");
                    } else {
                    	int type = cell.getCellType();
                    	if (type == 0) {
                    		int n = (int)cell.getNumericCellValue();
                    		String num = Integer.toString(n);
                    		if(num.length()==8) {
                    			num = "010"+num;
                    		} else if (num.length()==9 || num.length()==10) {
                    			num = "0"+num;
                    		}
                        	dataList.add(num);
                    	} else {
                    		String num = cell.getStringCellValue().replaceAll("[-]", "");
                        	dataList.add(num);
                    	}
                    }
                }
                contents.add(dataList);
            }
        }
        return contents; 
	}
}









