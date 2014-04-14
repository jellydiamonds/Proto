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
            try
            {
                bool verbose = false;
                int nbImage = 0;
                string imgSource = null;
                string imgDest = null;
                int reductionX = 1;
                int reductionY = 1;

                foreach (var s in args)
                {
                    // Option verbose pour les affichages
                    if (s.Equals("-v") || s.Equals("-V")) verbose = true;

                    // Acquisition de la deuxieme image : destination
                    if ((s.EndsWith(".jpg", StringComparison.InvariantCultureIgnoreCase)
                        || s.EndsWith(".jpeg", StringComparison.InvariantCultureIgnoreCase)) && nbImage == 1)
                    {
                        imgDest = s;
                        nbImage++;
                    }

                    // Acquisition de la premiere image : source
                    if ((s.EndsWith(".jpg", StringComparison.InvariantCultureIgnoreCase)
                        || s.EndsWith(".jpeg", StringComparison.InvariantCultureIgnoreCase)) && nbImage == 0)
                    {
                        imgSource = s;
                        nbImage++;
                    }

                    // Reduction en X
                    if (s.StartsWith("-x:") || s.StartsWith("-X:"))
                    {
                        char[] separator = { ':' };
                        string[] separatedS = s.Split(separator);
                        reductionX = int.Parse(separatedS[1]);
                        if (reductionX < 1 || reductionX >= 50) reductionX = 1;
                    }

                    // Reduction en Y
                    if (s.StartsWith("-y:") || s.StartsWith("-Y:"))
                    {
                        char[] separator = { ':' };
                        string[] separatedS = s.Split(separator);
                        reductionY = int.Parse(separatedS[1]);
                        if (reductionY < 1 || reductionY >= 50) reductionY = 1;
                    }
                }

                if (nbImage == 0)
                {
                    throw new Exception("Pas d'image format JPG en entree");
                }

                var imageSource = new Bitmap(imgSource);

                double[,] gaussianBlurryMatrix = {{1.0/16.0,2.0/16.0,1.0/16.0},
                                                    {2.0/16.0,4.0/16.0,2.0/16.0},
                                                    {1.0/16.0,2.0/16.0,1.0/16.0}};

                var blurryFilter = new ImageFilter(3, 1, 1, gaussianBlurryMatrix, reductionX, reductionY);
                var contourFilter = new SobelFilter(reductionX, reductionY);
                var fusionImage = new FusionImage();

                int height = imageSource.Height;
                int width = imageSource.Width;

                if (verbose) Console.WriteLine("Traitement (1/4) : Reduction du bruit");
                Bitmap blurryImage = blurryFilter.doFilter(imageSource);

                if (verbose) Console.WriteLine("Traitement (2/4) : Calcul des contours");
                Bitmap contourImage = contourFilter.doFilter(blurryImage);

                if (verbose) Console.WriteLine("Traitement (3/4) : Fusion des images");
                Bitmap imageDestination = fusionImage.doFusion(imageSource, contourImage);

                if (verbose) Console.WriteLine("Traitement (4/4) : Calcul de la couleur de la pierre");
                ColorComputer calculCouleur = new ColorComputer(reductionX, reductionY);
                int couleur = calculCouleur.doCompute(imageDestination);

                if (couleur < 0x100000) Console.WriteLine("0{0:X}", couleur);
                else Console.WriteLine("{0:X}", couleur);

                if (nbImage == 2) imageDestination.Save(imgDest, System.Drawing.Imaging.ImageFormat.Jpeg);

                return 0;
            }
            catch (Exception e)
            {
                Console.WriteLine(@"ERREUR : " + e.Message);
                return -1;
            }
        }
    }
}
