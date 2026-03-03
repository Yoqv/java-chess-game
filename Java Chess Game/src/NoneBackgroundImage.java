import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

@SuppressWarnings("serial")
public class NoneBackgroundImage extends ImageIcon // Creates ImageIcon with no background (removes all red pixels which are used as background)
{

	public NoneBackgroundImage(String fileName) {
		super(NoneBackgroundImage.class.getResource(fileName));
		ImageFilter filter = new RGBImageFilter() 
		{
		int transparentColor = Color.RED.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) 
			{
				if ((rgb | 0xFF000000) == transparentColor)
					return 0x00FFFFFF & rgb;
				else 
					return rgb;
			}
		};

		ImageProducer filteredImgProd = new FilteredImageSource(getImage().getSource(), filter);
		Image transparentImg = Toolkit.getDefaultToolkit().createImage(filteredImgProd);
		setImage(transparentImg);
	}
	


}
