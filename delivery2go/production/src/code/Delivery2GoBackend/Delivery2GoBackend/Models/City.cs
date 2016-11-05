using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class City
	{

		public City()
		{

			Entities = new HashSet<Entity>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public int StateId { get; set; }

		public virtual State State { get; set; }

		public virtual ICollection<Entity> Entities {get;set;}

	}
}
