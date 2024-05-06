using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopMate_Client_V1.Model
{
    public class Category
    {

        public uint CategoryId { get; set; }

        public string Name { get; set; }

        public string Icon { get; set; }

        public DateTime? UpdatedAt { get; set; }

        public DateTime? CreatedAt { get; set; }

        public virtual ICollection<Item> Items { get; set; } = new List<Item>();
      
    }
}
