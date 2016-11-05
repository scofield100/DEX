using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class EntityCategories
	{

		public int EntityId { get; set; }

		public int CategoryId { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual EntityCategory Category { get; set; }

	}
}
