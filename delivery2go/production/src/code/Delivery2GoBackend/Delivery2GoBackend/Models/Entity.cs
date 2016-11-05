using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace Delivery2GoBackend.Models
{
	public partial class Entity
	{

		public Entity()
		{

			EntityImageses = new HashSet<EntityImages>();
			EntityCategorieses = new HashSet<EntityCategories>();
			ClientEntityFavoriteses = new HashSet<ClientEntityFavorites>();
			UserEntitieses = new HashSet<UserEntities>();
			Addonses = new HashSet<Addons>();
			EntityMenus = new HashSet<EntityMenu>();
			EntityReviews = new HashSet<EntityReview>();
			Dishs = new HashSet<Dish>();
			Orders = new HashSet<Order>();

		}

		public int Id { get; set; }

		[Required]
		public string Name { get; set; }

		public int? Ranking { get; set; }

		public int ReviewCount { get; set; }

		public int? ImageId { get; set; }

		public string Adress { get; set; }

		public int? CityId { get; set; }

		public int? StateId { get; set; }

		public double? Lat { get; set; }

		public double? Lng { get; set; }

		public string Phone { get; set; }

		public string Email { get; set; }

		public string Tags { get; set; }

		public DateTime? OpeningTime { get; set; }

		public DateTime? CloseTime { get; set; }

		public string Description { get; set; }

		public bool IsAvailable { get; set; }

		public double? DeliveryPrice { get; set; }

		public double? DeliveryTimeMin { get; set; }

		public double? DeliveryTimeMax { get; set; }

		public double? MinPrice { get; set; }

		public DateTime? CreateDate { get; set; }

		public bool HasDelivery { get; set; }

		public bool HasPickup { get; set; }

		public string Account { get; set; }

		public int? UpdateUserId { get; set; }

		public DateTime UpdateDate { get; set; }

		public virtual Image Image { get; set; }

		public virtual City City { get; set; }

		public virtual State State { get; set; }

		public virtual User UpdateUser { get; set; }

		public virtual ICollection<EntityImages> EntityImageses {get;set;}

		public virtual ICollection<EntityCategories> EntityCategorieses {get;set;}

		public virtual ICollection<ClientEntityFavorites> ClientEntityFavoriteses {get;set;}

		public virtual ICollection<UserEntities> UserEntitieses {get;set;}

		public virtual ICollection<Addons> Addonses {get;set;}

		public virtual ICollection<EntityMenu> EntityMenus {get;set;}

		public virtual ICollection<EntityReview> EntityReviews {get;set;}

		public virtual ICollection<Dish> Dishs {get;set;}

		public virtual ICollection<Order> Orders {get;set;}

	}
}
