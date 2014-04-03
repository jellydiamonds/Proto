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
                    int reduction = 20;

                    double[,] gaussianBlurryMatrix = {{3.0/33.0,4.0/33.0,3.0/33.0},
                                                      {4.0/33.0,5.0/33.0,4.0/33.0},
                                                      {3.0/33.0,4.0/33.0,3.0/33.0}};

                    var blurryFilter = new ImageFilter(3, 1, 1, gaussianBlurryMatrix, reduction);
                    var contourFilter = new SobelFilter(reduction);
                    var fusionImage = new FusionImage();

                    int height = imageSource.Height;
                    int width = imageSource.Width;

                    Console.WriteLine("Traitement (1/3) : Reduction du bruit");
                    Bitmap blurryImage = blurryFilter.doFilter(imageSource);

                    Console.WriteLine("Traitement (2/3) : Calcul des contours");
                    Bitmap contourImage = contourFilter.doFilter(blurryImage);

                    Console.WriteLine("Traitement (3/3) : Fusion des images");
                    Bitmap imageDestination = fusionImage.doFusion(imageSource, contourImage);

                    imageDestination.Save(args[1], System.Drawing.Imaging.ImageFormat.Jpeg);

                   /* Console.WriteLine("Calcul des zones de couleurs");
                    ColorComputer calculCouleur = new ColorComputer(reduction);
                    Dictionary<int,int> proportionCouleur = calculCouleur.doCompute(imageDestination);

                    foreach (var key in proportionCouleur.Keys)
                    {
                        Console.WriteLine("Zone {0} : {1:X}",key,proportionCouleur[key]);
                    }

                    Console.ReadLine();*/
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
