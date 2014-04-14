using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace ImageBase64
{
    [DataContract]
    class JSONGemID
    {
        [DataMember]
        public string species {get; set;}
        [DataMember]
        public string supplierId { get; set; }
        [DataMember]
        public string sizeZ { get; set; }
        [DataMember]
        public string sizeY { get; set; }
        [DataMember]
        public string origin { get; set; }
        [DataMember]
        public string priceCurrency { get; set; }
        [DataMember]
        public string sizeX { get; set; }
        [DataMember]
        public string shape { get; set; }
        [DataMember]
        public string currentStatus { get; set; }
        [DataMember]
        public string enhancement { get; set; }
        [DataMember]
        public string reference { get; set; }
        [DataMember]
        public string priceValue { get; set; }
        [DataMember]
        public string mass { get; set; }
        [DataMember]
        public string creationDate { get; set; }
        [DataMember]
        public string certificate { get; set; }
        [DataMember]
        public string color { get; set; }
        [DataMember]
        public string clarity { get; set; }
        [DataMember]
        public string cut { get; set; }
        [DataMember]
        public string photoBinary { get; set; }
        [DataMember]
        public string light { get; set; }
        [DataMember]
        public string comments { get; set; }
    }
}
