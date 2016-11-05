using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class EntityMenu
	{

		public EntityMenu()
		{

			Dishs = new HashSet<Dish>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public int EntityId { get; set; }

		public bool IsSpecial { get; set; }

		public bool IsAvailable { get; set; }

		public int? ImageId { get; set; }

		public int? UpdateUserId { get; set; }

		public DateTime UpdateDate { get; set; }

		public DateTime CreateDate { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual Image Image { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<Dish> Dishs {get;set;}

	}
}
