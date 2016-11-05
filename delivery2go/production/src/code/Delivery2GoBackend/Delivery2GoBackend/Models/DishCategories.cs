using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishCategories
	{

		public int DishId { get; set; }

		public int CategoryId { get; set; }

		public virtual Dish Dish { get; set; }

		public virtual DishCategory Category { get; set; }

	}
}
