using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class EntityImages
	{

		public int EntityId { get; set; }

		public int ImageId { get; set; }

		public virtual Entity Entity { get; set; }

		public virtual Image Image { get; set; }

	}
}
