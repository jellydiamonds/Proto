using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace JellyDiamondsTraitement
{
    interface IFilter
    {
        Bitmap doFilter(Bitmap image);
    }
}
