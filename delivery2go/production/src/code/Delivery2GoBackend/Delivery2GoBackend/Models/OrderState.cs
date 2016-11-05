using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class OrderState
	{

		public OrderState()
		{

			Orders = new HashSet<Order>();

		}

		public int Id { get; set; }

		[Required]
		public string Code { get; set; }

		[Required]
		public string Name { get; set; }

		public virtual ICollection<Order> Orders {get;set;}

	}
}
