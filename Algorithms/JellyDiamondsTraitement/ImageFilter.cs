using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    public class ImageFilter:IFilter
    {
        private byte size;
        private double[,] convolutionMatrix;
        private int xCenter;
        private int yCenter;
        private int reductionX;
        private int reductionY;

        public ImageFilter(byte size, int xCenter, int yCenter, double[,] convolutionMatrix, int reductionX, int reductionY)
        {
            // TODO: Complete member initialization
            this.size = size;
            this.xCenter = xCenter;
            this.yCenter = yCenter;
            this.convolutionMatrix = convolutionMatrix;
            this.reductionX = reductionX;
            this.reductionY = reductionY;
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

            // Definit la zone de memoire a verrouiller, en l'occurence, toute l'image
            // (et ouais, je ne suis pas un mec delicat)
            Rectangle rect = new Rectangle(0, 0, image.Width, image.Height);

            // Verrouillage memoire de image : on ne fait que lire dedans
            System.Drawing.Imaging.BitmapData imageData =
                image.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadOnly,
                image.PixelFormat);

            // Verrouillage memoire de filteredImage : on ne fait qu'ecrire dedans
            System.Drawing.Imaging.BitmapData filteredImageData =
                filteredImage.LockBits(rect, System.Drawing.Imaging.ImageLockMode.WriteOnly,
                image.PixelFormat);

            // Pointeur vers le debut des images
            IntPtr ptrImage = imageData.Scan0;
            IntPtr ptrFilteredImage = filteredImageData.Scan0;

            // Declaration des tableaux qui contiendront les valeurs des bitmaps
            int bytes = Math.Abs(imageData.Stride) * image.Height;
            byte[] imageRgbValues = new byte[bytes];
            byte[] filteredImageRgbValues = new byte[bytes];

            // Copie des valeurs RGB de image dans le tableau correspondant
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, imageRgbValues, 0, bytes);

            // Application du filtre  
            for (int i = 0; i < filteredImageRgbValues.Length; i++)
            {
                // Reduction avec 1% de securite (pour les filtres suivants)
                if (i % imageData.Stride > imageData.Stride * (reductionX - 1) / 100 &&
                    i % imageData.Stride < (100 - reductionX + 1) * imageData.Stride / 100 &&
                    i / imageData.Stride > imageData.Height * (reductionY -1) / 100 &&
                    i / imageData.Stride < (100 - reductionY + 1) * imageData.Height / 100
                    )
                {
                    filteredImageRgbValues[i] = applyFilter(imageRgbValues, i, imageData.Stride);
                }
            }

            // Copie des valeurs RGB dans filteredImage
            System.Runtime.InteropServices.Marshal.Copy(filteredImageRgbValues, 0, ptrFilteredImage, bytes);

            // Deverrouillage de la memoire
            image.UnlockBits(imageData);
            filteredImage.UnlockBits(filteredImageData);

            return filteredImage;
        }

        private byte applyFilter(byte[] image, int pos, int stride)
        {
            double value = 0;
            int posTreatment = 0;
            int delta = 0;
/* Le centre du filtre par rapport à l'image doit faire correspondre (xCenter,yCenter) avec pos.
 * Les coordonnées des points du filtre (pour la couleur consideree) sont donc repérés par :
 * posTreatment = pos - stride*(j-yCenter) + 4*(i-xCenter)
 * Il est nécessaire pour le calcul de chaque point du filtre de vérifier qu'on n'essaie de lire un pixel hors de l'image.
 */
            for (int j = 0; j < size; j++)
            {
                for (int i = 0; i < size; i++)
                {
                    posTreatment = pos - stride * (j - yCenter) + 4 * (i - xCenter);
                    if(posTreatment < 0 || posTreatment >= image.Length) continue; // Pixel hors image
                    delta = Math.Abs((posTreatment % stride) - (pos % stride));
                    if (delta > Math.Abs(i-xCenter)*4) continue; // Pixel non adjacent
                    value += image[posTreatment] * convolutionMatrix[i,j];
                }
            }
            if (value < 0) value = 0; else if (value > 255) value = 255;

            return (byte)value;
        }
    }
}
