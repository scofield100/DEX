using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishReview
	{

		public int Id { get; set; }

		public int Rating { get; set; }

		public int ClientId { get; set; }

		public int DishId { get; set; }

		[Required]
		public string Description { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual Client Client { get; set; }

		public virtual Dish Dish { get; set; }

	}
}
