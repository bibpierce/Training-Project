package com.bibvip;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.bibvip.TableColumnNames.*;

public class ExcelUtils {


    public void writeDataLines(ResultSet result, XSSFWorkbook workbook,
                               XSSFSheet sheet) throws SQLException, IOException {
        int pRowCount = 1;
        while (result.next()) {
            int pcColCountCount = 2;

            String id = result.getString(ID);
            String name = result.getString(NAME);
            byte[] picture = result.getBytes(PICTURE);
            byte[] vacCard = result.getBytes(VACCINE_CARD);
            byte[] primaryId = result.getBytes(PRIMARY_ID);
            byte[] secondaryId = result.getBytes(SECONDARY_ID);

            drawExport(workbook,
                    sheet, pRowCount, picture, pcColCountCount++);

            drawExport(workbook,
                    sheet, pRowCount, vacCard, pcColCountCount++);

            drawExport(workbook,
                    sheet, pRowCount, primaryId, pcColCountCount++);

            drawExport(workbook,
                    sheet, pRowCount, secondaryId, pcColCountCount);

            Row row = sheet.createRow(pRowCount++);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(id);
            cell = row.createCell(columnCount);
            cell.setCellValue(name);
            for (int i = 0; i < 7; i++) {
                sheet.setColumnWidth(i, 4200);
                cell.getRow().setHeight((short) 1200);
            }
        }
    }

    public void drawExport(XSSFWorkbook workbook,
                           XSSFSheet sheet, int pcRowCount, byte[] all, int pcColCount) {


        CreationHelper helper = workbook.getCreationHelper();
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();

        anchor.setCol1(pcColCount++);
        anchor.setCol2(pcColCount);
        anchor.setRow1(pcRowCount++);
        anchor.setRow2(pcRowCount);
        drawing.createPicture(anchor, workbook.addPicture(all, Workbook.PICTURE_TYPE_JPEG));
    }


    public void writeHeaderLine(XSSFSheet sheet) {

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("ID");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Name");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("2x2 Picture");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Vaccination Card");

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Primary ID");

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("Secondary ID");
    }


}
