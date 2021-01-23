package stocks.xtesting_examples;
import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class MainOcr {

    // info: https://github.com/nguyenq/tess4j
    // info: https://github.com/naptha/tessdata

    public static void main(String[] args) {


	// https://github.com/nguyenq/tess4j/issues/123
	// Maybe needed: setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_TESSERACT_ONLY)



	// Image Improvement/processing is needed to get good results
	// like: https://stackoverflow.com/questions/57160892/tesseract-error-warning-invalid-resolution-0-dpi-using-70-instead
	// and other image processing before using ocr on the image


	Tesseract tesseract = new Tesseract();

	tesseract.setTessVariable("user_defined_dpi", "70");
	try {
	    tesseract.setDatapath("src\\main\\resources\\tessdata");
	    String text = tesseract.doOCR(new File("src\\main\\resources\\Images\\34.png"));


	    File tessDataFolder = LoadLibs.extractTessResources("tessdata");

	    //Set the tessdata path
	    tesseract.setDatapath(tessDataFolder.getAbsolutePath());

	    System.out.print(text);


	} catch (TesseractException e) {
	    e.printStackTrace();
	}



    }


}
