package com.clinic.impl;

import com.clinic.entities.Modification;
import com.clinic.entities.Order;
import com.clinic.entities.Person;
import com.clinic.services.PDFService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class PDFServiceImpl implements PDFService {

    String generalCommercialPath = "./commercial/commercialForOrder_<order>.pdf";
    String pathToFont = "/fonts/TimesNewRoman.ttf";
    String fontname;
    BaseFont baseFont;

    public PDFServiceImpl() throws DocumentException, IOException {

        fontname = PDFServiceImpl.class.getResource(pathToFont).toString();
        baseFont =BaseFont.createFont(fontname, "cp1251", BaseFont.EMBEDDED);
    }

    public String generateCommercial(Order order) throws IOException, DocumentException {

        Person currentPerson = order.getClient().getPerson();

        Document document = new Document();

        String filePath = this.generalCommercialPath.replace("<order>",String.valueOf(order.getId()));

        PdfWriter writer =PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));

        document.open();
        document.newPage();
        String dateOfCreation = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        Paragraph header = new Paragraph(
            "Коммерческое предложение по заказу №"+order.getId()+"\r\n для "+currentPerson.getSurname()+" "+currentPerson.getName()+" "+ currentPerson.getPatronymic(),
                new Font(baseFont,18)
        );
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        header = new Paragraph(
                " \r\n Предоставляемое коммерческое предложение действительно один день с даты создания. \r\n Дата предложения: "+ dateOfCreation,
                new Font(baseFont,10)
        );

        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        document.add(generatePriceTable(order.getModifications()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        header = new Paragraph(
                "Подпись заказчика: ___________/"+currentPerson.getSurname()+" "+currentPerson.getName().charAt(0)+". "+currentPerson.getPatronymic().charAt(0)+".",
                new Font(baseFont,12)
        );

        header.setAlignment(Element.ALIGN_RIGHT);

        document.add(header);

        document.close();

        writer.close();
        return filePath.toString();
    };

    public PdfPTable generatePriceTable(Set<Modification> modificationSet){
        PdfPTable table = new PdfPTable(2);

        AtomicReference<Float> sum = new AtomicReference<>((float) 0);

        Stream.of("Название", "Стоимость")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    //header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle,new Font(baseFont,12)));
                    table.addCell(header);
                });

        modificationSet.forEach(modification -> {
            table.addCell(new Phrase(modification.getName(),new Font(baseFont,12)));
            table.addCell(new Phrase(modification.getPrice()+" "+modification.getCurrency(),new Font(baseFont,12)));
            sum.updateAndGet(v -> (float) (v + modification.getPrice()));
        });

        List<PdfPCell> footer = new ArrayList<>();
        footer.add(new PdfPCell(new Phrase("Итого: ",new Font(baseFont,12,Font.BOLD))));
        footer.add(new PdfPCell(new Phrase(sum.get() +" "+modificationSet.iterator().next().getCurrency(),new Font(baseFont,12,Font.BOLD))));

        footer.forEach(cell->{
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            //cell.setBorderWidth(2);
            table.addCell(cell);
        });

        return  table;

    }
}
