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
        static int Main(string[] args)
        {
            if (args.Length != 2)
            {
                Console.WriteLine(@"ERREUR : Nombre d'arguments incorrect");
                Console.WriteLine(@"         Mettre le chemin de l'image source et le chemin de l'image de destination");
                return -1;
            }
            else
            {
                try
                {
                    var imageSource = new Bitmap(args[0]);
                    int reductionX = 15;
                    int reductionY = 15;
                    double[,] gaussianBlurryMatrix = {{1.0/16.0,2.0/16.0,1.0/16.0},
                                                      {2.0/16.0,4.0/16.0,2.0/16.0},
                                                      {1.0/16.0,2.0/16.0,1.0/16.0}};

                    var blurryFilter = new ImageFilter(3, 1, 1, gaussianBlurryMatrix, reductionX, reductionY);
                    var contourFilter = new SobelFilter(reductionX,reductionY);
                    var fusionImage = new FusionImage();

                    int height = imageSource.Height;
                    int width = imageSource.Width;

                    Console.WriteLine("Traitement (1/4) : Reduction du bruit");
                    Bitmap blurryImage = blurryFilter.doFilter(imageSource);

                    Console.WriteLine("Traitement (2/4) : Calcul des contours");
                    Bitmap contourImage = contourFilter.doFilter(blurryImage);

                    Console.WriteLine("Traitement (3/4) : Fusion des images");
                    Bitmap imageDestination = fusionImage.doFusion(imageSource, contourImage);

                    imageDestination.Save(args[1], System.Drawing.Imaging.ImageFormat.Jpeg);

                    Console.WriteLine("Traitement (4/4) : Calcul de la couleur de la pierre");
                    ColorComputer calculCouleur = new ColorComputer(reductionX, reductionY);
                    int couleur = calculCouleur.doCompute(imageDestination);

                    if(couleur < 0x100000) Console.WriteLine("0{0:X}", couleur);
                    else Console.WriteLine("{0:X}", couleur);

                    imageDestination.Save(args[1], System.Drawing.Imaging.ImageFormat.Jpeg);

                    return couleur;
                }
                catch (SystemException e)
                {
                    Console.WriteLine(@"ERREUR : " + e.Message);
                    Console.ReadLine();
                    return -1;
                }
            }
        }
    }
}
