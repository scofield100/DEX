using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class OrderDish
	{

		public OrderDish()
		{

			OrderDishAddonses = new HashSet<OrderDishAddons>();

		}

		public int Id { get; set; }

		public int OrderId { get; set; }

		public int DishId { get; set; }

		public double DishPrice { get; set; }

		public int Quantity { get; set; }

		public int? SizeId { get; set; }

		public int? DressingId { get; set; }

		public double? DressingPrice { get; set; }

		public double? SubTotal { get; set; }

		public DateTime UpdateDate { get; set; }

		public string Remarks { get; set; }

		public int? UpdateUserId { get; set; }

		public virtual Order Order { get; set; }

		public virtual Dish Dish { get; set; }

		public virtual DishSizePrice Size { get; set; }

		public virtual Addons Dressing { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<OrderDishAddons> OrderDishAddonses {get;set;}

	}
}
