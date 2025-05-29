
package services;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import model.LeaveApplication;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PdfGenerationService {

    public File generateApprovedLeavePdf(LeaveApplication leave) throws Exception {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String filePath = "src/main/resources/static/leave-form-" + leave.getId() + ".pdf";
        OutputStream out = new FileOutputStream(filePath);
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Font dottedFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.UNDERLINE);

        // Header
        Paragraph header = new Paragraph("The Uganda Public Service Standing Orders", titleFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        Paragraph formTitle = new Paragraph("APPLICATION FOR LEAVE", titleFont);
        formTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(formTitle);

        Paragraph appendix = new Paragraph("Appendix C-2     PSF 12", normalFont);
        appendix.setAlignment(Element.ALIGN_RIGHT);
        document.add(appendix);

        document.add(Chunk.NEWLINE);

        // SECTION I
        addLine(document);
        document.add(new Paragraph("SECTION I: To be addressed to the Responsible Officer/Head of Department/Division", sectionFont));
        document.add(Chunk.NEWLINE);
        addDottedLine(document, "To: ", leave.getToOfficer().getName());
        addDottedLine(document, "Thru: ", leave.getThruOfficer1().getName());
        addDottedLine(document, "Thru: ", leave.getThruOfficer2().getName());
        addDottedLine(document, "Name: ", leave.getEmployee().getName() + "    Designation: " + leave.getEmployee().getDesignation());
        addDottedLine(document, "Department: ", leave.getEmployee().getDepartment());
        addDottedLine(document, "Leave applied for: ", leave.getDaysApplied() + " days p.m.");
        addDottedLine(document, "Leave Address/Telephone No./E-mail: ", leave.getContactInfo());
        addDottedLine(document, "Date: ", leave.getApplicationDate().toString());
        addDottedLine(document, "Signature of Officer: ", "__________________________");

        document.add(Chunk.NEWLINE);

        // SECTION II
        addLine(document);
        document.add(new Paragraph("SECTION II: To be completed by Head of Human Resource", sectionFont));
        document.add(new Paragraph("COMPUTATION OF LEAVE", sectionFont));
        document.add(Chunk.NEWLINE);
        addDottedLine(document, "Leave due in (year): ", String.valueOf(leave.getComputation().getLeaveDue()));
        addDottedLine(document, "Less leave taken: ", String.valueOf(leave.getComputation().getLeaveTaken()));
        addDottedLine(document, "Balance: ", String.valueOf(leave.getComputation().getBalance()));
        document.add(new Paragraph("LEAVE AS COMPUTED ABOVE RECOMMENDED/APPROVED", normalFont));
        document.add(new Paragraph("This application is in accordance with the leave roster.", normalFont));
        addDottedLine(document, "Computation checked and leave recorded by: ", "_________________");
        addDottedLine(document, "Head of Human Resource: ", "_________________      Date: ______________");

        document.add(Chunk.NEWLINE);

        // SECTION III
        addLine(document);
        document.add(new Paragraph("SECTION III: To be completed by Responsible Officer", sectionFont));
        addDottedLine(document, "To: ", leave.getEmployee().getName());
        document.add(new Paragraph("Your application for leave from " + leave.getStartDate() + " to " +
                leave.getEndDate() + " is approved/not approved*", normalFont));
        addDottedLine(document, "Signature of Responsible Officer: ", "_________________      Date: ___________");
        addDottedLine(document, "Name: ", leave.getToOfficer().getName());
        addDottedLine(document, "Designation: ", leave.getToOfficer().getDesignation());
        addDottedLine(document, "Official Stamp and date: ", "_________________");
        document.add(new Paragraph("*Delete whichever is not applicable", normalFont));

        document.close();
        return new File(filePath);
    }

    private void addDottedLine(Document document, String label, String value) throws DocumentException {
        Paragraph p = new Paragraph();
        p.setSpacingAfter(5);
        p.add(new Chunk(label, new Font(Font.FontFamily.TIMES_ROMAN, 12)));
        Chunk dottedValue = new Chunk(value + " .", new Font(Font.FontFamily.TIMES_ROMAN, 12));
        p.add(dottedValue);
        document.add(p);
    }

    private void addLine(Document document) throws DocumentException {
        LineSeparator separator = new LineSeparator();
        separator.setOffset(-2);
        document.add(new Chunk(separator));
    }
}
