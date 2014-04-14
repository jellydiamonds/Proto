using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace ImageBase64
{
    [DataContract]
    class JSONJellyCollection
    {
        [DataMember]
        public List<JSONGemID> jellyCollection
        { get; set; }

        [DataMember]
        public string jellyUser { get; set; }
    }
}
