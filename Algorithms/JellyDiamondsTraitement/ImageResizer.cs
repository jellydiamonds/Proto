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
        private float threshold;

        public ImageResizer(float threshold)
        {
            // TODO: Complete member initialization
            this.threshold = threshold;
        }

        public Bitmap doFilter(Bitmap image)
        {
            int[] boundary = computeSize(image);

/* Le "-2" permet de reduire les bords de 1 pixel de chaque coté, comme les bords ne nous interessent pas. */
            int width = boundary[3] - boundary[2] - 2;
            int height = boundary[1] - boundary[0] - 2;

            Bitmap resizedImage = new Bitmap(width,height);
            for (int j = 0; j < height; j++)
            {
                for (int i = 0; i < width; i++)
                {
                    resizedImage.SetPixel(i, j, image.GetPixel(i+boundary[2]+1,j+boundary[0]+1));
                }
            }

            return resizedImage;
        }

        private float differentatePixel(Bitmap image,int i,int j)
        {
/* Pour ignorer les bords de l'image, ils posent probleme et n'apportent pas d'information */
            if (i < 3 || j < 3 || i > image.Width - 4 || j > image.Height - 4) return 0;

            Color previousXPixel = image.GetPixel(i-1,j);
            Color nextXPixel =  image.GetPixel(i+1,j);
            Color previousYPixel =  image.GetPixel(i,j-1);
            Color nextYPixel =  image.GetPixel(i,j+1);

            float dxH = nextXPixel.GetHue() - previousXPixel.GetHue();
            float dyH = nextXPixel.GetHue() - previousXPixel.GetHue();
            float dxS = nextXPixel.GetSaturation() - previousXPixel.GetSaturation();
            float dyS = nextXPixel.GetSaturation() - previousXPixel.GetSaturation();
            float dxB = nextXPixel.GetBrightness() - previousXPixel.GetBrightness();
            float dyB = nextXPixel.GetBrightness() - previousXPixel.GetBrightness();

/* C'est degueulasse, mais ca fait le maximum des 6 valeurs absolues des differentes approximations de derivees */
            return Math.Max(Math.Abs(dxH*dxS*dxB),Math.Abs(dyH*dyS*dyB));
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
            for (int j = 0; j < image.Height; j++)
            {
                for (int i = 0; i < image.Width; i++)
                {
                    derivateNumber = differentatePixel(image, i, j);

                    if (derivateNumber > threshold)
                    {
/* Pour le top : on compare avec boundary[0] */
                        if (boundary[0] > j) boundary[0] = j;
/* Pour le bottom : on compare avec boundary[1] */
                        if (boundary[1] < j) boundary[1] = j;
/* Pour le left : on compare avec boundary[2] */
                        if (boundary[2] > i) boundary[2] = i;
/* Pour le right : on compare avec boundary[3] */
                        if (boundary[3] < i) boundary[3] = i;
                    }
                }
            }
            return boundary;
        }
    }
}
