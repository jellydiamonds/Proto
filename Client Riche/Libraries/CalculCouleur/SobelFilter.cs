using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    class SobelFilter:IFilter
    {

        int[,] sobelHMatrix = { { 0, -1, -2, -1, 0 },
                                { -1, -2, -6, -2, -1 },
                                { 0, 0, 0, 0, 0 },
                                { 1, 2, 6, 2, 1 },
                                { 0, 1, 2, 1, 0 }};

        int[,] sobelVMatrix = { { 0, -1, 0, 1, 0 },
                                { -1, -2, 0, 2, 1 },
                                { -2, -6, 0, 6, 2 },  
                                { -1, -2, 0, 2, 1 },
                                { 0, -1, 0, 1, 0 }};
        
        //Reduction correspond à la portion (en pourcentage) sur les bords qui n'est pas prise en compte
        private int reductionX;
        private int reductionY;

        public SobelFilter(int reductionX, int reductionY)
        {
            this.reductionX = reductionX;
            this.reductionY = reductionY;
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
                if (i % imageData.Stride > imageData.Stride * (reductionX+1) / 100 &&
                    i % imageData.Stride < (100-reductionX-1) * imageData.Stride / 100 &&
                    i / imageData.Stride > imageData.Height * (reductionY+1) / 100 &&
                    i / imageData.Stride < (100-reductionY-1) * imageData.Height / 100
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
            int valueH = 0;
            int valueV = 0;
            int posTreatment = 0;
            int delta = 0;
/* Le centre du filtre par rapport à l'image doit faire correspondre (xCenter,yCenter) avec pos.
 * Les coordonnées des points du filtre (pour la couleur consideree) sont donc repérés par :
 * posTreatment = pos - stride*(j-yCenter) + 4*(i-xCenter)
 * Il est nécessaire pour le calcul de chaque point du filtre de vérifier qu'on n'essaie de lire un pixel hors de l'image.
 */
            for (int j = 0; j < 5; j++)
            {
                for (int i = 0; i < 5; i++)
                {
                    posTreatment = pos - stride * (j - 2) + 4 * (i - 2);
                    if(posTreatment < 0 || posTreatment >= image.Length) continue; // Pixel hors image
                    delta = Math.Abs((posTreatment % stride) - (pos % stride));
                    if (delta > Math.Abs(i-2)*4) continue; // Pixel non adjacent

                    valueH += image[posTreatment] * sobelHMatrix[i,j];
                    valueV += image[posTreatment] * sobelVMatrix[i,j];
                }
            }

            double value = Math.Sqrt(valueH * valueH + valueV * valueV);

            if (value < 0) value = 0; else if (value > 255) value = 255;

            return (byte)value;
        }
    }
}
