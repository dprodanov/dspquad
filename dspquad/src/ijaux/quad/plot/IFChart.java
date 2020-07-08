package ijaux.quad.plot;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;

/**
 * 
 * @author prodanov
 *
 */
public interface IFChart {

	/**
	 * 
	 * @param chart
	 * @param width
	 * @param height
	 * @param path
	 * @return
	 */
	default boolean exportAsPNG (JFreeChart chart, int width, int height, String path)  {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();

		g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		boolean fstate=false;
		try {
			File f = new File(path);
			if (f.exists()) 
				fstate=true; 
			else 
				fstate=f.createNewFile(); 
			if (fstate) {
				BufferedImage chartImage = chart.createBufferedImage( width, height, null); 
				ImageIO.write( chartImage, "png", f ); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fstate;
	}

}