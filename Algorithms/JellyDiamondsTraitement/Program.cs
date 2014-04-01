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

                    var imageResizer = new ImageResizer(0.068f);
                    double[,] blurryMatrix = {{1.0/9.0,1.0/9.0,1.0/9.0},{1.0/9.0,1.0/9.0,1.0/9.0},{1.0/9.0,1.0/9.0,1.0/9.0}};
                    double[,] laplaceMatrix = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
                    var blurryFilter = new ImageFilter(3,1,1,blurryMatrix);
                    var contourFilter = new ImageFilter(3, 1, 1, laplaceMatrix);
                    var fusionImage = new FusionImage();
                    var reductionColorFilter = new ReductionCouleur();

                    Console.WriteLine("Traitement (1/3) : Redimensionnement de l'image");
                    Bitmap imageResized = imageResizer.doFilter(imageSource);
                    Console.WriteLine("Traitement (2/3) : Creation de l'image de contour");
                    Bitmap imageFiltered = contourFilter.doFilter(reductionColorFilter.doFilter(blurryFilter.doFilter(imageResized)));
                    Console.WriteLine("Traitement (3/3) : Fusion des images");
                    Bitmap imageDestination = fusionImage.doContour(imageResized, imageFiltered);
                    imageDestination.Save(args[1], System.Drawing.Imaging.ImageFormat.Jpeg);
                }
                catch (SystemException e)
                {
                    Console.WriteLine(@"ERREUR : " + e.Message);
                }
            }
        }
    }
}
