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
        private int reduction;

        public ColorComputer(int reduction)
        {
            this.reduction = reduction;
        }

        public Dictionary<int,int> doCompute(Bitmap image)
        {
            Dictionary<int, int> colorProportion = new Dictionary<int,int>();
            int[,] pixelByZone = new int[image.Width,image.Height];
            HashSet<Point> pointToBeTreated = new HashSet<Point>();

            // Au commencement, on doit traiter tous les points
            for (int j = (reduction * image.Height) / 100; j < ((100 - reduction) * image.Height) / 100; j++)
            {
                for (int i = (reduction * image.Width) / 100; i < ((100 - reduction) * image.Width) / 100; i++)
                {
                    pointToBeTreated.Add(new Point(i, j));
                }
            }

            // Definit la zone de memoire a verrouiller, en l'occurence, toute l'image
            // (et ouais, je ne suis pas un mec delicat)
            Rectangle rect = new Rectangle(0, 0, image.Width, image.Height);

            // Verrouillage memoire de image : on ne fait que lire dedans
            System.Drawing.Imaging.BitmapData imageData =
                image.LockBits(rect, System.Drawing.Imaging.ImageLockMode.ReadOnly,
                image.PixelFormat);

            // Pointeur vers le debut des images
            IntPtr ptrImage = imageData.Scan0;

            // Declaration des tableaux qui contiendront les valeurs des bitmaps
            int bytes = Math.Abs(imageData.Stride) * image.Height;
            byte[] imageRgbValues = new byte[bytes];

            // Copie des valeurs RGB de image dans le tableau correspondant
            System.Runtime.InteropServices.Marshal.Copy(ptrImage, imageRgbValues, 0, bytes);

            // Traitement (recursif)
            ComputeZone(ref pointToBeTreated,
                        ref pixelByZone,
                        ref imageRgbValues,
                        image.Width,
                        image.Height,
                        imageData.Stride,
                        1);

            int temp;
            int color;
            int R;
            int G;
            int B;
            HashSet<int> myKeys = new HashSet<int>();

            for (int j = (reduction * image.Height) / 100; j < ((100 - reduction) * image.Height) / 100; j++)
            {
                for (int i = (reduction * image.Width); i < ((100 - reduction) * image.Width) / 100; i++)
                {
                    // Si la zone du pixel est -1, c'est une frontiere, on passe a la suite
                    if (pixelByZone[i,j] == -1) continue;

                    // On definit la valeur de la couleur (0x00RRGGBB)
                    R = imageRgbValues[(i * 4) + (j * imageData.Stride) + 2];
                    G = imageRgbValues[(i * 4) + (j * imageData.Stride) + 1];
                    B = imageRgbValues[(i * 4) + (j * imageData.Stride)];
                    color = (R << 16) + (G << 8) + B;

                    // Si la valeur de la zone existe, on lui ajoute 1, sinon on cree le couple cle/valeur (avec valeur==1)
                    if (colorProportion.TryGetValue(pixelByZone[i, j], out temp))
                    {
                        colorProportion[pixelByZone[i, j]] += color;
                    }
                    else
                    {
                        myKeys.Add(pixelByZone[i, j]);
                        colorProportion.Add(pixelByZone[i, j], color);
                    }
                }
            }

            // On multiplie par 100 et divise par le nombre total de pixels dans la zone reduite pour avoir les proportions
            foreach (var key in myKeys)
            {
                colorProportion[key] = (colorProportion[key] * 100) / (image.Height * image.Width * (100 - 2 * reduction) * (100 - 2 * reduction) / 10000);
            }

            // Deverrouillage de la memoire
            image.UnlockBits(imageData);

            return colorProportion;
        }


        private void ComputeZone(ref HashSet<Point> pointToBeTreated, 
                                 ref int[,] pixelByZone, 
                                 ref byte[] imageRgbValues, 
                                 int width, 
                                 int height, 
                                 int stride,
                                 int actualZone)
        {
            if (pointToBeTreated.Count > 0)
            {
                HashSet<Point> pointSeen = new HashSet<Point>();
                Point[] firstPoint = { new Point(0, 0) };
                pointToBeTreated.CopyTo(firstPoint, 0, 1);
                pointToBeTreated.Remove(firstPoint[0]);

                // Tant que le premier point de la liste est une frontiere, on la marque comme frontiere
                // Et on passe a la suite
                while (imageRgbValues[(firstPoint[0].X * 4) + (firstPoint[0].Y * stride)] == 255 &&
                       imageRgbValues[(firstPoint[0].X * 4) + (firstPoint[0].Y * stride) + 1] == 0 &&
                       imageRgbValues[(firstPoint[0].X * 4) + (firstPoint[0].Y * stride) + 2] == 255)
                {
                    pixelByZone[firstPoint[0].X, firstPoint[0].Y] = -1;

                    // On sait jamais, ca pourrait etre le dernier point a traiter
                    if (pointToBeTreated.Count == 0) return;
                    pointToBeTreated.CopyTo(firstPoint, 0, 1);
                    pointToBeTreated.Remove(firstPoint[0]);
                }

                pixelByZone[firstPoint[0].X, firstPoint[0].Y] = actualZone;

                // Si les voisins n'ont pas été traités, on les retire des points a traiter et ajoute aux points vus
                AddNeighbor(ref pointToBeTreated,
                            ref pointSeen, 
                            ref pixelByZone,
                            ref imageRgbValues,
                            ref firstPoint[0],
                            width,
                            height,
                            stride);

                // On traite tous les voisins, et les voisins des voisins...
                while (pointSeen.Count > 0)
                {
                    pointSeen.CopyTo(firstPoint, 0, 1);
                    pointSeen.Remove(firstPoint[0]);
                    AddNeighbor(ref pointToBeTreated,
                                ref pointSeen,
                                ref pixelByZone,
                                ref imageRgbValues,
                                ref firstPoint[0],
                                width,
                                height,
                                stride);
                }

                // Tous les points de la zone ont ete traite, on passe a la zone suivante
                ComputeZone(ref pointToBeTreated,
                        ref pixelByZone,
                        ref imageRgbValues,
                        width,
                        height,
                        stride,
                        actualZone+1);
            }
        }

        private void AddNeighbor(ref HashSet<Point> pointToBeTreated,
                                 ref HashSet<Point> pointSeen, 
                                 ref int[,] pixelByZone, 
                                 ref byte[] imageRgbValues, 
                                 ref Point point, 
                                 int width, 
                                 int height,
                                 int stride)
        {
            Point p;
            // Voisin de gauche
            if (point.X > (reduction * width) / 100)
            {
                p = new Point(point.X-1,point.Y);
                markPoint(ref pointToBeTreated, 
                          ref pointSeen, 
                          ref pixelByZone, 
                          ref imageRgbValues, 
                          ref point,
                          ref p, 
                          stride);
            }

            // Voisin de droite
            if (point.X < (((100 - reduction) * width) / 100) - 1)
            {
                p = new Point(point.X + 1, point.Y);
                markPoint(ref pointToBeTreated,
                          ref pointSeen,
                          ref pixelByZone,
                          ref imageRgbValues,
                          ref point,
                          ref p,
                          stride);
            }

            // Voisin du haut
            if (point.Y > (reduction * height) / 100)
            {
                p = new Point(point.X, point.Y - 1);
                markPoint(ref pointToBeTreated,
                          ref pointSeen,
                          ref pixelByZone,
                          ref imageRgbValues,
                          ref point,
                          ref p,
                          stride);
            }

            // Voisin du bas
            if (point.Y < (((100 - reduction) * height) / 100) - 1)
            {
                p = new Point(point.X, point.Y + 1);
                markPoint(ref pointToBeTreated,
                          ref pointSeen,
                          ref pixelByZone,
                          ref imageRgbValues,
                          ref point,
                          ref p,
                          stride);
            }
        }

        private void markPoint(ref HashSet<Point> pointToBeTreated,
                               ref HashSet<Point> pointSeen, 
                               ref int[,] pixelByZone, 
                               ref byte[] imageRgbValues,
                               ref Point point,
                               ref Point p,
                               int stride)
        {
            // Si le point p n'a pas été traité ou vu
            if (pointToBeTreated.Contains(p))
            {
                // Si c'est une frontiere, on le marque comme tel et le point est traite
                if (imageRgbValues[(p.X * 4) + (p.Y * stride)] == 255 &&
                    imageRgbValues[(p.X * 4) + (p.Y * stride) + 1] == 0 &&
                    imageRgbValues[(p.X * 4) + (p.Y * stride) + 2] == 255)
                {
                    pixelByZone[p.X, p.Y] = -1;
                    pointToBeTreated.Remove(p);
                }
                // Sinon, on le marque de la meme maniere que le point considere  
                // On l'ajoute aux points vus pour pouvoir ensuite traiter ses voisins
                else
                {
                    pixelByZone[p.X, p.Y] = pixelByZone[point.X, point.Y];
                    pointToBeTreated.Remove(p);
                    pointSeen.Add(p);
                }
            }
        }
    }
}