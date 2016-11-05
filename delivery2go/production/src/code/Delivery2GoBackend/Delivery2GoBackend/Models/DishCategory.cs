using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DishCategory
	{

		public DishCategory()
		{

			DishCategorieses = new HashSet<DishCategories>();

		}

		public int Id { get; set; }

		[Required]
		public string Code { get; set; }

		[Required]
		public string Name { get; set; }

		public int? UpdateUserId { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<DishCategories> DishCategorieses {get;set;}

	}
}
