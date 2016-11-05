using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class EntityCategory
	{

		public EntityCategory()
		{

			EntityCategorieses = new HashSet<EntityCategories>();

		}

		public int Id { get; set; }

		[Required]
		public string Code { get; set; }

		[Required]
		public string Name { get; set; }

		public virtual ICollection<EntityCategories> EntityCategorieses {get;set;}

	}
}
