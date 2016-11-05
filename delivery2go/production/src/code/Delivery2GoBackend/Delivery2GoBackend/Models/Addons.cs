using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Addons
	{

		public Addons()
		{

			OrderDishs = new HashSet<OrderDish>();
			OrderDishAddonses = new HashSet<OrderDishAddons>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public int EntityId { get; set; }

		public double Price { get; set; }

		public bool IsAvailable { get; set; }

		public bool IsDressing { get; set; }

		public int? AvailableCount { get; set; }

		public int? UpdateUserId { get; set; }

		public DateTime UpdateDate { get; set; }

		public DateTime CreateDate { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<OrderDish> OrderDishs {get;set;}

		public virtual ICollection<OrderDishAddons> OrderDishAddonses {get;set;}

	}
}
