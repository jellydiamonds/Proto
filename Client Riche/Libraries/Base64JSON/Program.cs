using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.IO;
using System.Runtime.Serialization.Json;

namespace ImageBase64
{
    class Program
    {
        static void Main(string[] args)
        {
            FileStream f = File.Open(args[0], FileMode.Open);

            JSONJellyCollection j = new JSONJellyCollection();

            DataContractJsonSerializer ser = new DataContractJsonSerializer(j.GetType());
            JSONJellyCollection jellyCollection = ser.ReadObject(f) as JSONJellyCollection;

            int n = 0;

            foreach (var gemID in jellyCollection.jellyCollection)
            {
                if (!gemID.photoBinary.Equals(""))
                {
                    Image photo;
                    photo = gemID.photoBinary.toBitmap();
                    photo.Save("resultat" + (n++) + ".jpg", System.Drawing.Imaging.ImageFormat.Jpeg);
                }
            }

//            Image photo;
//            JSONJellyCollection j = new JSONJellyCollection();
//            j.jellyUser = "Martin Raoul, le mec le plus cool";

//            List<JSONGemID> lGemID = new List<JSONGemID>();
//            for (int i = 0; i < args.Length; i++)
//            {
//                photo = new Bitmap(args[i]);
//                JSONGemID g = new JSONGemID();
//                g.species = i.ToString();
//                g.certificate = i.ToString();
//                g.supplierId = i.ToString();
//                g.sizeX = "1.0";
//                g.sizeY = "2.0";
//                g.sizeZ = i.ToString();
//                g.shape = i.ToString();
//                g.reference = "";
//                g.priceValue = i.ToString();
//                g.priceCurrency = i.ToString();
//                g.photoBinary = photo.toBase64();
//                g.origin = i.ToString();
//                g.mass = i.ToString();
//                g.light = i.ToString();
//                g.enhancement = i.ToString();
//                g.cut = i.ToString();
//                g.currentStatus = "local";
//                g.comments = "La gemme numero " + i;
//                g.color = "#" + i + i + i + i + i + i;
//                g.clarity = i.ToString();
//                g.creationDate = (DateTime.Now.ToUniversalTime() - new DateTime(1970, 1, 1)).TotalSeconds.ToString();
//                lGemID.Add(g);
//            }

//            j.jellyCollection = lGemID;

//            FileStream fw = new FileStream("JSONtestMartin.txt", FileMode.Create);
//            DataContractJsonSerializer ser = new DataContractJsonSerializer(j.GetType());
//            ser.WriteObject(fw, j);
        }
    }
}
