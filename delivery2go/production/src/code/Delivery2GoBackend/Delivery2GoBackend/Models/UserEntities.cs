using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class UserEntities
	{

		public int UserId { get; set; }

		public int EntityId { get; set; }

		public virtual User User { get; set; }

		public virtual Entity Entity { get; set; }

	}
}
