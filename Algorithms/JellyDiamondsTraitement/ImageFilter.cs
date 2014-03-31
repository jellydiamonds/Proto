using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    public class ImageFilter:IFilter
    {
        private byte size;
        private double[,] convolutionMatrix;
        private int xCenter;
        private int yCenter;

        public ImageFilter(byte size, int xCenter, int yCenter, double[,] convolutionMatrix)
        {
            // TODO: Complete member initialization
            this.size = size;
            this.xCenter = xCenter;
            this.yCenter = yCenter;
            this.convolutionMatrix = convolutionMatrix;
        }

        public byte Size
        {
            get { return size; }
            set { size = value; }
        }

        public int XCenter
        {
            get { return xCenter; }
            set { xCenter = value; }
        }

        public int YCenter
        {
            get{ return yCenter; }
            set { yCenter = value; }
        }

        public double[,] ConvolutionMatrix
        {
            get { return convolutionMatrix; }
            set { convolutionMatrix = value; }
        }
        
        public Bitmap doFilter(Bitmap image)
        {
            Bitmap filteredImage = new Bitmap(image);
            
            for (int j = 0; j < image.Height; j++)
            {
                for (int i = 0; i < image.Width; i++)
                {
                    filteredImage.SetPixel(i, j, applyFilter(image, i, j));
                }
            }

            return filteredImage;
        }

        private Color applyFilter(Bitmap image, int i, int j)
        {
            Color pixel;
            double red = 0;
            double green = 0;
            double blue = 0;
            for (int n = 0; n < size; n++)
            {
/* Le centre du filtre par rapport à l'image doit faire correspondre (xCenter,yCenter) avec (i,j).
 * Les coordonnées du les points du filtre sont donc repérés par : (i-xCenter+m,j-yCenter+n)
 * Il est nécessaire pour le calcul de chaque point du filtre de vérifier qu'on n'essaie de lire un pixel hors de l'image.
 */
                if (j - yCenter + n < 0 || j - yCenter + n >= image.Height) continue;
                for (int m = 0; m < size; m++)
                {
                    if (i - xCenter + m < 0 || i - xCenter + m >= image.Width || convolutionMatrix[m,n] == 0) continue;
                    pixel = image.GetPixel(i - xCenter + m, j - yCenter + n);
                    red += pixel.R * convolutionMatrix[m,n];
                    green += pixel.G * convolutionMatrix[m,n];
                    blue += pixel.B * convolutionMatrix[m,n];
                }
            }
            if (red < 0) red = 0; else if (red > 255) red = 255;
            int ired = (int)red;

            //green /= counter;
            if (green < 0) green = 0; else if (green > 255) green = 255;
            int igreen = (int)green;

            //blue /= counter;
            if (blue < 0) blue = 0; else if (blue > 255) blue = 255;
            int iblue = (int)blue;

            return Color.FromArgb(ired,igreen,iblue);
        }
    }
}
