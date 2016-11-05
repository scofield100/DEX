using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishImages
	{

		public int DishId { get; set; }

		public int ImageId { get; set; }

		public virtual Dish Dish { get; set; }

		public virtual Image Image { get; set; }

	}
}
