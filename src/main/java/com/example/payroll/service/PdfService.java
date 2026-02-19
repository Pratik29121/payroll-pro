package com.example.payroll.service;

import com.example.payroll.entity.Salary;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] generateSlip(Salary salary) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 60, 60);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

            // Fonts
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.WHITE);
            Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(40, 60, 100));
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);
            Font boldFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.BLACK);
            Font netFont = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(0, 128, 0));

            // Header Banner
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            PdfPCell headerCell = new PdfPCell(new Phrase("PAYROLL MANAGEMENT SYSTEM", titleFont));
            headerCell.setBackgroundColor(new Color(30, 60, 120));
            headerCell.setPadding(15);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(headerCell);
            document.add(headerTable);

            PdfPTable subHeaderTable = new PdfPTable(1);
            subHeaderTable.setWidthPercentage(100);
            PdfPCell subCell = new PdfPCell(new Phrase("SALARY SLIP", new Font(Font.HELVETICA, 13, Font.BOLD, new Color(30, 60, 120))));
            subCell.setBackgroundColor(new Color(220, 230, 255));
            subCell.setPadding(8);
            subCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            subCell.setBorder(Rectangle.NO_BORDER);
            subHeaderTable.addCell(subCell);
            document.add(subHeaderTable);

            document.add(new Paragraph(" "));

            // Period
            String period = (salary.getMonth() != null ? salary.getMonth() : "N/A") + " " + (salary.getYear() > 0 ? salary.getYear() : "");
            Paragraph periodPara = new Paragraph("Pay Period: " + period, boldFont);
            periodPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(periodPara);
            document.add(new Paragraph(" "));

            // Employee Info Table
            PdfPTable empTable = new PdfPTable(2);
            empTable.setWidthPercentage(100);
            empTable.setWidths(new float[]{1, 1});

            addInfoRow(empTable, "Employee Name", salary.getEmployee().getName(), headerFont, normalFont);
            addInfoRow(empTable, "Employee Code", salary.getEmployee().getEmpCode(), headerFont, normalFont);
            addInfoRow(empTable, "Designation", salary.getEmployee().getDesignation(), headerFont, normalFont);
            addInfoRow(empTable, "Department", salary.getEmployee().getDepartment() != null ? salary.getEmployee().getDepartment() : "-", headerFont, normalFont);
            addInfoRow(empTable, "Email", salary.getEmployee().getEmail(), headerFont, normalFont);
            addInfoRow(empTable, "Phone", salary.getEmployee().getPhone() != null ? salary.getEmployee().getPhone() : "-", headerFont, normalFont);

            document.add(empTable);
            document.add(new Paragraph(" "));

            // Earnings & Deductions Table
            PdfPTable salaryTable = new PdfPTable(4);
            salaryTable.setWidthPercentage(100);
            salaryTable.setWidths(new float[]{2, 1.5f, 2, 1.5f});

            // Table Header
            String[] headers = {"EARNINGS", "AMOUNT", "DEDUCTIONS", "AMOUNT"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE)));
                cell.setBackgroundColor(new Color(30, 60, 120));
                cell.setPadding(8);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                salaryTable.addCell(cell);
            }

            // Data rows
            addSalaryRow(salaryTable, "Basic Salary", fmt.format(salary.getBasic()), "Provident Fund (PF)", fmt.format(salary.getPf()), normalFont);
            addSalaryRow(salaryTable, "HRA", fmt.format(salary.getHra()), "", "", normalFont);
            addSalaryRow(salaryTable, "Bonus", fmt.format(salary.getBonus()), "", "", normalFont);

            // Totals row
            PdfPCell grossLabel = new PdfPCell(new Phrase("Gross Salary", boldFont));
            grossLabel.setBackgroundColor(new Color(240, 245, 255));
            grossLabel.setPadding(7);
            salaryTable.addCell(grossLabel);

            PdfPCell grossAmt = new PdfPCell(new Phrase(fmt.format(salary.getGrossSalary()), boldFont));
            grossAmt.setBackgroundColor(new Color(240, 245, 255));
            grossAmt.setPadding(7);
            salaryTable.addCell(grossAmt);

            PdfPCell totalDedLabel = new PdfPCell(new Phrase("Total Deductions", boldFont));
            totalDedLabel.setBackgroundColor(new Color(255, 240, 240));
            totalDedLabel.setPadding(7);
            salaryTable.addCell(totalDedLabel);

            PdfPCell totalDedAmt = new PdfPCell(new Phrase(fmt.format(salary.getPf()), boldFont));
            totalDedAmt.setBackgroundColor(new Color(255, 240, 240));
            totalDedAmt.setPadding(7);
            salaryTable.addCell(totalDedAmt);

            document.add(salaryTable);
            document.add(new Paragraph(" "));

            // Net Salary Box
            PdfPTable netTable = new PdfPTable(1);
            netTable.setWidthPercentage(50);
            netTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell netCell = new PdfPCell(new Phrase("NET SALARY: " + fmt.format(salary.getNetSalary()), netFont));
            netCell.setBackgroundColor(new Color(220, 255, 220));
            netCell.setPadding(12);
            netCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            netTable.addCell(netCell);
            document.add(netTable);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Footer
            Paragraph footer = new Paragraph("This is a system-generated salary slip and does not require a signature.\nFor queries, contact HR Department.", new Font(Font.HELVETICA, 8, Font.ITALIC, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed: " + e.getMessage(), e);
        }
        return out.toByteArray();
    }

    private void addInfoRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell lCell = new PdfPCell(new Phrase(label, labelFont));
        lCell.setPadding(6);
        lCell.setBackgroundColor(new Color(235, 240, 255));
        table.addCell(lCell);

        PdfPCell vCell = new PdfPCell(new Phrase(value, valueFont));
        vCell.setPadding(6);
        table.addCell(vCell);
    }

    private void addSalaryRow(PdfPTable table, String e1, String a1, String e2, String a2, Font font) {
        table.addCell(createCell(e1, font, Color.WHITE));
        table.addCell(createCell(a1, font, Color.WHITE));
        table.addCell(createCell(e2, font, Color.WHITE));
        table.addCell(createCell(a2, font, Color.WHITE));
    }

    private PdfPCell createCell(String text, Font font, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(7);
        cell.setBackgroundColor(bg);
        return cell;
    }
}
