using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class OrderDishAddons
	{

		public int OrderDishId { get; set; }

		public int AddonId { get; set; }

		public double AddonPrice { get; set; }

		public DateTime UpdateDate { get; set; }

		public string Remarks { get; set; }

		public int? UpdateUserId { get; set; }

		public virtual OrderDish OrderDish { get; set; }

		public virtual Addons Addon { get; set; }

		public virtual User UpdateUser { get; set; }

	}
}
