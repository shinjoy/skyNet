package kr.nomad.util.file;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ThumbImageFile {
	/**
	 * Image Resize 로직
	 * @param image                     BufferedImage bufferedImage = ImageIO.read(new File(imageLoc));
	 * @param targetSize                변경할 size : 최대 size 기준
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage createJustScaledImage(BufferedImage image, int targetSize) throws Exception {
	    double resizeRatio = 1.0;
	    int width = image.getWidth();
	    int height = image.getHeight();
	    if(width > height) {
	        resizeRatio = (double)targetSize / width;
	    } else {
	        resizeRatio = (double)targetSize / height;
	    }
	     
	    if(resizeRatio > 1) {
	        throw new Exception("target image size must smaller than original image size.");
	    }
	     
	    int rewidth = (int)(width * resizeRatio);
	    int reheight = (int)(height * resizeRatio);
	    int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage bufferdImage = new BufferedImage(rewidth, reheight, type);
	    Graphics2D g2 = bufferdImage.createGraphics(); 
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	     
	    AffineTransform xform = AffineTransform.getScaleInstance(resizeRatio, resizeRatio);
	    g2.drawRenderedImage((RenderedImage)image, xform);
	     
	    g2.dispose();
	     
	    return bufferdImage;
	}
	 
	/**
	 * Image Thumb 생성
	 * 
	 * @param image
	 * @fileFormat                      jpg
	 * @param targetFile
	 * @throws Exception
	 */
	public static boolean resizeImage(BufferedImage bufferedImage, String fileFormat, String targetFile) throws Exception {
	    File save = new File(targetFile);
	    return javax.imageio.ImageIO.write(bufferedImage, fileFormat, save);
	}
	
	
	public static boolean createThumb(String filepath, String filepathThumb) {		
        try {
        	String ext = filepath.substring(filepath.lastIndexOf(".")+1);
    		
    		BufferedImage orgImage = ImageIO.read(new File(filepath));
            BufferedImage bufferedImage = createJustScaledImage(orgImage, 280);
			return resizeImage(bufferedImage, ext, filepathThumb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	 
}
