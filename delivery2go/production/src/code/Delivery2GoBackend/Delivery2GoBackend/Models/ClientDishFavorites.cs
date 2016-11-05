using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class ClientDishFavorites
	{

		public int ClientId { get; set; }

		public int DishId { get; set; }

		public virtual Client Client { get; set; }

		public virtual Dish Dish { get; set; }

	}
}
