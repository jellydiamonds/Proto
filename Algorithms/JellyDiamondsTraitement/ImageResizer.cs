using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    class ImageResizer:IFilter
    {
        private byte threshold;

        public ImageResizer(byte threshold)
        {
            // TODO: Complete member initialization
            this.threshold = threshold;
        }

        public Bitmap doFilter(Bitmap image)
        {
            int[] boundary = computeSize(image);
/* boundary represente les coordonnees des bords de l'objet (en supposant qu'il n'y ait qu'un seul objet).
 * boundary[0] -> top
 * boundary[1] -> bottom
 * boundary[2] -> left
 * boundary[3] -> right
 */
            int width = boundary[3] - boundary[2];
            int height = boundary[1] - boundary[0];

            Bitmap resizedImage = new Bitmap(width,height);

            Rectangle rectImage = new Rectangle(boundary[2], boundary[0], width, height);

            Graphics g = Graphics.FromImage(resizedImage);
            g.DrawImage(image, 0, 0, rectImage, GraphicsUnit.Pixel);
            g.Dispose();

            return resizedImage;
        }

        private float differentatePixel(byte[] image,int i,int stride)
        {
// On va chercher la valeur des pixels adjacents (si pas de pixel adjacent, on prend celle du pixel traite)
            byte previousXB = image[i];     if (i - 4 > 0 && (i - 4) / stride == i / stride) previousXB = image[i - 4];
            byte previousXG = image[i + 1]; if (i - 3 > 0 && (i - 3) / stride == (i + 1) / stride) previousXG = image[i - 3];
            byte previousXR = image[i + 2]; if (i - 2 > 0 && (i - 2) / stride == (i + 2) / stride) previousXR = image[i - 2];

            byte nextXB = image[i];     if (i + 4 < image.Length && (i + 4) / stride == i / stride) nextXB = image[i + 4];
            byte nextXG = image[i + 1]; if (i + 5 < image.Length && (i + 5) / stride == (i + 1) / stride) previousXG = image[i + 5];
            byte nextXR = image[i + 2]; if (i + 6 < image.Length && (i + 6) / stride == (i + 2) / stride) previousXR = image[i + 6];

            byte previousYB = image[i]; if (i - stride > 0) previousXB = image[i - stride];
            byte previousYG = image[i + 1]; if (i + 1 - stride > 0) previousXG = image[i + 1 - stride];
            byte previousYR = image[i + 2]; if (i + 2 - stride > 0) previousXR = image[i + 2 - stride];

            byte nextYB = image[i]; if (i + stride < image.Length) previousXB = image[i + stride];
            byte nextYG = image[i + 1]; if (i + 1 + stride < image.Length) previousXG = image[i + 1 + stride];
            byte nextYR = image[i + 2]; if (i + 2 + stride < image.Length) previousXR = image[i + 2 + stride]; 


            byte dxR = (byte)Math.Abs(nextXR - previousXR);
            byte dyR = (byte)Math.Abs(nextYR - previousYR);
            byte dxG = (byte)Math.Abs(nextXG - previousXG);
            byte dyG = (byte)Math.Abs(nextYG - previousYG);
            byte dxB = (byte)Math.Abs(nextXB - previousXB);
            byte dyB = (byte)Math.Abs(nextYB - previousYB);

/* C'est degueulasse, mais ca fait le maximum des 6 valeurs absolues des differentes approximations de derivees */
            return Math.Max(Math.Max(Math.Max(dxR, dyR), Math.Max(dxG, dyG)), Math.Max(dxB, dyB));
        }


        private int[] computeSize(Bitmap image)
        {
/* boundary represente les coordonnees des bords de l'objet (en supposant qu'il n'y ait qu'un seul objet).
 * boundary[0] -> top
 * boundary[1] -> bottom
 * boundary[2] -> left
 * boundary[3] -> right
 */
            int[] boundary = {image.Height,0,image.Width,0};
            float derivateNumber;

            // Definit la zone de memoire a verrouiller, en l'occurence, toute l'image
            // (et ouais, je ne suis pas un mec delicat)
            Rectangle rect = new Rectangle(0, 0, image.Width, image.Height);

            // Verrouillage memoire de image : on ne fait que lire dedans
            System.Drawing.Imaging.BitmapData imageData =
                image.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadOnly,
                image.PixelFormat);

            // Pointeur vers le debut de l'image
            IntPtr ptrImage = imageData.Scan0;

            // Declaration des tableaux qui contiendront les valeurs des bitmaps
            int bytes = Math.Abs(imageData.Stride) * image.Height;
            byte[] imageRgbValues = new byte[bytes];

            // Copie des valeurs RGB de image dans le tableau correspondant
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, imageRgbValues, 0, bytes);

            // Application du filtre  
            for (int i = 0; i < imageRgbValues.Length; i += 4)
            {
                derivateNumber = differentatePixel(imageRgbValues, i, imageData.Stride);

                if (derivateNumber > threshold)
                {
                    /* Pour le top : on compare avec boundary[0] */
                    if (boundary[0] > i / imageData.Stride) boundary[0] = i / imageData.Stride;
                    /* Pour le bottom : on compare avec boundary[1] */
                    if (boundary[1] < i / imageData.Stride) boundary[1] = i / imageData.Stride;
                    /* Pour le left : on compare avec boundary[2] */
                    if (boundary[2] > (i % imageData.Stride) / 4) boundary[2] = (i % imageData.Stride) / 4;
                    /* Pour le right : on compare avec boundary[3] */
                    if (boundary[3] < (i % imageData.Stride) / 4) boundary[3] = (i % imageData.Stride) / 4;
                }
            }
    
            // Deverrouillage de la memoire
            image.UnlockBits(imageData);

            return boundary;
        }
    }
}
