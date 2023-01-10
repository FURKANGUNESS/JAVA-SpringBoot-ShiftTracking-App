package com.shift.tracking.view;

import com.shift.tracking.entity.Shift;
import com.shift.tracking.request.ShiftForm;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {


        response.setHeader("Content-Disposition", "attachment; filename=\"shift.xls\"");

        //model' den post olarak gonderilen shiftform nesnesi alınır.
        ShiftForm shiftForm = (ShiftForm) model.get("shiftForm");
        List<Shift> shifts = shiftForm.getShifts();
        // Excel sheet olusturulur.
        Sheet sheet = workbook.createSheet("User shift");
        sheet.setDefaultColumnWidth(30);



        // Ilk satira basliklar eklenir
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Employee Name");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Description");
        header.createCell(3).setCellValue("Start Time");
        header.createCell(4).setCellValue("End Time");


        int rowCount = 1;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        // shifts listesindeki veriler satır satır excel e aktarilir.
        for (Shift shift : shifts) {
            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName());
            userRow.createCell(1).setCellValue(df.format(shift.getDate()));
            userRow.createCell(2).setCellValue(shift.getDescription());
            userRow.createCell(3).setCellValue(shift.getStartTime());
            userRow.createCell(4).setCellValue(shift.getEndTime());

        }

    }
}
