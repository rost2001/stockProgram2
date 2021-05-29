package stocks.model.image;

import java.awt.Color;

public class Colors {

    enum ColorType{
	NORMAL,
	NOBRIGHTNESS;
    }
    public double colorDistance(ColorType type, Color c, Color c2) {

	/* without brightness */
	if(type.name().equals(ColorType.NORMAL.name())) {
	    return Math.sqrt(
		    0.299*(c.getRed() - c2.getRed()) * 0.299*(c.getRed() - c2.getRed()) 
		    + 0.587*(c.getGreen() - c2.getGreen()) * 0.587*(c.getGreen() - c2.getGreen()) 
		    + 0.114*(c.getBlue() - c2.getBlue()) * 0.114*(c.getBlue() - c2.getBlue()));
	}

	/* Normal */
	if (type.name().equals(ColorType.NORMAL.name())) {
	    return Math.sqrt(
		    (c.getRed() - c2.getRed()) * (c.getRed() - c2.getRed())
		    + (c.getGreen() - c2.getGreen()) * (c.getGreen() - c2.getGreen())
		    + (c.getBlue() - c2.getBlue()) * (c.getBlue() - c2.getBlue()));
	}
	return 0;

    }
    
    
}
