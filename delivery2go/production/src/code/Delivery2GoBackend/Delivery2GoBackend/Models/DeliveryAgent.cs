using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class DeliveryAgent
	{

		public DeliveryAgent()
		{

			DeliveryTarifs = new HashSet<DeliveryTarif>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public string Account { get; set; }

		public double DeliveryDiscount { get; set; }

		public DateTime UpdateDate { get; set; }

		public DateTime CreateDate { get; set; }

		public int UpdateUserId { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<DeliveryTarif> DeliveryTarifs {get;set;}

	}
}
