using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class State
	{

		public State()
		{

			Cities = new HashSet<City>();
			Entities = new HashSet<Entity>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public virtual ICollection<City> Cities {get;set;}

		public virtual ICollection<Entity> Entities {get;set;}

	}
}
