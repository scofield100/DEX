using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishSizePrice
	{

		public DishSizePrice()
		{

			OrderDishs = new HashSet<OrderDish>();

		}

		public int Id { get; set; }

		public int SizeId { get; set; }

		public int DishId { get; set; }

		public double ExtraPrice { get; set; }

		public int? AvailableCount { get; set; }

		public DateTime UpdateDate { get; set; }

		public int? UpdateUserId { get; set; }

		public virtual DishSize Size { get; set; }

		public virtual Dish Dish { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<OrderDish> OrderDishs {get;set;}

	}
}
