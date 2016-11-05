using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishSize
	{

		public DishSize()
		{

			DishSizePrices = new HashSet<DishSizePrice>();

		}

		public int Id { get; set; }

		[Required]
		public string Code { get; set; }

		[Required]
		public string Name { get; set; }

		public virtual ICollection<DishSizePrice> DishSizePrices {get;set;}

	}
}
