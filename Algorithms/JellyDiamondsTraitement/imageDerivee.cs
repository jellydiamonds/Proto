using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    public class FusionImage
    {
        public Bitmap doContour(Bitmap imageOriginal, Bitmap imageContour)
        {
            Bitmap imageWithContour = new Bitmap(imageOriginal);
            Color pixel;

            for (int j = 0; j < imageOriginal.Height; j++)
            {
                for (int i = 0; i < imageOriginal.Width; i++)
                {
                    pixel = imageContour.GetPixel(i, j);
                    // On considere qu'il s'agit d'un bord si c'est un gris clair
                    if (pixel.R > 20 && pixel.G > 20 && pixel.B > 20)
                    {
                        imageWithContour.SetPixel(i, j, Color.FromArgb(255, 0, 255));
                    }
                }
            }

            return imageWithContour;
        }
    }
}
