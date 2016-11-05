using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Permission
	{

		public int Id { get; set; }

		public int RollId { get; set; }

		public int UserId { get; set; }

		public bool CanRead { get; set; }

		public bool CanWrite { get; set; }

		public bool CanCreate { get; set; }

		public bool CanDelete { get; set; }

		public DateTime CreateDate { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual Roll Roll { get; set; }

		public virtual User User { get; set; }

	}
}
