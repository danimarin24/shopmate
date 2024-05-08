using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopMate_Client_V1.Model
{
    public class Item
    {
        public uint ItemId { get; set; }

        public string Name { get; set; }

        public uint? CategoryId { get; set; }

        public DateTime? UpdatedAt { get; set; }

        public DateTime? CreatedAt { get; set; }         

        

      
    }
}
