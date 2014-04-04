using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    class ColorComputer
    {
        //Reduction correspond à la portion (en pourcentage) sur les bords qui n'est pas prise en compte
        private int reductionX;
        private int reductionY;

        public ColorComputer(int reductionX,int reductionY)
        {
            this.reductionX = reductionX;
            this.reductionY = reductionY;
        }

        public int doCompute(Bitmap image)
        {
            // Definit la zone de memoire a verrouiller, en l'occurence, toute l'image
            // (et ouais, je ne suis pas un mec delicat)
            Rectangle rect = new Rectangle(0, 0, image.Width, image.Height);

            // Verrouillage memoire de image : on ne fait que lire dedans
            System.Drawing.Imaging.BitmapData imageData =
                image.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadOnly,
                image.PixelFormat);

            // Pointeur vers le debut des images
            IntPtr ptrImage = imageData.Scan0;

            // Declaration du tableau qui contiendra les valeurs du bitmap, et des constantes width, height, stride
            int width = image.Width;
            int height = image.Height;
            int strideCoeff = imageData.Stride / width;
            int bytes = Math.Abs(strideCoeff * width) * height;
            byte[] imageRgbValues = new byte[bytes];
            byte[] leftrightff00ff = new byte[bytes];
            byte[] updownff00ff = new byte[bytes];

            // Copie des valeurs RGB de image dans le tableau correspondant
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, imageRgbValues, 0, bytes);
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, leftrightff00ff, 0, bytes);
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, updownff00ff, 0, bytes);

            // Transformation du fond travaille en #FF00FF (couleur ignoree)
            // D'abord horizontalement
            bool leftBorder;
            bool rightBorder;
            for (int j = reductionY * height / 100; j < (100 - reductionY) * height / 100; j++)
            {
                leftBorder = rightBorder = false;
                for (int i = reductionX * width / 100; i < (100 - reductionX) * width / 100 && !(leftBorder && rightBorder); i++)
                {
                    if (!leftBorder)
                    {
                        if (leftrightff00ff[strideCoeff * (j * width + i)] == 255 &&
                            leftrightff00ff[strideCoeff * (j * width + i) + 1] == 0 &&
                            leftrightff00ff[strideCoeff * (j * width + i) + 2] == 255) leftBorder = true;
                        leftrightff00ff[strideCoeff * (j * width + i)] = 255;
                        leftrightff00ff[strideCoeff * (j * width + i) + 1] = 0;
                        leftrightff00ff[strideCoeff * (j * width + i) + 2] = 255;
                    }
                    if (!rightBorder)
                    {
                        if (leftrightff00ff[strideCoeff * (j * width - i)] == 255 &&
                            leftrightff00ff[strideCoeff * (j * width - i) + 1] == 0 &&
                            leftrightff00ff[strideCoeff * (j * width - i) + 2] == 255) rightBorder = true;
                        leftrightff00ff[strideCoeff * (j * width - i)] = 255;
                        leftrightff00ff[strideCoeff * (j * width - i) + 1] = 0;
                        leftrightff00ff[strideCoeff * (j * width - i) + 2] = 255;
                    }
                }
            }

            // Puis verticalement
            bool topBorder;
            bool bottomBorder;
            for (int i = reductionX * width / 100; i < (100 - reductionX) * width / 100; i++)
            {
                topBorder = bottomBorder = false;
                for (int j = reductionY * height / 100; j < (100 - reductionY) * height / 100 && !(topBorder && bottomBorder); j++)
                {
                    if (!topBorder)
                    {
                        if (updownff00ff[strideCoeff * (j * width + i)] == 255 &&
                            updownff00ff[strideCoeff * (j * width + i) + 1] == 0 &&
                            updownff00ff[strideCoeff * (j * width + i) + 2] == 255) topBorder = true;
                        updownff00ff[strideCoeff * (j * width + i)] = 255;
                        updownff00ff[strideCoeff * (j * width + i) + 1] = 0;
                        updownff00ff[strideCoeff * (j * width + i) + 2] = 255;
                    }
                    if (!bottomBorder)
                    {
                        if (updownff00ff[strideCoeff * ((height - j) * width + i)] == 255 &&
                            updownff00ff[strideCoeff * ((height - j) * width + i) + 1] == 0 &&
                            updownff00ff[strideCoeff * ((height - j) * width + i) + 2] == 255) bottomBorder = true;
                        updownff00ff[strideCoeff * ((height - j) * width + i)] = 255;
                        updownff00ff[strideCoeff * ((height - j) * width + i) + 1] = 0;
                        updownff00ff[strideCoeff * ((height - j) * width + i) + 2] = 255;
                    }
                }
            }

            // Fusion des images
            for (int j = reductionY * height / 100; j < (100 - reductionY) * height / 100; j++)
            {
                leftBorder = rightBorder = false;
                for (int i = reductionX * width / 100; i < (100 - reductionX) * width / 100; i += 4)
                {
                    if ((updownff00ff[strideCoeff * (j * width + i)] == 255 &&
                        updownff00ff[strideCoeff * (j * width + i) + 1] == 0 &&
                        updownff00ff[strideCoeff * (j * width + i) + 2] == 255) ||
                        (leftrightff00ff[strideCoeff * (j * width + i)] == 255 &&
                        leftrightff00ff[strideCoeff * (j * width + i) + 1] == 0 &&
                        leftrightff00ff[strideCoeff * (j * width + i) + 2] == 255))
                    {
                        imageRgbValues[strideCoeff * (j * width + i)] = 255;
                        imageRgbValues[strideCoeff * (j * width + i) + 1] = 0;
                        imageRgbValues[strideCoeff * (j * width + i) + 2] = 255;
                    }
                }
            }

            // Copie des valeurs RGB dans filteredImage
            System.Runtime.InteropServices.Marshal.Copy(imageRgbValues, 0, ptrImage, bytes);

            // Deverrouillage de l'image en memoire, on a recupere le tableau de pixels, ca suffira pour faire les calculs
            image.UnlockBits(imageData);

            // Calcul de la moyenne de couleur
            uint R = 0;
            uint G = 0;
            uint B = 0;
            uint counter = 0;
            for (int j = reductionY * height / 100; j < (100 - reductionY) * height / 100; j++)
            {
                leftBorder = rightBorder = false;
                for (int i = reductionX * width / 100; i < (100 - reductionY) * width / 100; i++)
                {
                    if (!(imageRgbValues[strideCoeff * (j * width + i)] == 255 &&
                            imageRgbValues[strideCoeff * (j * width + i) + 1] == 0 &&
                            imageRgbValues[strideCoeff * (j * width + i) + 2] == 255))
                    {
                        // Pour forcer une saturation minimale
                        if (Math.Max(Math.Max(imageRgbValues[strideCoeff * (j * width + i)],
                            imageRgbValues[strideCoeff * (j * width + i) + 1]),
                            imageRgbValues[strideCoeff * (j * width + i) + 2])-
                           Math.Min(Math.Min(imageRgbValues[strideCoeff * (j * width + i)],
                           imageRgbValues[strideCoeff * (j * width + i) + 1]),
                           imageRgbValues[strideCoeff * (j * width + i) + 2])> 25)
                        {
                            B += imageRgbValues[strideCoeff * (j * width + i)];
                            G += imageRgbValues[strideCoeff * (j * width + i) + 1];
                            R += imageRgbValues[strideCoeff * (j * width + i) + 2];
                            counter++;
                        }
                    }
                }
            }

            int color = -1;
            if (counter > 0) color = (int)((R / counter) * 0x10000 + (G / counter) * 0x100 + (B / counter));
            return color;
        }
    }
}