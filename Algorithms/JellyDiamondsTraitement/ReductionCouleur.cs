using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    class ReductionCouleur:IFilter
    {
        public Bitmap doFilter(Bitmap image)
        {
            var approxImage = new Bitmap(image);

            for (int j = 0; j < image.Height; j++)
            {
                for (int i = 0; i < image.Width; i++)
                {
                    approxImage.SetPixel(i, j, approximationCouleur(image.GetPixel(i, j)));
                }
            }

            return approxImage;
        }

        Color approximationCouleur(Color couleurOriginale)
        {
            float hue = couleurOriginale.GetHue();
            float brightness = couleurOriginale.GetBrightness();
            float saturation = couleurOriginale.GetSaturation();

            /* Approximation */
            hue = (float)Math.Floor((hue + 2.5) / 5.0) * 5.0f; if (hue >= 360.0f) hue = 0; //petite securite
            brightness = (float)Math.Floor((brightness) * 5.0 + 0.75) / 5.0f; if (brightness > 1) brightness = 1;
            saturation = (float)Math.Floor((saturation) * 5.0 + 0.25) / 5.0f; if (saturation > 1) saturation = 1;

            int ti = (int)Math.Floor(hue / 60.0) % 6;
            float f = hue / 60.0f - ti;
            float l = (brightness * (1.0f - saturation));
            float m = (brightness * (1.0f - saturation * f));
            float n = (brightness * (1.0f - saturation * (1.0f - f)));

            int L = (int)(l*255);
            int M = (int)(m*255);
            int N = (int)(n*255);
            int V = (int)(brightness*255);

            int R=0, G=0, B=0;

            switch (ti)
            {
                case 0: 
                    R = V;
                    G = N;
                    B = L; 
                    break;
                case 1:
                    R = M;
                    G = V;
                    B = L; 
                    break;
                case 2:
                    R = L;
                    G = V;
                    B = N;
                    break;
                case 3:
                    R = L;
                    G = M;
                    B = V; 
                    break;
                case 4:
                    R = N;
                    G = L;
                    B = V;
                    break;
                case 5:
                    R = V;
                    G = L;
                    B = M;
                    break;
            }

            return Color.FromArgb(R,G,B);
        }
    }
}
