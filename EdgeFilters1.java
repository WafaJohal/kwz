import java.awt.*;
import java.awt.image.*;

/**
 * A filter which acts as a superclass for filters which need to have the whole image in memory
 * to do their stuff.
 */
public abstract class EdgeFilters1 extends AbstractBufferedImageOp {

        /**
     * The output image bounds.
     */
    protected Rectangle transformedSpace;

        /**
     * The input image bounds.
     */
        protected Rectangle originalSpace;
        
        /**
         * Construct a FancyWindow.
         */
        public EdgeFilters1() {
        }

    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();
                int type = src.getType();
                WritableRaster srcRaster = src.getRaster();

                originalSpace = new Rectangle(0, 0, width, height);
                transformedSpace = new Rectangle(0, 0, width, height);
                transformSpace(transformedSpace);

        if ( dst == null ) {
            ColorModel dstCM = src.getColorModel();
                        dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(transformedSpace.width, transformedSpace.height), dstCM.isAlphaPremultiplied(), null);
                }
                WritableRaster dstRaster = dst.getRaster();

                int[] inPixels = getRGB( src, 0, 0, width, height, null );
                inPixels = filterPixels( width, height, inPixels, transformedSpace );
                setRGB( dst, 0, 0, transformedSpace.width, transformedSpace.height, inPixels );

        return dst;
    }

        /**
     * Calculate output bounds for given input bounds.
     * @param rect input and output rectangle
     */
        protected void transformSpace(Rectangle rect) {
        }
        
        /**
     * Actually filter the pixels.
     * @param width the image width
     * @param height the image height
     * @param inPixels the image pixels
     * @param transformedSpace the output bounds
     * @return the output pixels
     */
        protected abstract int[] filterPixels( int width, int height, int[] inPixels, Rectangle transformedSpace );



}