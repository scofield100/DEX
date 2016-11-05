using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Image
	{

		public Image()
		{

			EntityImageses = new HashSet<EntityImages>();
			DishImageses = new HashSet<DishImages>();
			EntityImage = new HashSet<Entity>();
			EntityMenus = new HashSet<EntityMenu>();
			DishImage = new HashSet<Dish>();
			Orders = new HashSet<Order>();

		}

		public int Id { get; set; }

		[Required]
		public string Location { get; set; }

		public int? UpdateUserId { get; set; }

        public byte[] ImageData { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<EntityImages> EntityImageses {get;set;}

		public virtual ICollection<DishImages> DishImageses {get;set;}

		public virtual ICollection<Entity> EntityImage {get;set;}

		public virtual ICollection<EntityMenu> EntityMenus {get;set;}

		public virtual ICollection<Dish> DishImage {get;set;}

		public virtual ICollection<Order> Orders {get;set;}
        
    }
}
