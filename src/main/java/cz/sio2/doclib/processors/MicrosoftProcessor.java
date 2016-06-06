package cz.sio2.doclib.processors;

import cz.sio2.doclib.ExplorationResult;
import cz.sio2.doclib.FileProcessor;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MicrosoftProcessor implements FileProcessor {

    @Override
    public boolean accept(File file) {
        return file.getAbsolutePath().endsWith(".doc") || file.getAbsolutePath().endsWith(".docx") || file.getAbsolutePath().endsWith(".xls") || file.getAbsolutePath().endsWith(".xlsx") || file.getAbsolutePath().endsWith(".ppt") || file.getAbsolutePath().endsWith(".pptx");
    }

    @Override
    public ExplorationResult process(File file) {
        final ExplorationResult result = new ExplorationResult();
        try {

            String lowerFilePath = file.getAbsolutePath().toLowerCase();
            if (lowerFilePath.endsWith(".xls")) {
                HSSFWorkbook workbook = null;
                workbook = new HSSFWorkbook(new FileInputStream(file.getAbsolutePath()));

                Integer sheetNums = workbook.getNumberOfSheets();
                if (sheetNums > 0) {
                    // TODO other workbooks
                    result.setNumberOfPages(workbook.getSheetAt(0).getRowBreaks().length + 1);
                }
//        } else if (lowerFilePath.endsWith(".xlsx")) {
//            XSSFWorkbook xwb = new XSSFWorkbook(file.getAbsolutePath());
//            Integer sheetNums = xwb.getNumberOfSheets();
//            if (sheetNums > 0) {
//                result.setNumberOfPages( xwb.getSheetAt(0).getRowBreaks().length + 1);
//            }
//        } else if (lowerFilePath.endsWith(".docx")) {
//            XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(file.getAbsolutePath()));
//            result.setNumberOfPages( docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages());
            } else if (lowerFilePath.endsWith(".doc")) {
                HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(file.getAbsolutePath()));
                if (wordDoc.getSummaryInformation() != null) {
                    result.setNumberOfPages(wordDoc.getSummaryInformation().getPageCount());
                }
            } else if (lowerFilePath.endsWith(".ppt")) {
                HSLFSlideShow document = new HSLFSlideShow(new FileInputStream(file.getAbsolutePath()));
                SlideShow slideShow = new SlideShow(document);
                result.setNumberOfPages(slideShow.getSlides().length);
            } else if (lowerFilePath.endsWith(".pptx")) {
                XSLFSlideShow xdocument = new XSLFSlideShow(file.getAbsolutePath());
                XMLSlideShow xslideShow = new XMLSlideShow(xdocument.getPackage());
                result.setNumberOfPages(xslideShow.getSlides().length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        }
        return result;
    }
}
