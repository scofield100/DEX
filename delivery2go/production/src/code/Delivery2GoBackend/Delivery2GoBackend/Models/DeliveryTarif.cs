using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DeliveryTarif
	{

		public int Id { get; set; }

		public double FromMiles { get; set; }

		public double? ToMiles { get; set; }

		public double Price { get; set; }

		public int DeliveryAgentId { get; set; }

		public DateTime UpdateDate { get; set; }

		public DateTime CreateDate { get; set; }

		public int UpdateUserId { get; set; }

		public virtual DeliveryAgent DeliveryAgent { get; set; }

		public virtual User UpdateUser { get; set; }

	}
}
