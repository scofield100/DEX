using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Order
	{

		public Order()
		{

			OrderDishs = new HashSet<OrderDish>();

		}

		public int OrderId { get; set; }

		[Required]
		public string Code { get; set; }

		public int EntityId { get; set; }

		public int ClientId { get; set; }

		public DateTime CreateDate { get; set; }

		public double TotalAmount { get; set; }

		public double DeliveryCharge { get; set; }

		public double TaxAmount { get; set; }

		public DateTime UpdateDate { get; set; }

		public string DeliveryAdress { get; set; }

		public string Phone { get; set; }

		public double? DeliveryLat { get; set; }

		public double? DeliveryLng { get; set; }

		public int OrderStateId { get; set; }

		public int OrderTypeId { get; set; }

		public string PaymentRef { get; set; }

		public int? ClientSignatureId { get; set; }

		public int? DeliveryTimeMinutes { get; set; }

		public string Remarks { get; set; }

		public int? UpdateUserId { get; set; }

        public bool ClientNotified { get; set; }

        public bool EntityNotified { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual Client Client { get; set; }

		public virtual OrderState OrderState { get; set; }

		public virtual OrderType OrderType { get; set; }

		public virtual Image ClientSignature { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<OrderDish> OrderDishs {get;set;}

	}
}
