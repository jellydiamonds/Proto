using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    public class FusionImage
    {
        public Bitmap doFusion(Bitmap imageOriginal, Bitmap imageContour)
        {
            Bitmap imageWithContour = new Bitmap(imageOriginal);

            // Definit la zone de memoire a verrouiller, en l'occurence, toute l'image
            // (et ouais, je ne suis pas un mec delicat)
            Rectangle rect = new Rectangle(0, 0, imageOriginal.Width, imageOriginal.Height);

            // Verrouillage memoire de imageContour : on ne fait que lire dedans
            System.Drawing.Imaging.BitmapData imageContourData =
                imageContour.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadOnly,
                imageContour.PixelFormat);

            // Verrouillage memoire de filteredImage : on ne fait qu'ecrire dedans
            System.Drawing.Imaging.BitmapData imageWithContourData =
                imageWithContour.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadWrite,
                imageWithContour.PixelFormat);

            // Pointeur vers le debut des images
            IntPtr ptrImage = imageContourData.Scan0;
            IntPtr ptrFilteredImage = imageWithContourData.Scan0;

            // Declaration des tableaux qui contiendront les valeurs des bitmaps
            int bytes = Math.Abs(imageContourData.Stride) * imageContour.Height;
            byte[] imageRgbValues = new byte[bytes];
            byte[] filteredImageRgbValues = new byte[bytes];

            // Copie des valeurs RGB de image dans le tableau correspondant
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, imageRgbValues, 0, bytes);
            System.Runtime.InteropServices.Marshal.Copy(ptrFilteredImage, filteredImageRgbValues, 0, bytes);

            // Application du filtre  
            for (int i = 0; i < filteredImageRgbValues.Length; i+=4)
            {
                if (imageRgbValues[i] > 96 &&
                    imageRgbValues[i + 1] > 96 &&
                    imageRgbValues[i + 2] > 96)
                {
                    filteredImageRgbValues[i] = 255;
                    filteredImageRgbValues[i+1] = 0;
                    filteredImageRgbValues[i+2] = 255;
                    filteredImageRgbValues[i+3] = 255;
                }
            }

            // Copie des valeurs RGB dans filteredImage
            System.Runtime.InteropServices.Marshal.Copy(filteredImageRgbValues, 0, ptrFilteredImage, bytes);

            // Deverrouillage de la memoire
            imageContour.UnlockBits(imageContourData);
            imageWithContour.UnlockBits(imageWithContourData);

            return imageWithContour;
        }
    }
}
