using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class ClientEntityFavorites
	{

		public int ClientId { get; set; }

		public int EntityId { get; set; }

		public virtual Client Client { get; set; }

		public virtual Entity Entity { get; set; }

	}
}
