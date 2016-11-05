using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Roll
	{

		public Roll()
		{

			Permissions = new HashSet<Permission>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual ICollection<Permission> Permissions {get;set;}

	}
}
