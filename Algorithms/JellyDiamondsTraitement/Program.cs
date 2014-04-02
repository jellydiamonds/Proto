using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length != 2)
            {
                Console.WriteLine(@"ERREUR : Nombre d'arguments incorrect");
                Console.WriteLine(@"         Mettre le chemin de l'image source et le chemin de l'image de destination");
            }
            else
            {
                try
                {
                    var imageSource = new Bitmap(args[0]);

                    double[,] gaussianBlurryMatrix = {{3.0/33.0,4.0/33.0,3.0/33.0},
                                                      {4.0/33.0,5.0/33.0,4.0/33.0},
                                                      {3.0/33.0,4.0/33.0,3.0/33.0}};

                    var blurryFilter = new ImageFilter(3, 1, 1, gaussianBlurryMatrix);
                    var contourFilter = new SobelFilter();
                    var fusionImage = new FusionImage();

                    Console.WriteLine("Traitement (1/3) : Reduction du bruit");
                    Bitmap blurryImage = blurryFilter.doFilter(imageSource);

                    Console.WriteLine("Traitement (2/3) : Calcul des contours");
                    Bitmap contourImage = contourFilter.doFilter(blurryImage);

                    Console.WriteLine("Traitement (3/3) : Fusion des images");
                    Bitmap imageDestination = fusionImage.doContour(imageSource, contourImage);

                    imageDestination.Save(args[1], System.Drawing.Imaging.ImageFormat.Jpeg);
                }
                catch (SystemException e)
                {
                    Console.WriteLine(@"ERREUR : " + e.Message);
                    Console.ReadLine();
                }
            }
        }
    }
}
